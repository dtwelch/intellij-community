package com.jetbrains.resolve.newProject;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.DirectoryProjectGeneratorBase;
import com.intellij.util.BooleanFunction;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.ResolveIcons;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class ResolveProjectGenerator extends DirectoryProjectGeneratorBase<ResolveNewProjectSettings> {
  private static final Logger LOGGER = Logger.getInstance(ResolveProjectGenerator.class);

  private final List<SettingsListener> myListeners = ContainerUtil.newArrayList();
  protected Consumer<String> myErrorCallback;

  public final void setErrorCallback(@NotNull final Consumer<String> errorCallback) {
    myErrorCallback = errorCallback;
  }

  @Override
  public final void generateProject(@NotNull final Project project,
                                    @NotNull final VirtualFile baseDir,
                                    @NotNull final ResolveNewProjectSettings settings,
                                    @NotNull final Module module) {
    ApplicationManager.getApplication().runWriteAction(() -> ModuleRootModificationUtil.setModuleSdk(module, settings.getSdk()));
  }

  public Object getProjectSettings() {
    return new ResolveNewProjectSettings();
  }

  public void addSettingsStateListener(@NotNull SettingsListener listener) {
    myListeners.add(listener);
  }

  @NotNull
  @Nls
  @Override
  public String getName() {
    return "Pure Resolve";
  }

  @Nullable
  @Override
  public Icon getLogo() {
    return ResolveIcons.RESOLVE;
  }

  public void fireStateChanged() {
    for (SettingsListener listener : myListeners) {
      listener.stateChanged();
    }
  }

  public interface SettingsListener {
    void stateChanged();
  }
}
