package com.jetbrains.resolve.action.fmt;

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
import com.jetbrains.resolve.action.ResolveValidateAction;
import com.jetbrains.resolve.action.RunResolveListener;
import com.jetbrains.resolve.configuration.ResolveCompilerSettings;
import edu.clemson.resolve.core.Main;
import edu.clemson.resolve.core.control.AbstractUserInterfaceControl;
import edu.clemson.resolve.core.control.DefaultUserInterfaceControl;
import edu.clemson.resolve.semantics.SyntaxAwareMathFormatter;
import edu.clemson.resolve.verifier.settings.VerifierIndependentSettings;
import org.jetbrains.annotations.NotNull;

/**
 * Formats a single (open) file according to the settings stored in {@link ResolveCompilerSettings}.
 */
public class ResolveFileFormatAction extends AbstractFormatAction {

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

    Main.InitConfig env = new Main.InitConfig();

    AbstractUserInterfaceControl control = new DefaultUserInterfaceControl(env);
    control.registerSupplementalASCIIAbbreviations();

    VerifierIndependentSettings.DEFAULT_INSTANCE.getViewSettings()
      .setUseUnicodeNotFontAware(ideSettings.isUseMathUnicodeSymbols());
    VerifierIndependentSettings.DEFAULT_INSTANCE.getViewSettings()
      .setUseAsciiAbbreviations(ideSettings.isUseMathAsciiAbbreviations());
    doFormat(editor, project, file, control);
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Format Math in File by Settings";
  }
}
