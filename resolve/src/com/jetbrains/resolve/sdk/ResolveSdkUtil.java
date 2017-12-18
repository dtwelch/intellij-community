// Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jetbrains.resolve.sdk;

import com.intellij.ide.RecentProjectsManager;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.PlatformUtils;
import com.intellij.util.SystemProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

  @NotNull
  public static File findSequentNonExistingResolveBaseDir() {
    String resolveWorkBaseDir = getResolvePathDefaultBaseDir();
    return FileUtil.findSequentNonexistentFile(new File(resolveWorkBaseDir), "newComponent", "");
  }

  @NotNull
  public static List<Sdk> findBaseSdks(@NotNull List<Sdk> existingSdks) {
    VirtualFile sdkDir = suggestSdkDirectory();
    Set<String> existingPaths = existingSdks.stream().map(e -> e.getHomePath()).collect(Collectors.toSet());
    List<Sdk> result = new ArrayList<>(existingSdks);
    if (sdkDir != null && !existingPaths.contains(sdkDir.getPath())) {
      result.add(new ResolveDetectedSdk(sdkDir.getPath()));
    }
    return result;
  }

  @NotNull
  public static List<Sdk> findBaseSdks() {
    return findBaseSdks(Collections.emptyList());
  }

  @Nullable
  public static VirtualFile suggestSdkDirectory() {
    if (SystemInfo.isWindows) {
      return LocalFileSystem.getInstance().findFileByPath("C:\\resolve-lite-private");
    }
    if (SystemInfo.isMac || SystemInfo.isLinux) {
      VirtualFile usrLocal = LocalFileSystem.getInstance().findFileByPath("/usr/local/resolve-lite-private");
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

  @Nullable
  public static String parseResolveVersion(@NotNull String text) {
    Matcher matcher = RESOLVE_VERSION_PATTERN.matcher(text);
    if (matcher.find()) {
      return matcher.group();
    }
    return null;
  }

}
