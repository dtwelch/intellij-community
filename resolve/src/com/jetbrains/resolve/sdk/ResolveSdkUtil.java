// Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jetbrains.resolve.sdk;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.Function;
import com.intellij.util.SystemProperties;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.library.ResolveApplicationLibrariesService;
import com.jetbrains.resolve.library.ResolveLibrariesService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.intellij.util.containers.ContainerUtil.newLinkedHashSet;

public class ResolveSdkUtil {

  // \w   A word character, short for [a-zA-Z_0-9]
  // \s   A whitespace character
  // \d   Any digit [0-9]
  //So this pattern allows matching things like 1.1rc2
  private static final Pattern RESOLVE_VERSION_PATTERN = Pattern.compile("[\\d.]+\\w+(\\d+)?");

  private static final Key<String> RESOLVE_VER_DATA_KEY = Key.create("RESOLVE_VERSION_KEY");

  public static String getResolvePathDefaultBaseDir() {
    final String userHome = SystemProperties.getUserHome();
    return userHome.replace('/', File.separatorChar) + File.separator + "Documents"
           + File.separator + "resolvework" + File.separator + "src";
  }

  /**
   * Retrieves root directories from RESOLVEPATH env-variable. This method doesn't consider user defined libraries,
   * for that case use {@link {@link this#getRESOLVEPathRoots(Project, Module)}
   */
  @NotNull
  public static Collection<VirtualFile> getResolvePathsRootsFromEnvironment() {
    return ResolveEnvironmentPathModificationTracker.getResolveEnvironmentPathRoots();
  }

  @NotNull
  public static File findSequentNonExistingResolveBaseDir() {
    String resolveWorkBaseDir = getResolvePathDefaultBaseDir();
    return FileUtil.findSequentNonexistentFile(new File(resolveWorkBaseDir), "newComponent", "");
  }

  @Nullable
  public static VirtualFile suggestSdkDirectory() {
    if (SystemInfo.isWindows) {
      return LocalFileSystem.getInstance().findFileByPath("C:\\resolve-lite-private");
    }
    if (SystemInfo.isMac || SystemInfo.isLinux) {
      final String userHome = SystemProperties.getUserHome();
      String path = userHome + File.separator + "Documents" + File.separator + "resolve-lite-private";
      VirtualFile usrLocal = LocalFileSystem.getInstance().findFileByPath(path);
      if (usrLocal != null) return usrLocal;
    }
    return null;
  }

  @Nullable
  public static String retrieveResolveVersion(@NotNull String sdkPath) {
    try {
      VirtualFile sdkRoot = VirtualFileManager.getInstance().findFileByUrl(VfsUtilCore.pathToUrl(sdkPath));
      if (sdkRoot != null) {
        String cachedVersion = sdkRoot.getUserData(RESOLVE_VER_DATA_KEY);
        if (cachedVersion != null) {
          return !cachedVersion.isEmpty() ? cachedVersion : null;
        }

        VirtualFile versionFile = sdkRoot.findFileByRelativePath("VERSION.txt");
        if (versionFile != null) {
          String text = VfsUtilCore.loadText(versionFile);
          String version = parseResolveVersion(text);
          if (version == null) {
            ResolveSdkService.LOG.debug("Cannot parse Resolve version in VERSION.txt");
          }
          return version;
        }
        else {
          ResolveSdkService.LOG.debug("Cannot find Resolve version file in sdk path: " + sdkPath);
        }
      }
    }
    catch (IOException e) {
      ResolveSdkService.LOG.debug("Cannot find Resolve version file in sdk path: " + sdkPath, e);
    }
    return null;
  }

  /**
   * Retrieves root directories from RESOLVEPATH env-variable. This method doesn't consider user defined libraries,
   * for that case use {@link {@link this#getRESOLVEPathRoots(Project, Module)}
   */
  @NotNull
  public static Collection<VirtualFile> getRESOLVEPathsRootsFromEnvironment() {
    return ResolveEnvironmentPathModificationTracker.getResolveEnvironmentPathRoots();
  }

