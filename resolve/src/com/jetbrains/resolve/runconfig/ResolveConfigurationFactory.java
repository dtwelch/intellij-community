package com.jetbrains.resolve.runconfig;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class ResolveConfigurationFactory extends ConfigurationFactory {

  private static final String FACTORY_NAME = "RESOLVE configuration factory";

  protected ResolveConfigurationFactory(@NotNull ConfigurationType type) {
    super(type);
  }

  @NotNull
  @Override
  public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
    return null;
  }

  @Override
  public String getName() {
    return FACTORY_NAME;
  }
}
