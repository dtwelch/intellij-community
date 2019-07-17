package com.jetbrains.resolve;

import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.JBColor;
import com.intellij.ui.content.ContentFactory;
import com.jetbrains.resolve.symbols.MathSymbolPanel;
import edu.clemson.resolve.core.Main;
import edu.clemson.resolve.core.ResolveSelectionEvent;
import edu.clemson.resolve.core.control.WindowUserInterfaceControl;
import edu.clemson.resolve.util.immutableadts.ImmutableList;
import edu.clemson.resolve.verifier.ResolveSelectionListener;
import edu.clemson.resolve.verifier.VerificationCondition;
import edu.clemson.resolve.verifier.derivation.Derivation;
import edu.clemson.resolve.verifier.gui.MainWindow;
import edu.clemson.resolve.verifier.gui.VerificationConditionTreePanel;
import edu.clemson.resolve.verifier.proof.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * This object is the controller for the RESOLVE plug-in. It receives events and can send them on to its contained
 * components; here the main components being primarily the compiler's console output window.
 */
public class ResolveStudioController implements ProjectComponent {

  public static final Key<VerificationCondition> VC_ANNOTATION = Key.create("VC_ANNOTATION");

  //public static final String RESOLVESTUDIO_ID = "com.jetbrains.resolve";
  public static final Logger LOG = Logger.getInstance("RESOLVEPluginController");

  public static final String CONSOLE_WINDOW_ID = "RESOLVE Output";
  public static final String VERIFIER_WINDOW_ID = "RESOLVE VCs";
  public static final String SYMBOL_WINDOW_ID = "Symbols";

  public boolean projectIsClosed = false;

  public Project project;

  public ConsoleView console;
  public ToolWindow consoleWindow;

  public JPanel verifierPanel;
  public ToolWindow verifierWindow;
  public MainWindow mainVerifierWindowFrame;

  public MathSymbolPanel mathSymbolPanel;
  public ToolWindow mathSymbolWindow;

  public ResolveStudioController(@NotNull Project project) {
    this.project = project;
  }

  public static ResolveStudioController getInstance(@NotNull Project project) {
    ResolveStudioController pc = project.getComponent(ResolveStudioController.class);
    if (pc == null) {
      LOG.error("getInstance: getOuterMostComponent() for project.getName() returns null");
    }
    return pc;
  }

  @Override
  public void initComponent() {
  }

  @Override
  public void projectOpened() {
    LOG.info("ResolveStudio running on Java version " + SystemInfo.JAVA_VERSION);
    // make sure the tool windows are created early
    createToolWindows();
    System.setProperty("apple.laf.useScreenMenuBar", "true");

    //installListeners();
  }

  ProofTreeListener ptl = new ProofTreeAdaptor() {
    @Override
    public void proofGoalsAdded(ProofTreeEvent e) {
    }

    //when at least one derivation is closed, this action should be
    // activated
    @Override
    public void derivationClosed(ProofTreeEvent e) {

    }
  };

  private static Map<String, Derivation> alreadyHandledInCurrSession = new HashMap<>();

