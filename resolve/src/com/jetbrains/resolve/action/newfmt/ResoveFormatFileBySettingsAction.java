package com.jetbrains.resolve.action.newfmt;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.ResolveStudioController;
import com.jetbrains.resolve.action.CompilerIssueListener;
import com.jetbrains.resolve.action.ResolveValidateAction;
import com.jetbrains.resolve.action.RunResolveListener;
import com.jetbrains.resolve.configuration.ResolveCompilerSettings;
import edu.clemson.resolve.core.Main;
import edu.clemson.resolve.core.control.AbstractUserInterfaceControl;
import edu.clemson.resolve.core.control.DefaultUserInterfaceControl;
import edu.clemson.resolve.semantics.SimpleNotationAwareMathFormatter;
import edu.clemson.resolve.util.Debug;
import edu.clemson.resolve.util.Utils;
import edu.clemson.resolve.verifier.prettyprint.NotationInfo;
import edu.clemson.resolve.verifier.settings.VerifierIndependentSettings;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResoveFormatFileBySettingsAction extends ResolveFormatAction {

  @Override
  public void update(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null ||
        file == null ||
        file.isDirectory() ||
        !file.isInLocalFileSystem() ||
        !isAvailableOnFile(file)) {
      e.getPresentation().setEnabled(false);
      return;
    }
    e.getPresentation().setEnabled(true);
  }

  //maybe make it so I can manually configure the NotationInfo
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || !isAvailableOnFile(file)) return;

    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
    if (editor == null) return;
    commitDoc(project, file);

    ResolveCompilerSettings ideSettings = ResolveCompilerSettings.getInstance();

    //NotationInfo.INSTANCE.UNICODE_ENABLED = ideSettings.isUseMathUnicodeSymbols();
    //NotationInfo.INSTANCE.USE_ASCII_ABBREVIATIONS = ideSettings.isUseMathAsciiAbbreviations();

    Main.InitConfig env = new Main.InitConfig();

    AbstractUserInterfaceControl control = new DefaultUserInterfaceControl(env);
    Debug.ENABLE_DEBUG = true;

    control.registerSupplementalASCIIAbbreviations();

    VerifierIndependentSettings.DEFAULT_INSTANCE.getViewSettings()
      .setUseUnicodeNotFontAware(ideSettings.isUseMathUnicodeSymbols());
    VerifierIndependentSettings.DEFAULT_INSTANCE.getViewSettings()
      .setUseAsciiAbbreviations(ideSettings.isUseMathAsciiAbbreviations());

    ConsoleView console = ResolveStudioController.getInstance(project).getConsole();
    console.clear();
    String timeStamp = getTimeStamp();

    console.print(timeStamp + ": resolve " + "-format " + file.getPath() + "\n",
                  ConsoleViewContentType.SYSTEM_OUTPUT);
    RunResolveListener defaultListener = new RunResolveListener(control, console);

    control.addAdditionalCompilerListener(defaultListener);

    SimpleNotationAwareMathFormatter formatter = new SimpleNotationAwareMathFormatter(control);
    formatter.formatFileOrDirectory(file.getPath());
    //ResolveValidateAction.setupAndRunCompiler(project, getTitle(), file, args, issueListener);
    ResolveValidateAction.annotateIssues(editor, file, control, null);

    VfsUtil.markDirtyAndRefresh(true, true, true, file);
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Format File";
  }
}
