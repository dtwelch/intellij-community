package com.jetbrains.resolve.action.formatting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.action.CompilerIssueListener;
import com.jetbrains.resolve.action.ResolveValidateAction;
import edu.clemson.resolve.Resolve;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ResolveFormatFileAction extends ResolveFormatAction {

  @Override
  public void update(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || file == null || !file.isInLocalFileSystem() || !isAvailableOnFile(file)) {
      e.getPresentation().setEnabled(false);
      return;
    }
    e.getPresentation().setEnabled(true);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || !isAvailableOnFile(file)) return;

    commitDoc(project, file);
    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
    if (editor == null) return;

    List<String> args = getArguments(e);
    CompilerIssueListener issueListener = new CompilerIssueListener();

    Resolve compiler = ResolveValidateAction.setupAndRunCompiler(project, getTitle(), file, args, issueListener);
    ResolveValidateAction.annotateIssues(editor, file, compiler, issueListener);
    VfsUtil.markDirtyAndRefresh(true, true, true, file);
  }

  @NotNull
  public abstract String getTitle();
}
