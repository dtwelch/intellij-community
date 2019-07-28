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
import edu.clemson.resolve.verifier.absyn.PTerm;
import edu.clemson.resolve.verifier.gui.MainWindow;
import edu.clemson.resolve.verifier.gui.VerificationConditionTreePanel;
import edu.clemson.resolve.verifier.proof.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    installListeners();
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

  private void installListeners() {
    ClosedDerivationAnnotationListener l =
      new ClosedDerivationAnnotationListener(mainVerifierWindowFrame, project);
    mainVerifierWindowFrame.getMediator().addDerivationTreeListener(l);

  }

  private void uninstallListeners() {

  }

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
  }

  //responsible for annotating gutter information about vcs + highlighting the regions corresponding to
  // selected vcs in the editor
  public static class ClosedDerivationAnnotationListener extends DerivationTreeAdaptor {

    private final MainWindow mainWindowFrame;
    private final Project project;
    //these are basically the highlights for each line where one or more vcs arises (bscly for annotation
    //of the gutter icons)
    final List<RangeHighlighter> activeVcGutterLineHiglighters = new ArrayList<>();

    //technically this is just going to be a singleton (as far as my thinking has gone), cant
    //use an ordinary var though as java would copy the ref, so it'd have to be effectively final
    // (which I can't do for this since its constantly getting updated as users click various vcs in the VGui panel)
    final List<RangeHighlighter> currentlySelectedVcHighlighters = new ArrayList<>();

    private DocumentListener docListener = null;
    private ActionListener reloadListener = null;
    private ResolveSelectionListener vcSelectionListener = null;

    public ClosedDerivationAnnotationListener(@NotNull MainWindow mainWindow, @NotNull Project p) {
      this.mainWindowFrame = mainWindow;
      this.project = p;
    }

    @Override
    public void derivationClosed(ProofTreeEvent e) {
      WindowUserInterfaceControl uiControl = mainWindowFrame.getUserInterface();
      ImmutableList<VerificationCondition> finalVCs = uiControl.convertToVcs(e.getSource());
      if (finalVCs.isEmpty()) return;

      ApplicationManager.getApplication().invokeLater(
        new Runnable() {
          public void run() {

            Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

            if (editor == null || editor.isDisposed()) return;

            if (docListener != null) {
              editor.getDocument().removeDocumentListener(docListener);
            }
            if (reloadListener != null) {
              mainWindowFrame.getReloadFileAction().removeActionListener(reloadListener);
            }
            if (vcSelectionListener != null) {
              mainWindowFrame.getMediator().getSelectionModel()
                .removeResolveSelectionListener(vcSelectionListener);
            }

            //add the gutter icons + links
            annotateVcGutterIcons(editor, finalVCs);

            vcSelectionListener = getVcSelectionHighlightingListener(editor);
            mainWindowFrame.getMediator().getSelectionModel()
              .addResolveSelectionListener(vcSelectionListener);

            docListener = getDocumentChangeListener(editor);
            editor.getDocument().addDocumentListener(docListener);

            reloadListener = getReloadListener(editor);
            mainWindowFrame.getReloadFileAction().addActionListener(reloadListener);
          }
        });
    }

    private void annotateVcGutterIcons(Editor editor,
                                       @NotNull ImmutableList<VerificationCondition> vcs) {

      if (!editor.isDisposed()) {
        MarkupModel markup = editor.getMarkupModel();

        //RESOLVEPluginController controller = RESOLVEPluginController.getInstance(project);
        //markup.removeAllHighlighters();

        //A mapping from [line number] -> [vc_1, .., vc_j]
        Map<Integer, List<VerificationCondition>> byLine = getVCsGroupedByLineNumber(vcs);

        for (Map.Entry<Integer, List<VerificationCondition>> vcsByLine : byLine.entrySet()) {
          List<AnAction> actionsPerVC = new ArrayList<>();
          //create clickable actions for each vc
          for (VerificationCondition vc : vcsByLine.getValue()) {
            actionsPerVC.add(new VCNavigationAction(vc.getUniqueId(),
                                                    vc.getSourceInfo().getExplanation(), vc,
                                                    mainWindowFrame));
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
          activeVcGutterLineHiglighters.add(highlighter);
        }
      }
    }

    private Map<Integer, List<VerificationCondition>> getVCsGroupedByLineNumber(
      Iterable<VerificationCondition> vcs) {
      Map<Integer, List<VerificationCondition>> result = new HashMap<>();

      for (VerificationCondition vc : vcs) {
        PTerm.TermLabel vcInfo = vc.getSourceInfo();
        PTerm.Location loc = vcInfo.getReportableLocation();
        int currentLineNum = loc.getLineNumber();
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

    public ResolveSelectionListener getVcSelectionHighlightingListener(Editor editor) {
      return new ResolveSelectionListener() {
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
          if (editor == null) return;

          //remove the existing vc location highlight in the editor (if there is one)
          MarkupModel markup = editor.getMarkupModel();
          for (RangeHighlighter h : currentlySelectedVcHighlighters) {
            markup.removeHighlighter(h);
          }
          //clear the prior highlight from our running list (if there was one)
          currentlySelectedVcHighlighters.clear();

          final TextAttributes attr = new TextAttributes();

          int a = vc.getSourceInfo().getReportableLocation().getStart();
          int b = vc.getSourceInfo().getReportableLocation().getStop() + 1;
          attr.setBackgroundColor(new JBColor(new Color(246, 235, 188),
                                              new Color(246, 235, 188)));
          attr.setEffectType(EffectType.BOXED);

          VirtualFile f = FileDocumentManager.getInstance().getFile(editor.getDocument());
          if (f == null) return;
          String sourceName = vc.getSourceInfo().getReportableLocation().getFileName();
          String vFilePath = f.getPath();
          if (vFilePath.equals(sourceName)) { //only want highlights in the doc the user is looking at.
            RangeHighlighter h = markup.addRangeHighlighter(
              a, b, HighlighterLayer.ERROR, // layer
              attr, HighlighterTargetArea.EXACT_RANGE);

            currentlySelectedVcHighlighters.add(h);
            h.putUserData(VC_ANNOTATION, vc);
          }
        }
      };
    }

    //i don't like the way this is currently used since it means that each time we close a derivation
    //we install a new document listener
    private DocumentListener getDocumentChangeListener(Editor editor) {
      if (editor == null) return null;
      MarkupModel markup = editor.getMarkupModel();
      //if (editor.getDocument())
      return new DocumentListener() {
        @Override
        public void beforeDocumentChange(DocumentEvent event) {
        }

        @Override
        public void documentChanged(DocumentEvent event) {
          //remove vc-related highlighters
          for (RangeHighlighter h : activeVcGutterLineHiglighters) {
            markup.removeHighlighter(h);
          }
          for (RangeHighlighter h : currentlySelectedVcHighlighters) {
            markup.removeHighlighter(h);
          }

          currentlySelectedVcHighlighters.clear();
          activeVcGutterLineHiglighters.clear();

          //these lines should reset all important state for the verifiergui...
          mainWindowFrame.getActiveDerivationListPanel().disposeDerivations();
          mainWindowFrame.getUserInterface().resetVCcount();
          mainWindowFrame.getUserInterface().resetDerivationVcMappingFromPriorSession();
          mainWindowFrame.getVerificationConditionViewPanel().dispose();
        }
      };
    }

    private ActionListener getReloadListener(Editor editor) {
      return new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (editor == null) return;
          MarkupModel markup = editor.getMarkupModel();
          //remove any active vc gutter related highlighters
          for (RangeHighlighter h : activeVcGutterLineHiglighters) {
            markup.removeHighlighter(h);
          }
          //now remove any highlights related to the currently selected vc
          for (RangeHighlighter h : currentlySelectedVcHighlighters) {
            markup.removeHighlighter(h);
          }
          currentlySelectedVcHighlighters.clear();
          activeVcGutterLineHiglighters.clear();

          //these lines should reset all important state for the verifiergui...
          mainWindowFrame.getMediator().removeResolveSelectionListener(vcSelectionListener);
          mainWindowFrame.getActiveDerivationListPanel().disposeDerivations();
          mainWindowFrame.getUserInterface().resetVCcount();
          mainWindowFrame.getUserInterface().resetDerivationVcMappingFromPriorSession();
          mainWindowFrame.getVerificationConditionViewPanel().dispose();
        }
      };
    }

  }

  public MainWindow getMainVerifierWindowFrame() {
    return mainVerifierWindowFrame;
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
    ApplicationManager.getApplication()
      .invokeLater(() -> getInstance(project).getConsoleWindow().show(null));
  }

  public static void showVerifierWindow(final Project project) {
    ApplicationManager.getApplication()
      .invokeLater(() -> getInstance(project).getVerifierWindow().show(null));
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
      vcView.navigateToSelectedVC(vc);
    }
  }
}
