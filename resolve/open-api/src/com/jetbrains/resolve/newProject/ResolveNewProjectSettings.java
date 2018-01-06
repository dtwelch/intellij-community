package com.jetbrains.resolve.newProject;

import com.intellij.openapi.projectRoots.Sdk;
import org.jetbrains.annotations.Nullable;

public class ResolveNewProjectSettings {
  private Sdk mySdk;

  @Nullable
  public final Sdk getSdk() {
    return mySdk;
  }

  public final void setSdk(@Nullable final Sdk sdk) {
    mySdk = sdk;
  }

}
