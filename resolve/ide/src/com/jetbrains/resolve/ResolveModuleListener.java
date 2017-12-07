package com.jetbrains.resolve;

import com.intellij.ProjectTopics;
import com.intellij.execution.RunManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;

/**
 * @author yole
 */
public class ResolveModuleListener {
  public ResolveModuleListener(MessageBus messageBus) {
    messageBus.connect().subscribe(ProjectTopics.MODULES, new ModuleListener() {
      @Override
      public void beforeModuleRemoved(@NotNull Project project, @NotNull Module module) {
        final RunManager runManager = RunManager.getInstance(project);
        /*for (RunnerAndConfigurationSettings configuration : runManager.getAllSettings()) {
          if (configuration.getConfiguration() instanceof AbstractPythonRunConfiguration) {
            final Module configModule = ((AbstractPythonRunConfiguration)configuration.getConfiguration()).getModule();
            if (configModule == module) {
              runManager.removeConfiguration(configuration);
            }
          }
        }*/
      }
    });    
  }
}
