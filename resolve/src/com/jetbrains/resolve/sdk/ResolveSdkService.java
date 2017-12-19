package com.jetbrains.resolve.sdk;

import com.intellij.execution.configurations.PathEnvironmentVariableUtil;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

//use this instead of the flavor stuff...
public class ResolveSdkService extends SimpleModificationTracker {
  public static final Logger LOG = Logger.getInstance(ResolveSdkService.class);

  @NotNull
  protected final Project project;

  protected ResolveSdkService(@NotNull Project project) {
    this.project = project;
  }

  public static ResolveSdkService getInstance(@NotNull Project project) {
    return ServiceManager.getService(project, ResolveSdkService.class);
  }


  public static String getResolveCompilerJarPath(@Nullable String sdkHomePath) {
    if (sdkHomePath != null) {
      File compiler = new File(sdkHomePath + File.separator + "compiler" + File.separator + "resolve.jar");
      if (compiler.exists()) {
        return compiler.getAbsolutePath();
      }
    }
    return null;
  }
}
