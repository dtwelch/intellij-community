package com.jetbrains.resolve.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;

public class ResolveProgramRunningState extends CommandLineState {

  protected final ResolveRunConfiguration configuration;

  protected ResolveProgramRunningState(@NotNull ExecutionEnvironment environment,
                                       @NotNull ResolveRunConfiguration configuration) {
    super(environment);
    this.configuration = configuration;
  }

  @NotNull
  public ResolveRunConfiguration getConfiguration() {
    return configuration;
  }

  @NotNull
  @Override
  protected ProcessHandler startProcess() throws ExecutionException {

    //Execute bytecode
    final KillableColoredProcessHandler processHandler = new KillableColoredProcessHandler(new GeneralCommandLine(
      "java",
      "-cp", "TODO",
      "TODO"
    ));
    processHandler.setShouldDestroyProcessRecursively(true);
    return processHandler;
  }
}
