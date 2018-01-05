package com.jetbrains.resolve.sdk;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.*;
import com.intellij.util.SystemProperties;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.library.ResolveEnvUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.Set;

public class ResolveEnvironmentPathModificationTracker {

  private final Set<String> pathsToTrack = ContainerUtil.newHashSet();

  private final Collection<VirtualFile> resolvePathRoots = ContainerUtil.newLinkedHashSet();

  public ResolveEnvironmentPathModificationTracker() {
    String resPath = ResolveEnvUtil.retrieveRESOLVEPath();
    if (resPath != null) {
      String home = SystemProperties.getUserHome();
      for (String s : StringUtil.split(resPath, File.pathSeparator)) {
        if (s.contains("$HOME")) {
          s = s.replaceAll("\\$HOME", home);
        }
        pathsToTrack.add(s);
      }
    }
    recalculateFiles();

    VirtualFileManager.getInstance().addVirtualFileListener(
      new VirtualFileAdapter() {
        @Override
        public void fileCreated(@NotNull VirtualFileEvent event) {
          handleEvent(event);
        }

        @Override
        public void fileDeleted(@NotNull VirtualFileEvent event) {
          handleEvent(event);
        }

        @Override
        public void fileMoved(@NotNull VirtualFileMoveEvent event) {
          handleEvent(event);
        }

        @Override
        public void fileCopied(@NotNull VirtualFileCopyEvent event) {
          handleEvent(event);
        }

        private void handleEvent(VirtualFileEvent event) {
          if (pathsToTrack.contains(event.getFile().getPath())) {
            recalculateFiles();
          }
        }
      });
  }

  private void recalculateFiles() {
    Collection<VirtualFile> result = ContainerUtil.newLinkedHashSet();
    for (String path : pathsToTrack) {
      ContainerUtil.addIfNotNull(result, LocalFileSystem.getInstance().findFileByPath(path));
    }
    updateResolvePathRoots(result);
  }

  private synchronized void updateResolvePathRoots(Collection<VirtualFile> newRoots) {
    resolvePathRoots.clear();
    resolvePathRoots.addAll(newRoots);
  }

  private synchronized Collection<VirtualFile> getResolvePathRoots() {
    return resolvePathRoots;
  }

  public static Collection<VirtualFile> getResolveEnvironmentPathRoots() {
    return ServiceManager.getService(ResolveEnvironmentPathModificationTracker.class).getResolvePathRoots();
  }
}
