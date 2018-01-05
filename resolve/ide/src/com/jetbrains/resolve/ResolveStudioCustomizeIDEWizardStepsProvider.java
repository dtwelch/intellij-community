package com.jetbrains.resolve;

import com.intellij.ide.customize.*;
import com.intellij.openapi.util.Pair;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class ResolveStudioCustomizeIDEWizardStepsProvider implements CustomizeIDEWizardStepsProvider {
  @Override
  public void initSteps(CustomizeIDEWizardDialog wizardDialog, List<AbstractCustomizeWizardStep> steps) {
    PluginGroups groups = new PluginGroups() {
      @Override
      protected void initGroups(Map<String, Pair<Icon, List<String>>> tree, Map<String, String> featuredPlugins) {
        addMarkdownPlugin(featuredPlugins);
      }
    };
    /*
    steps.add(new CustomizeUIThemeStepPanel());
    if (CustomizeLauncherScriptStep.isAvailable()) {
      steps.add(new CustomizeLauncherScriptStep());
    }
    steps.add(new CustomizeFeaturedPluginsStepPanel(groups));*/
  }
}
