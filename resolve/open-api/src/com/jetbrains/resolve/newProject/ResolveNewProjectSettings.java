package com.jetbrains.resolve.newProject;

import com.intellij.openapi.projectRoots.Sdk;
import org.jetbrains.annotations.Nullable;

public class ResolveNewProjectSettings {
  private Sdk mySdk;
  private boolean myInstallFramework;
  /**
   * Path on remote server for remote project
   */
  @Nullable
  private String myRemotePath;

  @Nullable
  public final Sdk getSdk() {
    return mySdk;
  }

  public final void setSdk(@Nullable final Sdk sdk) {
    mySdk = sdk;
  }

  public void setInstallFramework(final boolean installFramework) {
    myInstallFramework = installFramework;
  }

  public boolean installFramework() {
    return myInstallFramework;
  }

  public final void setRemotePath(@Nullable final String remotePath) {
      myRemotePath = remotePath;
  }

  @Nullable
  public final String getRemotePath() {
    return myRemotePath;
  }
}
