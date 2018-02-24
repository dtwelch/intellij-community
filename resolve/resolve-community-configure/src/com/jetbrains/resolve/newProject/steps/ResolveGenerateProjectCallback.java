package com.jetbrains.resolve.newProject.steps;

import com.intellij.ide.util.projectWizard.AbstractNewProjectStep;
import com.intellij.ide.util.projectWizard.ProjectSettingsStepBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.roots.ModifiableModelsProvider;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.platform.DirectoryProjectGenerator;
import com.intellij.platform.ProjectGeneratorPeer;
import com.jetbrains.resolve.newProject.ResolveNewProjectSettings;
import com.jetbrains.resolve.newProject.ResolveProjectGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolveGenerateProjectCallback<T> extends AbstractNewProjectStep.AbstractCallback<T> {

  /**
   * This gets called when a user, on the SDK and project location selection screen, clicks create. So we can add each
   * sdk in the combo box chooser to the projectSdkModel and whichever one is selected is the one for which we finally
   * call {@code SdkConfigurationUtil.setDirectoryProjectSdk(newProject, sdk)}.
   * @param step
   * @param projectGeneratorPeer
   */
  @Override
  public void consume(@Nullable ProjectSettingsStepBase<T> step, @NotNull ProjectGeneratorPeer<T> projectGeneratorPeer) {
    if (!(step instanceof ProjectSpecificSettingsStep)) return;

    final ProjectSpecificSettingsStep settingsStep = (ProjectSpecificSettingsStep)step;
    final DirectoryProjectGenerator generator = settingsStep.getProjectGenerator();
    Sdk sdk = settingsStep.getSdk();  //this will setup the sdk and give it to us.. then here we'll add it...

    //
    final Object settings = computeProjectSettings(generator, settingsStep, projectGeneratorPeer, sdk);
    final Project newProject = generateProject(settingsStep, settings);

    if (newProject != null && generator instanceof ResolveProjectGenerator) {
      SdkConfigurationUtil.setDirectoryProjectSdk(newProject, sdk);
    }
  }

  @Nullable
  private static Project generateProject(@NotNull final ProjectSettingsStepBase settings, @Nullable Object generationSettings) {
    if (generationSettings == null) return null;
    final DirectoryProjectGenerator generator = settings.getProjectGenerator();
    final String location = FileUtil.expandUserHome(settings.getProjectLocation());
    return AbstractNewProjectStep.doGenerateProject(null, location, generator, generationSettings);
  }

  @Nullable
  public static Object computeProjectSettings(DirectoryProjectGenerator<?> generator,
                                              final ProjectSpecificSettingsStep settingsStep,
                                              @NotNull final ProjectGeneratorPeer projectGeneratorPeer, @Nullable Sdk sdk) {
    Object projectSettings = null;
    if (generator instanceof ResolveProjectGenerator) {
      final ResolveProjectGenerator projectGenerator = (ResolveProjectGenerator)generator;
      projectSettings = projectGenerator.getProjectSettings();
    }
    if (projectSettings instanceof ResolveNewProjectSettings) {
      final ResolveNewProjectSettings newProjectSettings = (ResolveNewProjectSettings)projectSettings;
      newProjectSettings.setSdk(sdk);
    }
    return projectSettings;
  }
}
