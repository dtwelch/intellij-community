package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

import static com.intellij.util.ArrayUtil.toObjectArray;
import static com.intellij.util.containers.ContainerUtil.newArrayList;

public class ResolveSdkSettingsConfigurable extends SearchableConfigurable.Parent.Abstract implements Configurable.NoScroll {

  @NotNull private final Project project;
  private ResolveGeneralConfigurationPanel mappings;

  public ResolveSdkSettingsConfigurable(@NotNull Project project) {
    this.project = project;
  }

  @Override
  public JComponent createComponent() {
    this.mappings = new ResolveGeneralConfigurationPanel(project);
    return this.mappings;
  }

  @NotNull
  @Override
  public String getId() {
    return "";
  }

  @Override
  protected Configurable[] buildConfigurables() {
    List<Configurable> result = newArrayList();
    result.add(new RESOLVEROOTConfigurable(project));
    result.add(new RESOLVEPATHConfigurable(project));
    return toObjectArray(result, Configurable.class);
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVE";
  }
}
