package com.jetbrains.resolve.action.formatting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.action.CompilerIssueListener;
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

    commitDoc(project, file);

    List<String> args = getArguments(file.getPath());
    CompilerIssueListener issueListener = new CompilerIssueListener();

    //ResolveValidateAction.setupAndRunCompiler(project, getTitle(), file, args, issueListener);
    VfsUtil.markDirtyAndRefresh(true, true, true, file);
  }


  @NotNull
  @Override
  public List<String> getArguments(@NotNull String fileName) {
    return null;
  }

  @NotNull
  @Override
  public String getTitle() {
    return null;
  }
}
