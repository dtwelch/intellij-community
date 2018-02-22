package com.jetbrains.resolve.newProject;

import com.intellij.ide.util.projectWizard.AbstractNewProjectDialog;
import com.jetbrains.resolve.newProject.steps.ResolveStudioNewProjectStep;

public class ResolveStudioNewProjectDialog extends AbstractNewProjectDialog {
  @Override
  protected ResolveStudioNewProjectStep createRootStep() {
    return new ResolveStudioNewProjectStep();
  }
}
