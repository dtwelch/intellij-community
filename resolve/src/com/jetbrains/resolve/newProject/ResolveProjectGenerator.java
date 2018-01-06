package com.jetbrains.resolve.newProject;

import com.intellij.facet.ui.ValidationResult;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.DirectoryProjectGeneratorBase;
import com.intellij.util.BooleanFunction;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public abstract class ResolveProjectGenerator<T extends ResolveNewProjectSettings> extends DirectoryProjectGeneratorBase<T> {
  private static final Logger LOGGER = Logger.getInstance(ResolveProjectGenerator.class);

  private final List<SettingsListener> myListeners = ContainerUtil.newArrayList();
  @Nullable private MouseListener myErrorLabelMouseListener;

  protected Consumer<String> myErrorCallback;

  protected ResolveProjectGenerator() {}

  public final void setErrorCallback(@NotNull final Consumer<String> errorCallback) {
    myErrorCallback = errorCallback;
  }

  @Nullable
  public JComponent getSettingsPanel(File baseDir) throws ProcessCanceledException {
    return null;
  }

  @Nullable
  public JPanel extendBasePanel() throws ProcessCanceledException {
    return null;
  }

  @Override
  public final void generateProject(
      @NotNull final Project project,
      @NotNull final VirtualFile baseDir,
      @NotNull final T settings,
      @NotNull final Module module) {
    final Sdk sdk = settings.getSdk();
    /*if (sdk instanceof PyLazySdk) {
      final Sdk createdSdk = ((PyLazySdk) sdk).create();
      settings.setSdk(createdSdk);
      if (createdSdk != null) {
        SdkConfigurationUtil.addSdk(createdSdk);
      }
    }*/

    configureProject(project, baseDir, settings, module);
  }

  /**
   * Same as {@link #configureProject(Project, VirtualFile, PyNewProjectSettings, Module,
   * PyProjectSynchronizer)} but with out of settings. Called by Intellij Plugin when framework is
   * installed as project template.
   */
  protected void configureProjectNoSettings(
      @NotNull final Project project,
      @NotNull final VirtualFile baseDir,
      @NotNull final Module module) {
    throw new IllegalStateException(
        String.format(
            "%s does not support project creation with out of settings. " + "See %s doc for detail",
            getClass(), ResolveProjectGenerator.class));
  }

  protected abstract void configureProject(
      @NotNull final Project project,
      @NotNull final VirtualFile baseDir,
      @NotNull final T settings,
      @NotNull final Module module);

  public Object getProjectSettings() {
    return new ResolveNewProjectSettings();
  }

  public ValidationResult warningValidation(@Nullable final Sdk sdk) {
    return ValidationResult.OK;
  }

  public void addSettingsStateListener(@NotNull SettingsListener listener) {
    myListeners.add(listener);
  }

  public void locationChanged(@NotNull final String newLocation) {}

  public interface SettingsListener {
    void stateChanged();
  }

  public void fireStateChanged() {
    for (SettingsListener listener : myListeners) {
      listener.stateChanged();
    }
  }

  @Nullable
  public BooleanFunction<ResolveProjectGenerator> beforeProjectGenerated(@Nullable final Sdk sdk) {
    return null;
  }

  /**
   * @deprecated This method no longer has any effect. The standard interpreter chooser UI is always
   *     shown.
   */
  @Deprecated
  @Contract(" -> false")
  public boolean hideInterpreter() {
    return false;
  }

  public void addErrorLabelMouseListener(@NotNull final MouseListener mouseListener) {
    myErrorLabelMouseListener = mouseListener;
  }

  @Nullable
  public MouseListener getErrorLabelMouseListener() {
    return myErrorLabelMouseListener;
  }

}
