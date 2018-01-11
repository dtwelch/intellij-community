package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class ResolveSdkSettingsConfigurable extends SearchableConfigurable.Parent.Abstract implements Configurable.NoScroll {

  @NotNull private final Project project;
  private VcsDirectoryConfigurationPanel myMappings;
  private VcsGeneralConfigurationConfigurable myGeneralPanel;

  public ResolveSdkSettingsConfigurable(@NotNull Project project) {
    this.project = project;
  }

  @NotNull
  @Override
  public String getId() {
    return null;
  }

  @Override
  protected Configurable[] buildConfigurables() {
    return new Configurable[0];
  }

  @Nls
  @Override
  public String getDisplayName() {
    return null;
  }
}