  public void createToolWindows() {
    LOG.info("createToolWindows " + project.getName());
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    Main.InitConfig env = new Main.InitConfig();
    mainVerifierWindowFrame = MainWindow.getInstance(env, false);

    verifierPanel = mainVerifierWindowFrame.getContentsAsPanel();
    mathSymbolPanel = new MathSymbolPanel(project);

    ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

    //init verifier window
    verifierWindow = toolWindowManager.registerToolWindow(VERIFIER_WINDOW_ID, true, ToolWindowAnchor.BOTTOM);
    verifierWindow.getContentManager().addContent(contentFactory.createContent(verifierPanel, "", false));
    verifierWindow.setIcon(ResolveIcons.VCG);

    //init math symbol browser window
    //TODO: Left for now (at least until the verifier window is up
    mathSymbolWindow = toolWindowManager.registerToolWindow(SYMBOL_WINDOW_ID, true, ToolWindowAnchor.LEFT);
    mathSymbolWindow.setSplitMode(true, null);
    mathSymbolWindow.getContentManager().addContent(contentFactory.createContent(mathSymbolPanel, "", false));
    mathSymbolWindow.setIcon(ResolveIcons.SYMBOLS);

    //init console window
    TextConsoleBuilderFactory factory = TextConsoleBuilderFactory.getInstance();
    TextConsoleBuilder consoleBuilder = factory.createBuilder(project);
    console = consoleBuilder.getConsole();

    JComponent consoleComponent = console.getComponent();
    consoleWindow = toolWindowManager.registerToolWindow(CONSOLE_WINDOW_ID, true, ToolWindowAnchor.BOTTOM);
    consoleWindow.getContentManager().addContent(contentFactory.createContent(consoleComponent, "", false));
    consoleWindow.setIcon(ResolveIcons.RESOLVE);

    //Ok. This was firing (unexpectedly and uninvitedly) twice per each derivation. Took me a while to track
    //down the source; it had to do with an erroneous call in ResolveMediator#setProofHelper(..) which is
    //called once a derivation is closed.
    mainVerifierWindowFrame.getMediator().addDerivationTreeListener(new DerivationTreeAdaptor() {
      @Override
      public void derivationClosed(ProofTreeEvent e) {
        annotateVcsInGutter(e.getSource());
      }
    });

    mainVerifierWindowFrame.getMediator().addResolveSelectionListener(new ResolveSelectionListener() {

      /**
       * focused node has changed
       */
      @Override
      public void selectedNodeChanged(ResolveSelectionEvent e) {
      }

      @Override
      public void selectedProofChanged(ResolveSelectionEvent e) {
      }

      @Override
      public void selectedVCChanged(ResolveSelectionEvent e) {
        if (e.getSource() == null) return;
        if (e.getSource().getSelectedVC() == null) return;

        VerificationCondition vc = e.getSource().getSelectedVC();
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;

        VirtualFile f = FileDocumentManager.getInstance().getFile(editor.getDocument());
        if (f == null) return;
        List<RangeHighlighter> highlighters = new ArrayList<>();
        //annotateVCInEditor(f, highlighters, editor, vc);
      }
    });

    mainVerifierWindowFrame.getReloadFileAction().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) return;
        MarkupModel markup = editor.getMarkupModel();
        markup.removeAllHighlighters();
      }
    });

    //install gutter icon capability + navigation
    //mainVerifierWindowFrame.getUserInterface().getProofControl().getDefaultProverTaskListener()

    /*mainVerifierWindowFrame.getUserInterface().getProofControl()
      .addAutoDerivationModeListener(new AutoModeListener() {
        @Override
        public void autoModeStarted(ProofEvent event) {
          //clear existing gutter icons
        }

        @Override
        public void autoModeStopped(ProofEvent event) {
          annotateVcsInGutter(event.getSource());
        }
      });*/
  }

  private void annotateVcsInGutter(@NotNull Derivation closedDerivation) {
    ApplicationManager.getApplication().invokeLater(
      new Runnable() {
        public void run() {
          WindowUserInterfaceControl uiControl = mainVerifierWindowFrame.getUserInterface();
          if (alreadyHandledInCurrSession.get(closedDerivation.getName()) != null) return;

          ImmutableList<VerificationCondition> finalVCs =
            uiControl.convertToVcs(closedDerivation);

          Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
          if (editor == null) return;
          final List<RangeHighlighter> activeVcLineMarkers = new ArrayList<>();

          addVCGutterIcons(finalVCs, editor, activeVcLineMarkers,
                           mainVerifierWindowFrame.getUserInterface());
          alreadyHandledInCurrSession.put(closedDerivation.getName(), closedDerivation);
        }
      });
  }

  private void addVCGutterIcons(@NotNull ImmutableList<VerificationCondition> vcs,
                                Editor editor, List<RangeHighlighter> activeVcMarkers,
                                WindowUserInterfaceControl control) {
    if (!editor.isDisposed()) {
      MarkupModel markup = editor.getMarkupModel();

      //RESOLVEPluginController controller = RESOLVEPluginController.getInstance(project);
      //markup.removeAllHighlighters();

      //A mapping from [line number] -> [vc_1, .., vc_j]
      Map<Integer, List<VerificationCondition>> byLine = getVCsGroupedByLineNumber(vcs);
      List<RangeHighlighter> vcRelatedHighlighters = new ArrayList<>();

      for (Map.Entry<Integer, List<VerificationCondition>> vcsByLine : byLine.entrySet()) {
        List<AnAction> actionsPerVC = new ArrayList<>();
        //create clickable actions for each vc
        for (VerificationCondition vc : vcsByLine.getValue()) {
          actionsPerVC.add(new VCNavigationAction(vc.getUniqueId(), vc.getSourceInfo().getExplanation(), vc, mainVerifierWindowFrame));
        }

        RangeHighlighter highlighter =
          markup.addLineHighlighter(vcsByLine.getKey() - 1,
                                    HighlighterLayer.ELEMENT_UNDER_CARET, null);
        highlighter.setGutterIconRenderer(new GutterIconRenderer() {
          @NotNull
          @Override
          public Icon getIcon() {
            return ResolveIcons.VC;
          }

          @Override
          public boolean equals(Object obj) {
            return false;
          }

          @Override
          public int hashCode() {
            return 0;
          }

          @Override
          public boolean isNavigateAction() {
            return true;
          }

          public ActionGroup getPopupMenuActions() {
            DefaultActionGroup g = new DefaultActionGroup();
            g.addAll(actionsPerVC);
            return g;
          }

          @Nullable
          public AnAction getClickAction() {
            return null;
          }
        });
        //vcRelatedHighlighters.add(highlighter);
        activeVcMarkers.add(highlighter);
      }

      editor.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void beforeDocumentChange(DocumentEvent event) {
        }

        @Override
        public void documentChanged(DocumentEvent event) {
          //remove vc-related highlighters
          for (RangeHighlighter h : activeVcMarkers) {
            markup.removeAllHighlighters();
            //markup.removeHighlighter(h);
          }

          VirtualFile vf = FileDocumentManager.getInstance().getFile(event.getDocument());

          //these lines should reset all important state for the verifiergui...
          Main.InitConfig env = control.getEnvironment();
          mainVerifierWindowFrame.getActiveDerivationListPanel().disposeDerivations();
          mainVerifierWindowFrame.getUserInterface().resetVCcount();
          mainVerifierWindowFrame.getUserInterface().resetDerivationVcMappingFromPriorSession();
          mainVerifierWindowFrame.getVerificationConditionViewPanel().dispose();
        }
      });
    }
  }

  private Map<Integer, List<VerificationCondition>> getVCsGroupedByLineNumber(
    Iterable<VerificationCondition> vcs) {
    Map<Integer, List<VerificationCondition>> result = new HashMap<>();

    for (VerificationCondition vc : vcs) {
      int currentLineNum = vc.getSourceInfo().getReportableLoc().getLineNumber();
      if (result.get(currentLineNum) == null) {
        List<VerificationCondition> vcsForThisLine = new ArrayList<>();
        vcsForThisLine.add(vc);
        result.put(currentLineNum, vcsForThisLine);
      }
      else {
        result.get(currentLineNum).add(vc);
      }
    }
    return result;
  }

  public void annotateVCInEditor(@NotNull VirtualFile file,
                                 List<RangeHighlighter> highlighters,
                                 Editor editor,
                                 VerificationCondition vc) {
    MarkupModel markupModel = editor.getMarkupModel();  //ref to the current editor's markup model...
    final TextAttributes attr = new TextAttributes();

    int a = vc.getSourceInfo().getReportableLoc().getStart();
    int b = vc.getSourceInfo().getReportableLoc().getStop() + 1;
    attr.setBackgroundColor(new JBColor(new Color(246, 235, 188),
                                        new Color(246, 235, 188)));
    attr.setEffectType(EffectType.BOXED);

    String sourceName = vc.getSourceInfo().getReportableLoc().getFileName();
    String vFilePath = file.getPath();
    if (vFilePath.equals(sourceName)) { //only want highlights in the doc the user is looking at.
      RangeHighlighter h = markupModel.addRangeHighlighter(
        a, b, HighlighterLayer.ERROR, // layer
        attr, HighlighterTargetArea.EXACT_RANGE);

      highlighters.add(h);
      h.putUserData(VC_ANNOTATION, vc);
    }
  }

  public MainWindow getMainVerifierWindowFrame() {
    return mainVerifierWindowFrame;
  }

  @Override
  public void projectClosed() {
    LOG.info("projectClosed " + project.getName());
    projectIsClosed = true;
    //uninstallListeners();
    Disposer.dispose(console);

    unregisterWindow(VERIFIER_WINDOW_ID);
    unregisterWindow(SYMBOL_WINDOW_ID);
    unregisterWindow(CONSOLE_WINDOW_ID);

    mainVerifierWindowFrame = null;
    verifierPanel = null;
    consoleWindow = null;
    verifierWindow = null;

    project = null;
  }

  public void unregisterWindow(String id) {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    toolWindowManager.unregisterToolWindow(id);
  }

  public ConsoleView getConsole() {
    return console;
  }

  public ToolWindow getConsoleWindow() {
    return consoleWindow;
  }

  public ToolWindow getVerifierWindow() {
    return verifierWindow;
  }

  public static void showConsoleWindow(final Project project) {
    ApplicationManager.getApplication().invokeLater(() -> getInstance(project).getConsoleWindow().show(null));
  }

  public static void showVerifierWindow(final Project project) {
    ApplicationManager.getApplication().invokeLater(() -> getInstance(project).getVerifierWindow().show(null));
  }

  @Override
  public void disposeComponent() {
  }

  @NotNull
  @Override
  public String getComponentName() {
    return "resolve.ProjectComponent";
  }

  private static class VCNavigationAction extends AnAction {

    private final int vcNum;
    public boolean isProved = false;

    private final MainWindow ui;
    private final VerificationCondition vc;

    VCNavigationAction(int vcId, String explanation, VerificationCondition vc,
                       MainWindow ui) {
      super("VC #" + vcId + " : " + explanation);
      this.vc = vc;
      Presentation template = this.getTemplatePresentation();
      template.setText("VC #" + vcId + " : " + explanation, false);   //mneumonic set to false so my tooltips can have underscores.
      this.vcNum = vcId;
      this.ui = ui;
    }

    @Override
    public void update(AnActionEvent e) {
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
      if (e.getProject() == null) return;
      ResolveStudioController controller = ResolveStudioController.getInstance(e.getProject());
      controller.getVerifierWindow().show(null);  //open the verifier window

      //update nodeview with the current vc.
      ui.getMediator().setSelectedVC(vc);
      VerificationConditionTreePanel vcView = ui.getVerificationConditionViewPanel();
      //DefaultMutableTreeNode root = tree.getRoot();
      //Now navigate to the node
      vcView.navigateToSelectedVC(vc);

     // for (int i = 0; i < tree.getRowCount())
    //  DefaultMutableTreeNode firstLeaf = root.getFirstLeaf();
     // tree.setSelectionPath(new TreePath(firstLeaf.getPath()));
      //now position the treeView to focus on the vc requested

      /*VerificationConditionSelectorPanel vcselector = controller.getVerifierPanel().getVcSelectorPanel();
      if (vcselector == null) return;
      vcselector.vcTabs.get(Integer.parseInt(vcNum));
      VerifierPanel verifierPanel = controller.getVerifierPanel();
      if (verifierPanel.getVcSelectorPanel() == null) return;
      VerificationConditionSelectorPanel selector = verifierPanel.getVcSelectorPanel();
      ConditionCollapsiblePanel details = selector.vcTabs.get(Integer.parseInt(vcNum));
      details.setExpanded(true);
*/
      //TODO: Make it scroll to the vc selected! This is a top priority usability improvement.
      //vcselector.scrollRectToVisible(details.get);
    }
  }
}
