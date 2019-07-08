// Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jetbrains.resolve.sdk;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.ui.JBDefaultTreeCellRenderer;
import com.intellij.util.Function;
import com.intellij.util.SystemProperties;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.ResolveBundle;
import com.jetbrains.resolve.ResolveConstants;
import com.jetbrains.resolve.library.ResolveApplicationLibrariesService;
import com.jetbrains.resolve.library.ResolveLibrariesService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.intellij.util.containers.ContainerUtil.newLinkedHashSet;

public class ResolveSdkUtil {
  protected static final Logger LOG = Logger.getInstance("#com.jetbrains.resolve.sdk.ResolveSdkUtil");

  // \w   A word character, short for [a-zA-Z_0-9]
  // \s   A whitespace character
  // \d   Any digit [0-9]
  //So this pattern allows matching things like 1.1rc2
  private static final Pattern RESOLVE_VERSION_PATTERN = Pattern.compile("[\\d.]+\\w+(\\d+)?");

  private static final Key<String> RESOLVE_VER_DATA_KEY = Key.create("RESOLVE_VERSION_KEY");

  @NotNull
  public static FileChooserDescriptor getResolveWorkspaceChooserDescriptor() {
    return new FileChooserDescriptor(false, true, false, false, false, false) {
      @Override
      public void validateSelectedFiles(VirtualFile[] files) throws Exception {
        if (files.length != 0) {
          VirtualFile selectedFile = files[0];
          if (!selectedFile.getName().equals(ResolveConstants.RESOLVEPATH_DIR_NAME)) {
            throw new Exception(ResolveBundle.message("sdk.error.unrecognized.workspace.name", selectedFile));
          }
        }
      }
    }.withTitle(ResolveBundle.message("sdk.select.workspace.path")).withShowHiddenFiles(false);
  }

  @NotNull
  public static FileChooserDescriptor getResolveRootChooserDescriptor() {
    return new FileChooserDescriptor(false, true, false, false, false, false) {
      @Override
      public void validateSelectedFiles(VirtualFile[] files) throws Exception {
        if (files.length != 0) {

          if (!isValidSdkHome(files[0].getPath())) {
            throw new Exception(ResolveBundle.message("sdk.error.invalid.compiler.name", files[0].getName()));
          }
        }
      }
    }.withTitle(ResolveBundle.message("sdk.select.compiler.path")).withShowHiddenFiles(false);
  }

  //TODO: Perhaps also insist that "valid" sdk home dirs have a well-formed VERSION.txt file...
  public static boolean isValidSdkHome(String path) {
    LOG.debug("Validating Resolve sdk path: " + path);
    String jarPath = getResolveCompilerJarPath(path);
    if (jarPath == null) {
      LOG.debug("Resolve compiler jar is not found.. ");
      return false;
    }
    return true;
  }

  @NotNull
  public static String getResolveCompilerJarNameWithExt() {
    return "resolve.jar";
  }

  @Nullable
  protected static String getResolveCompilerJarPath(@Nullable String sdkHomePath) {
    if (sdkHomePath != null) {
      File compiler = new File(sdkHomePath + File.separator + "compiler" +
                               File.separator + getResolveCompilerJarNameWithExt());
      if (compiler.exists()) {
        return compiler.getAbsolutePath();
      }
    }
    return null;
  }

  /**
   * Retrieves root directories from RESOLVEPATH env-variable. This method doesn't consider user defined libraries,
   * for that case use {@link {@link this#getRESOLVEPathRoots(Project, Module)}.
   * <p>
   * Note that right now, <tt>RESOLVEPATH</tt> is a single entity as opposed to a list of 'roots'. I've just kept it
   * in list form for simplicity for now (the original GoLang plugin did it this way because Go apparently supports
   * multiple semicolon-delimited GOPATHs)
   * <p>
   * So in other words, the returned collection should always be a singleton.
   */
  @NotNull
  public static Collection<VirtualFile> getResolvePathRootsFromEnvironment() {
    return ResolveEnvironmentPathModificationTracker.getResolveEnvironmentPathRoots();
  }

  @NotNull
  public static File findSequentNonExistingResolveBaseDir() {
    String resolveWorkBaseDir = suggestResolveWorkspaceDirectory();
    return FileUtil.findSequentNonexistentFile(new File(resolveWorkBaseDir), "newComponent", "");
  }