  @NotNull
  public static Collection<VirtualFile> getRESOLVEPathSourcesRoot(@NotNull final Project project,
                                                                  @Nullable final Module module) {
    if (module != null) {
      return CachedValuesManager.getManager(project).getCachedValue(
        module, new CachedValueProvider<Collection<VirtualFile>>() {
          @Override
          public Result<Collection<VirtualFile>> compute() {
            Collection<VirtualFile> result = newLinkedHashSet();
            Project project = module.getProject();
            List<VirtualFile> root = getRESOLVEPathSourcesRootInner(project, module);
            result.addAll(getRESOLVEPathSourcesRootInner(project, module));
            return Result.create(result, getSdkAndLibrariesCacheDependencies(project, module));
          }
        });
    }
    return CachedValuesManager.getManager(project).getCachedValue(project,
                                                                  new CachedValueProvider<Collection<VirtualFile>>() {
                                                                    @Nullable
                                                                    @Override
                                                                    public Result<Collection<VirtualFile>> compute() {
                                                                      return Result.create(getRESOLVEPathSourcesRootInner(project, null),
                                                                                           getSdkAndLibrariesCacheDependencies(project,
                                                                                                                               null));
                                                                    }
                                                                  });
  }

  @NotNull
  private static Collection<Object> getSdkAndLibrariesCacheDependencies(@NotNull Project project,
                                                                        @Nullable Module module) {
    Collection<Object> dependencies = ContainerUtil.newArrayList(
      (Object[])ResolveLibrariesService.getModificationTrackers(project, module));
    ContainerUtil.addAllNotNull(dependencies);
    return dependencies;
  }

  @NotNull
  private static List<VirtualFile> getRESOLVEPathSourcesRootInner(@NotNull Project project, @Nullable Module module) {
    return ContainerUtil.mapNotNull(getRESOLVEPathRoots(project, module),
                                    new RetrieveSubDirectoryOrSelfFunction("src"));
  }

  @NotNull
  private static Collection<VirtualFile> getRESOLVEPathRoots(@NotNull Project project, @Nullable Module module) {
    Collection<VirtualFile> roots = ContainerUtil.newArrayList();
    if (ResolveApplicationLibrariesService.getInstance().isUsingRESOLVEPathFromSystemEnvironment()) {
      roots.addAll(getRESOLVEPathsRootsFromEnvironment());
    }
    roots.addAll(module != null ?
                 ResolveLibrariesService.getUserDefinedLibraries(module) :
                 ResolveLibrariesService.getUserDefinedLibraries(project));
    return roots;
  }

  public static boolean isResolveSdkLibRoot(@NotNull VirtualFile root) {
    return root.isInLocalFileSystem() &&
           root.isDirectory() &&
           retrieveResolveVersion(root.getPath()) != null;
  }

  @Nullable
  public static String parseResolveVersion(@NotNull String text) {
    Matcher matcher = RESOLVE_VERSION_PATTERN.matcher(text);
    if (matcher.find()) {
      return matcher.group();
    }
    return null;
  }

  @NotNull
  static Collection<VirtualFile> getSdkDirectoriesToAttach(@NotNull String sdkPath) {
    // src is enough at the moment, possible process binaries from pkg
    return ContainerUtil.createMaybeSingletonList(getSdkSrcDir(sdkPath));
  }

  @Nullable
  private static VirtualFile getSdkSrcDir(@NotNull String sdkPath) {
    String srcPath = "src";
    VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(VfsUtilCore.pathToUrl(FileUtil.join(sdkPath, srcPath)));
    return file != null && file.isDirectory() ? file : null;
  }

  private static class RetrieveSubDirectoryOrSelfFunction implements Function<VirtualFile, VirtualFile> {
    @NotNull
    private final String subdirName;

    RetrieveSubDirectoryOrSelfFunction(@NotNull String subdirName) {
      this.subdirName = subdirName;
    }

    @Override
    public VirtualFile fun(VirtualFile file) {
      return file == null || FileUtil.namesEqual(subdirName, file.getName()) ? file : file.findChild(subdirName);
    }
  }
}
