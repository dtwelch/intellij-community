package com.jetbrains.resolve.library;

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

  //THE IDE Won't Attempt to read environmental variables. It will instead rely on configurables (see preferences menu)
    //if you run the compiler from the commandline, these of course are fine. If they are set, the IDE doesn't care about them
    //the onus is on the user to make sure the IDE settings reflect their preferred ROOT and PATH dir. And of course there are defaults
    //so...
  //String path = EnvironmentUtil.getValue("RESOLVEPATH");
  //if (path == null) {
    String path = getDefaultPath(); //TODO: For now... eventually retrieve it from seom configurable...
  //}
  //else {
  //  path = PathMacros.getInstance().getValue("RESOLVEPATH");
  //}
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
      throw new IllegalStateException("Currently unrecognized/unsupported system..");
    }
    return result;
  }
}