  /**
   * Returns a suggested directory for where a user may consider storing their <tt>resolvework</tt>.
   */
  //TODO: Create diff suggestions for Windows.
  @NotNull
  public static String suggestResolveWorkspaceDirectory() {
    final String userHome = SystemProperties.getUserHome();
    return userHome.replace('/', File.separatorChar) + File.separator + "Documents"
           + File.separator + "resolvework" + File.separator + "src";
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
        VirtualFile versionFile =
          sdkRoot.findFileByRelativePath("/compiler/resources/edu/clemson/resolve/util/version");

        if (versionFile != null) {
          String text = VfsUtilCore.loadText(versionFile);
          String version = parseResolveVersion(text);
          if (version == null) {
            LOG.debug("Cannot parse Resolve version in VERSION.txt");
          }
          return version;
        }
        else {
          LOG.debug("Cannot find Resolve version file in sdk path: " + sdkPath);
        }
      }
    }
    catch (IOException e) {
      LOG.debug("Cannot find Resolve version file in sdk path: " + sdkPath, e);
    }
    return null;
  }

  //TODO: Figure out if we really need this.
  @NotNull
  public static LinkedHashSet<VirtualFile> getSourcesPathsToLookup(@NotNull Project project) {
    LinkedHashSet<VirtualFile> sdkAndPathSrcs = newLinkedHashSet();
    ContainerUtil.addIfNotNull(sdkAndPathSrcs, getSdkSrcDir(project));

    //Collection<VirtualFile> pathSrcs = getResolvePathSourcesRoot(project);
    ContainerUtil.addAllNotNull(sdkAndPathSrcs, getResolvePathSourcesRoot(project));
    return sdkAndPathSrcs;
  }

  @NotNull
  public static Collection<VirtualFile> getResolvePathSourcesRoot(@NotNull final Project project) {
    return CachedValuesManager
      .getManager(project)
      .getCachedValue(project, new CachedValueProvider<Collection<VirtualFile>>() {
        @Override
        public Result<Collection<VirtualFile>> compute() {
          return Result.create(getRESOLVEPathSourcesRootInner(project),
                               getSdkAndLibrariesCacheDependencies(project));
          //JBDefaultTreeCellRenderer
        }
      });
  }

  @Nullable
  public static VirtualFile getResolveRootDir(@NotNull final Project project) {
    List<VirtualFile> x = new ArrayList<>(getResolvePathSourcesRoot(project));
    return (x.size() == 2) ? x.get(1) : null;
  }

  @Nullable
  public static VirtualFile getSrcDirRootForResolvePath(@NotNull final Project project) {
    List<VirtualFile> x = new ArrayList<>(getResolvePathSourcesRoot(project));
    return x.get(0);
  }

  @NotNull
  private static Collection<Object> getSdkAndLibrariesCacheDependencies(@NotNull Project project) {
    Collection<Object> dependencies = ContainerUtil.newArrayList(
      (Object[])ResolveLibrariesService.getModificationTrackers(project));
    ContainerUtil.addAllNotNull(dependencies);
    return dependencies;
  }

  @NotNull
  private static List<VirtualFile> getRESOLVEPathSourcesRootInner(@NotNull Project project) {
    return ContainerUtil.mapNotNull(getRESOLVEPathRoots(project),
                                    new RetrieveSubDirectoryOrSelfFunction("src"));
  }

  @NotNull
  private static Collection<VirtualFile> getRESOLVEPathRoots(@NotNull Project project) {
    Collection<VirtualFile> roots = ContainerUtil.newArrayList();
    if (ResolveApplicationLibrariesService.getInstance().isUseResolvePathFromSystemEnvironment()) {
      roots.addAll(getResolvePathRootsFromEnvironment());
    }
    roots.addAll(ResolveLibrariesService.getUserDefinedLibraries(project));
    return roots;
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
    return ContainerUtil.createMaybeSingletonList(getSdkSrcDir(sdkPath));
  }

  @Nullable
  public static VirtualFile getSdkSrcDir(@NotNull Project project) {
    Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
    return sdk != null && sdk.getHomePath() != null ? getSdkSrcDir(sdk.getHomePath()) : null;
  }

  @Nullable
  private static VirtualFile getSdkSrcDir(@NotNull String sdkPath) {
    String srcPath = "src";
    VirtualFile file = VirtualFileManager
      .getInstance()
      .findFileByUrl(VfsUtilCore.pathToUrl(FileUtil.join(sdkPath, srcPath)));
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
