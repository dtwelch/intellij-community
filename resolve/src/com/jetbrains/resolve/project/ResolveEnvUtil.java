package com.jetbrains.resolve.project;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathMacros;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.EnvironmentUtil;
import com.intellij.util.SystemProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ResolveEnvUtil {

  @Nullable
  public static String retrieveRESOLVEPath() {
  if (ApplicationManager.getApplication().isUnitTestMode()) return null;

  String path = EnvironmentUtil.getValue("RESOLVEPATH");
  if (path == null) {
    path = getDefaultPath();
  }
  else {
    path = PathMacros.getInstance().getValue("RESOLVEPATH");
  }
  return path;
  }

  @NotNull
  public static String getDefaultPath() {
    String result = null;
    String userHome = SystemProperties.getUserHome();
    if (SystemInfo.isMac) {
      result = userHome.replace('/', File.separatorChar) + File.separator + "Documents"
               + File.separator + "resolvework";
    }
    else if (SystemInfo.isWindows) {
      result = "C:\\resolvework";
    }
    else {
      result = ".";
    }
    return result;
  }
}
