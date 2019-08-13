package com.jetbrains.resolve.runconfig;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.openapi.extensions.Extensions;
import icons.ResolveIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ResolveRunConfigurationType implements ConfigurationType {

  @NotNull
  public static ResolveRunConfigurationType getInstance() {
    return Extensions.findExtension(CONFIGURATION_TYPE_EP, ResolveRunConfigurationType.class);
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVE";
  }

  @Nls
  @Override
  public String getConfigurationTypeDescription() {
    return "RESOLVE Run Configuration Type";
  }

  @Override
  public Icon getIcon() {
    return ResolveIcons.RESOLVE;
  }

  @NotNull
  @Override
  public String getId() {
    return "RESOLVE_RUN_CONFIGURATION";
  }

  @Override
  public ConfigurationFactory[] getConfigurationFactories() {
    return new ConfigurationFactory[]{new ResolveConfigurationFactory(this)};
  }
}
