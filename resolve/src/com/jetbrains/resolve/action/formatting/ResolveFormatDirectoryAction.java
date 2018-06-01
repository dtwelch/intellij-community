package com.jetbrains.resolve.action.formatting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.action.CompilerIssueListener;
import com.jetbrains.resolve.action.ResolveValidateAction;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public abstract class ResolveFormatDirectoryAction extends ResolveFormatAction {

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
    VirtualFile directory = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (directory == null || !directory.isDirectory()) {
      return; //dunno about this, don't think we need it we already do this in update()...
    }

    boolean onResolvePath = false;
    boolean onResolveRootPath = false;
    VirtualFile sdkResolveRootDir = ResolveSdkUtil.getSdkSrcDir(project);
    VirtualFile sdkResolvePathDir = ResolveSdkUtil.getResolvePathRoot(project);
    if (sdkResolveRootDir == null || sdkResolvePathDir == null) return; //houston we've got a problem

    //can only run sugar directory on something in RESOLVEPATH or RESOLVEROOT (or these themselves)
    for (VirtualFile vf : ResolveSdkUtil.getSourcesPathsToLookup(project)) {
      String vfp = vf.getPath();
      if (sdkResolveRootDir.getPath().equals(vfp) && directory.getPath().startsWith(vfp)) {
        onResolveRootPath = true;
        break;
      }
      else if (sdkResolvePathDir.getPath().equals(vfp) && directory.getPath().startsWith(vfp)) {
        onResolvePath = true;
        break;
      }
    }

    if (!onResolvePath && !onResolveRootPath) {
      e.getPresentation().setEnabled(false);
      return;
    }
    String fname = onResolvePath
                   ? directory.getPath().replace(
                     sdkResolvePathDir.getPath(), "RESOLVEPATH" + File.separator + "src")
                   : directory.getPath().replace(
                     sdkResolveRootDir.getPath(), "RESOLVEROOT" + File.separator + "src") ;

    List<String> args = getArguments(fname);
    CompilerIssueListener issueListener = new CompilerIssueListener();

    ResolveValidateAction.setupAndRunCompiler(project, getTitle(), directory, args, issueListener);
    VfsUtil.markDirtyAndRefresh(true, true, true, directory);
  }
}
