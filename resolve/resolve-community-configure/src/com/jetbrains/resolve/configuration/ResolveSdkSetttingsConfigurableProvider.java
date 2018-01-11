package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableProvider;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class ResolveSdkSetttingsConfigurableProvider extends ConfigurableProvider {

  @NotNull private final Project project;

  public ResolveSdkSetttingsConfigurableProvider(@NotNull Project project) {
    this.project = project;
  }

  @NotNull
  @Override
  public Configurable createConfigurable() {
    return new ResolveSdkSettingsConfigurable(project);
  }

  @Override
  public boolean canCreateConfigurable() {
    return true;
  }
}
