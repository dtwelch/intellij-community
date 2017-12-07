package com.jetbrains.resolve.newProject.steps;

import com.intellij.facet.ui.ValidationResult;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.ResolveIcons;
import com.jetbrains.resolve.newProject.ResolveNewProjectSettings;
import com.jetbrains.resolve.newProject.ResolveProjectGenerator;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;

public class ResolveBaseProjectGenerator
    extends ResolveProjectGenerator<ResolveNewProjectSettings> {

  public ResolveBaseProjectGenerator() {
    super();
  }

  @NotNull
  @Nls
  @Override
  public String getName() {
    return "Pure Resolve";
  }

  @Override
  @Nullable
  public JComponent getSettingsPanel(File baseDir) throws ProcessCanceledException {
    return null;
  }

  @Override
  public Object getProjectSettings() {
    return new ResolveNewProjectSettings();
  }

  @Nullable
  @Override
  public Icon getLogo() {
    return ResolveIcons.Resolve;
  }

  @Override
  public void configureProject(
      @NotNull final Project project,
      @NotNull VirtualFile baseDir,
      @NotNull final ResolveNewProjectSettings settings,
      @NotNull final Module module) {
    ApplicationManager.getApplication()
        .runWriteAction(() -> ModuleRootModificationUtil.setModuleSdk(module, settings.getSdk()));
  }

  @NotNull
  @Override
  public ValidationResult validate(@NotNull String baseDirPath) {
    return ValidationResult.OK;
  }
}
