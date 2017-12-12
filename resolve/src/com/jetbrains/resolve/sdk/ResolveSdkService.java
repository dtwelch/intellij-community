package com.jetbrains.resolve.sdk;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SimpleModificationTracker;
import org.jetbrains.annotations.NotNull;

//use this instead of the flavor stuff...
public class ResolveSdkService extends SimpleModificationTracker {

  @NotNull
  protected final Project project;

  protected ResolveSdkService(@NotNull Project project) {
    this.project = project;
  }

  public static ResolveSdkService getInstance(@NotNull Project project) {
    return ServiceManager.getService(project, ResolveSdkService.class);
  }

  
}
