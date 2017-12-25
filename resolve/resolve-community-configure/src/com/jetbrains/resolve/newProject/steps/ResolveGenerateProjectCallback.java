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

  @Override
  public void consume(@Nullable ProjectSettingsStepBase<T> step, @NotNull ProjectGeneratorPeer<T> projectGeneratorPeer) {
    if (!(step instanceof ProjectSpecificSettingsStep)) return;

    final ProjectSpecificSettingsStep settingsStep = (ProjectSpecificSettingsStep)step;
    final DirectoryProjectGenerator generator = settingsStep.getProjectGenerator();
    Sdk sdk = settingsStep.getOrCreateSdk();

    final Object settings = computeProjectSettings(generator, settingsStep, projectGeneratorPeer);
    final Project newProject = generateProject(settingsStep, settings);

    if (newProject != null && generator instanceof ResolveProjectGenerator) {
      ModuleManager x =  ModuleManager.getInstance(newProject);
      Module[] vb =  x.getModules();

      final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.SERVICE.getInstance();
      final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(vb[0]);
      String N = model.getSdkName();
      Sdk m = model.getSdk();

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
                                              @NotNull final ProjectGeneratorPeer projectGeneratorPeer) {
    Object projectSettings = null;
    if (generator instanceof ResolveProjectGenerator) {
      final ResolveProjectGenerator<?> projectGenerator = (ResolveProjectGenerator<?>)generator;
      projectSettings = projectGenerator.getProjectSettings();
    }
    if (projectSettings instanceof ResolveNewProjectSettings) {
      final ResolveNewProjectSettings newProjectSettings = (ResolveNewProjectSettings)projectSettings;
      newProjectSettings.setSdk(settingsStep.getOrCreateSdk());
      //newProjectSettings.setInstallFramework(settingsStep.installFramework());
      //newProjectSettings.setRemotePath(settingsStep.getRemotePath());
    }
    return projectSettings;
  }
}
