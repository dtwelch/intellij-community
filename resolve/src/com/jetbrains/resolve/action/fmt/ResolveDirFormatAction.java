package com.jetbrains.resolve.action.fmt;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ResolveDirFormatAction extends AbstractFormatAction {

  @Override
  public void update(AnActionEvent e) {
    Project project = e.getProject();

    VirtualFile directory = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || directory == null || !directory.isDirectory()) {
      e.getPresentation().setEnabled(false);
      return;
    }
    boolean onPath = isDirectoryWithinResolvePath(project, directory);
    boolean onRoot = isDirectoryWithinResolveRoot(project, directory);
    e.getPresentation().setEnabled(onPath || onRoot);
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

    VirtualFile sdkResolveRootDir = ResolveSdkUtil.getSdkSrcDir(project);
    VirtualFile sdkResolvePathDir = ResolveSdkUtil.getResolvePathRoot(project);
    if (sdkResolveRootDir == null || sdkResolvePathDir == null) return; //houston we've got a problem


    //ResolveValidateAction.setupAndRunCompiler(project, getTitle(), directory, args, issueListener);
    VfsUtil.markDirtyAndRefresh(true, true, true, directory);
  }

  private boolean isDirectoryWithinResolvePath(@NotNull Project p, @NotNull VirtualFile directory) {
    boolean result = true;
    VirtualFile sdkResolvePathDir = ResolveSdkUtil.getResolvePathRoot(p);
    if (sdkResolvePathDir == null) return false;
    for (VirtualFile vf : ResolveSdkUtil.getSourcesPathsToLookup(p)) {
      String vfp = vf.getPath();
      if (sdkResolvePathDir.getPath().equals(vfp) && directory.getPath().startsWith(vfp)) {
        result = false;
        break;
      }
    }
    return result;
  }

  private boolean isDirectoryWithinResolveRoot(@NotNull Project p, @NotNull VirtualFile directory) {
    boolean result = true;
    VirtualFile sdkRootDir = ResolveSdkUtil.getSdkSrcDir(p);
    if (sdkRootDir == null) return false;
    for (VirtualFile vf : ResolveSdkUtil.getSourcesPathsToLookup(p)) {
      String vfp = vf.getPath();
      if (sdkRootDir.getPath().equals(vfp) && directory.getPath().startsWith(vfp)) {
        result = false;
        break;
      }
    }
    return result;
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Format RESOLVE Files in Directory";
  }
}
