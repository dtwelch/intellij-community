package com.jetbrains.resolve.action.fmt;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import edu.clemson.resolve.core.Main;
import edu.clemson.resolve.core.control.AbstractUserInterfaceControl;
import edu.clemson.resolve.core.control.DefaultUserInterfaceControl;
import edu.clemson.resolve.verifier.settings.VerifierIndependentSettings;
import org.jetbrains.annotations.NotNull;

public class ResolveLaTeXFormatAction extends AbstractFormatAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || !isAvailableOnFile(file)) return;

    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
    if (editor == null) return;
    commitDoc(project, file);

    Main.InitConfig env = new Main.InitConfig();

    AbstractUserInterfaceControl control = new DefaultUserInterfaceControl(env);
    VerifierIndependentSettings.DEFAULT_INSTANCE.getViewSettings()
      .setUseUnicodeNotFontAware(false);
    VerifierIndependentSettings.DEFAULT_INSTANCE.getViewSettings()
      .setUseAsciiAbbreviations(true);
    doFormat(editor, project, file, control);
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Format file for LaTeX";
  }
}
