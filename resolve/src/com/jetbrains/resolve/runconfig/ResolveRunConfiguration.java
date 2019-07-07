package com.jetbrains.resolve.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolveRunConfiguration extends RunConfigurationBase
    implements
      RunConfigurationWithSuppressedDefaultDebugAction,
      RunConfigurationWithSuppressedDefaultRunAction {

  protected ResolveRunConfiguration(@NotNull Project project,
                                    @NotNull ConfigurationFactory factory,
                                    String name) {
    super(project, factory, name);
  }

  @NotNull
  @Override
  public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    return null;
  }

  @Nullable
  @Override
  public RunProfileState getState(@NotNull Executor executor,
                                  @NotNull ExecutionEnvironment environment)
    throws ExecutionException {
    return new ResolveProgramRunningState(environment, this);

  }
}
