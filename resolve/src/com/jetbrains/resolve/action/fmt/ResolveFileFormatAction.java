package com.jetbrains.resolve.action.fmt;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.ResolveCompilerSettings;
import edu.clemson.resolve.core.Main;
import edu.clemson.resolve.core.control.AbstractUserInterfaceControl;
import edu.clemson.resolve.core.control.DefaultUserInterfaceControl;
import edu.clemson.resolve.verifier.settings.GlobalViewSettings;
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

    GlobalViewSettings.DEFAULT_INSTANCE.getViewSettings()
      .setUseUnicodeNotFontAware(ideSettings.isUseMathUnicodeSymbols());
    GlobalViewSettings.DEFAULT_INSTANCE.getViewSettings()
      .setUseAsciiAbbreviations(ideSettings.isUseMathAsciiAbbreviations());
    doFormat(editor, project, file, control);
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Format Math in File by Settings";
  }
}
