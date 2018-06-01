package com.jetbrains.resolve.action.formatting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.sdk.ResolveSdkType;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ResolveFormatDirectoryAction extends ResolveFormatAction {

  @Override
  public void update(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || file == null || !file.isDirectory()) {
      e.getPresentation().setEnabled(false);
      return;
    }
    e.getPresentation().setEnabled(true);
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();
    if (project == null) return;

    FileDocumentManager.getInstance().saveAllDocuments();
    VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (file == null) return;
    VirtualFile projectRoot = null;

    //we can only format projects within the scope of RESOLVEROOT or RESOLVEPATH
    for (VirtualFile root : ResolveSdkUtil.getSourcesPathsToLookup(project)) {
      if (file.getPath().startsWith(root.getPath())) {
        projectRoot = root;
        break;
      }
    }

    if (projectRoot == null) return;

    int i;
    i = 0;
  }

  @NotNull
  @Override
  public List<String> getArguments(@NotNull AnActionEvent e) {
    return Collections.emptyList();
  }
}
