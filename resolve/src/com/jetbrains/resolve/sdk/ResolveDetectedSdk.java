package com.jetbrains.resolve.sdk;

import com.intellij.openapi.projectRoots.impl.ProjectJdkImpl;
import org.jetbrains.annotations.NotNull;

public class ResolveDetectedSdk extends ProjectJdkImpl {

  public ResolveDetectedSdk(@NotNull String name) {
    super(name, ResolveSdkType.getInstance());
    setHomePath(name);
  }
}
