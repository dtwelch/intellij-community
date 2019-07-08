package com.jetbrains.resolve.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.action.ResolveValidateAction;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;

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

    Project project = configuration.getProject();
    String filePath = configuration.getFilePath();
    String outputPath = project.getBasePath() + File.separator + "out";

    String classPath = ResolveSdkUtil.getResolveRootDir(project) + File.separator + "compiler" +
                       ResolveSdkUtil.getResolveCompilerJarNameWithExt() + ":" + outputPath;

    String className = getClassName(project, filePath);

    File target = new File(filePath);
    if (!target.exists()) {
      throw new ExecutionException("Unexpected State: target file doesn't exist? " +
                                   "(this shouldn't be possible here)");
    }
    VirtualFile targetFileVf = LocalFileSystem.getInstance().findFileByIoFile(target);

    //cross compile from RESOLVE to java
    boolean successfullyGeneratedJava = generateAndWriteJava(project, filePath, outputPath);

    int i;
    i=0;
    //Execute bytecode
    final KillableColoredProcessHandler processHandler = new KillableColoredProcessHandler(new GeneralCommandLine(
      "java",
      "-cp", "TODO",
      "TODO"
    ));
    processHandler.setShouldDestroyProcessRecursively(true);
    return processHandler;
  }

  public boolean generateAndWriteJava(Project project, String filePath, String outputPath) {
   // ResolveValidateAction.setupAndRunCompiler(project, "gencode", )
    return false;
  }

  @NotNull
  private String getClassName(@NotNull Project project, @NotNull String filePath)
    throws ExecutionException {
    if (project.getBasePath() == null) {
      throw new ExecutionException("project.basePath == null");
    }
    String result = FileUtil.getRelativePath(project.getBasePath(), filePath, File.separatorChar);
    result = result.substring(0, result.lastIndexOf('.'));
    result = project.getName() + File.separator + result;
    return result.replaceAll(File.separator, ".");
  }
}
