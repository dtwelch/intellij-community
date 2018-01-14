package com.jetbrains.resolve.library;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.EnvironmentUtil;
import com.intellij.util.SystemProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ResolveEnvUtil {

  @Nullable
  public static String retrieveResolvePathFromEnv() {
    if (ApplicationManager.getApplication().isUnitTestMode()) return null;

    //If we can actually retrieve the system defined RESOLVEPATH, then do it.
    String path = EnvironmentUtil.getValue("RESOLVEPATH");
    if (path == null) {
      //If not, we settle for the default suggestion
      path = getDefaultResolvePath();
    }
    return path;
  }

  @NotNull
  public static String getDefaultResolvePath() {
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
      throw new IllegalStateException("Currently unrecognized/unsupported system..");
    }
    return result;
  }
}
