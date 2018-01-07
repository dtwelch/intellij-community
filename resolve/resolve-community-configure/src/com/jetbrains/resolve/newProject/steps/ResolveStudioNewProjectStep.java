package com.jetbrains.resolve.newProject.steps;

import com.intellij.ide.util.projectWizard.AbstractNewProjectStep;
import com.intellij.ide.util.projectWizard.ProjectSettingsStepBase;
import com.intellij.ide.util.projectWizard.actions.ProjectSpecificAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.platform.DirectoryProjectGenerator;
import com.jetbrains.resolve.newProject.ResolveProjectGenerator;
import org.jetbrains.annotations.NotNull;

public class ResolveStudioNewProjectStep extends AbstractNewProjectStep {

  public ResolveStudioNewProjectStep() {
    super(new Customization());
  }

  protected static class Customization extends AbstractNewProjectStep.Customization {

    @NotNull
    @Override
    protected AbstractCallback createCallback() {
      return new ResolveGenerateProjectCallback();
    }

    @NotNull
    @Override
    protected DirectoryProjectGenerator createEmptyProjectGenerator() {
      return new ResolveProjectGenerator();
    }

    @NotNull
    @Override
    protected ProjectSettingsStepBase createProjectSpecificSettingsStep(@NotNull DirectoryProjectGenerator projectGenerator,
                                                                        @NotNull AbstractCallback callback) {
      return new ProjectSpecificSettingsStep<>(projectGenerator, callback);
    }

    @NotNull
    @Override
    protected DirectoryProjectGenerator[] getProjectGenerators() {
      return super.getProjectGenerators();
    }

    @Override
    public void setUpBasicAction(@NotNull ProjectSpecificAction projectSpecificAction, @NotNull DirectoryProjectGenerator[] generators) {
      if (generators.length == 0) {
        projectSpecificAction.setPopup(false);
      }
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public AnAction[] getActions(@NotNull DirectoryProjectGenerator generator, @NotNull AbstractCallback callback) {
      if (generator instanceof ResolveProjectGenerator) {
        ProjectSpecificAction group =
          new ProjectSpecificAction(generator,
                                    new ProjectSpecificSettingsStep<ResolveStudioNewProjectStep>(generator,
                                                                                                 new ResolveGenerateProjectCallback()));
        return group.getChildren(null);
      }
      else {
        return AnAction.EMPTY_ARRAY;
      }
    }

    @NotNull
    @Override
    public AnAction[] getExtraActions(@NotNull AbstractCallback callback) {
      return AnAction.EMPTY_ARRAY;
    }
  }
}
