package com.jetbrains.resolve.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.runconfig.ui.ResolveRunSettingsEditor;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolveRunConfiguration extends RunConfigurationBase
    implements
      RunConfigurationWithSuppressedDefaultDebugAction,
      RunConfigurationWithSuppressedDefaultRunAction {

  private String filePath = "";
  protected ResolveRunConfiguration(@NotNull Project project,
                                    @NotNull ConfigurationFactory factory,
                                    String name) {
    super(project, factory, name);
  }

  //TODO: In the form, taken from the tutorial:
  //https://www.jetbrains.org/intellij/sdk/docs/tutorials/run_configurations.html
  //rename "script" to "client facility"

  @NotNull
  @Override
  public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    return new ResolveRunSettingsEditor(getProject());
  }

  @NotNull
  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(@NotNull String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void checkConfiguration() throws RuntimeConfigurationException {
    //TODO: maybe make sure its a resolve file containing a facility module (that its executable as well)
    // and that it's a path conformal project
    Project project = getProject();

    VirtualFile sdkResolveRootDir = ResolveSdkUtil.getSdkSrcDir(project);
    if (sdkResolveRootDir == null) {
      throw new RuntimeConfigurationError("RESOLVE Sdk missing...");
    }
    //check the file path... update: no, this is done in the file chooser.. see ResolveRunUtil#getFileChooser
  }

  @Nullable
  @Override
  public RunProfileState getState(@NotNull Executor executor,
                                  @NotNull ExecutionEnvironment environment)
    throws ExecutionException {
    return new ResolveProgramRunningState(environment, this);
  }
}
