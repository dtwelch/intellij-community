package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.library.ResolveApplicationLibrariesService;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

import static com.intellij.util.ArrayUtil.toObjectArray;
import static com.intellij.util.containers.ContainerUtil.newArrayList;

public class ResolveSdkSettingsConfigurable extends SearchableConfigurable.Parent.Abstract implements Configurable.NoScroll {

  @NotNull private final Project project;
  private ResolveCompilerFlagsConfigurable general;

  public ResolveSdkSettingsConfigurable(@NotNull Project project) {
    this.project = project;
  }

  @Override
  public JComponent createComponent() {
    this.general = new ResolveCompilerFlagsConfigurable(project);
    return this.general;
  }

  @NotNull
  @Override
  public String getId() {
    return "";
  }

  @Override
  protected Configurable[] buildConfigurables() {
    List<Configurable> result = newArrayList();
    result.add(new ResolveActiveCompilerConfigurable(project));

    String[] urlsFromEnv = ContainerUtil.map2Array(ResolveSdkUtil.getResolvePathRootsFromEnvironment(), String.class, VirtualFile::getUrl);
    result.add(new ResolveLibraryPathConfigurable(project, ResolveApplicationLibrariesService.getInstance(), urlsFromEnv));

    return toObjectArray(result, Configurable.class);
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVE";
  }
}
