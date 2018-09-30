package com.jetbrains.resolve;

import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.ide.IdeAboutInfoUtil;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.ContentFactory;
import com.jetbrains.resolve.symbols.MathSymbolPanel;
import edu.clemson.resolve.core.Main;
import edu.clemson.resolve.verifier.gui.MainWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * This object is the controller for the RESOLVE plug-in. It receives events and can send them on to its contained
 * components; here the main components being primarily the compiler's console output window.
 */
public class ResolveStudioController implements ProjectComponent {

  //public static final String RESOLVESTUDIO_ID = "com.jetbrains.resolve";
  public static final Logger LOG = Logger.getInstance("RESOLVEPluginController");

  public static final String CONSOLE_WINDOW_ID = "RESOLVE Output";
  public static final String VERIFIER_WINDOW_ID = "RESOLVE VCs";
  public static final String SYMBOL_WINDOW_ID = "Symbols";

  public boolean projectIsClosed = false;

  public Project project;

  public ConsoleView console;
  public ToolWindow consoleWindow;

  //public VerifierPanel verifierPanel;
  public ToolWindow verifierWindow;

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
    //installListeners();
  }

  public static class Foo extends JBPanel {

  }
  public void createToolWindows() {
    LOG.info("createToolWindows " + project.getName());
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

    Main.InitConfig env = new Main.InitConfig();
    MainWindow mainVerifierWindowFrame = new MainWindow(env, false);
    JPanel verifierPanel = mainVerifierWindowFrame.getContentsAsPanel();

    //verifierPanel = new VerifierPanel(project);
    mathSymbolPanel = new MathSymbolPanel(project);

    ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

    //init verifier window
    verifierWindow = toolWindowManager.registerToolWindow(VERIFIER_WINDOW_ID, true, ToolWindowAnchor.BOTTOM);
    verifierWindow.getContentManager().addContent(contentFactory.createContent(verifierPanel, "", false));
    //verifierWindow.setIcon(ResolveIcons.);

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

  @Override
  public void projectClosed() {
    LOG.info("projectClosed " + project.getName());
    projectIsClosed = true;
    //uninstallListeners();
    Disposer.dispose(console);

    //unregisterWindow(VERIFIER_WINDOW_ID);
    unregisterWindow(SYMBOL_WINDOW_ID);
    unregisterWindow(CONSOLE_WINDOW_ID);

    //verifierPanel = null;
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

  /*public VerifierPanel getVerifierPanel() {
    return verifierPanel;
  }*/

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
}
