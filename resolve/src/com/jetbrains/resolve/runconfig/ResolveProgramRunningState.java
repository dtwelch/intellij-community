package com.jetbrains.resolve.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.KillableColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.ResolveStudioController;
import com.jetbrains.resolve.action.ResolveValidateAction;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import edu.clemson.resolve.core.control.AbstractUserInterfaceControl;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
    if (sdk == null) {
      throw new ExecutionException("Can't generate code: Having an issue " +
                                   "finding the RESOLVE compiler (i.e. in Jetbrains speak: " +
                                   "the RESOLVE Sdk)...");
    }
    String classPath = sdk.getHomePath() + File.separator + "compiler" + File.separator +
                       ResolveSdkUtil.getResolveCompilerJarNameWithExt() + ":" + outputPath;

    String className = getClassName(project, filePath);

    File target = new File(filePath);
    if (!target.exists()) {
      throw new ExecutionException("Unexpected State: target file doesn't exist? " +
                                   "(this shouldn't be possible here)");
    }
    VirtualFile targetFileVf = LocalFileSystem.getInstance().findFileByIoFile(target);

    //cross compile from RESOLVE to java
    boolean successfullyGeneratedJava =
      generateAndWriteJava(project, targetFileVf, outputPath);

    if (!successfullyGeneratedJava) {
      ProcessHandler p =
        new KillableColoredProcessHandler(new GeneralCommandLine());
      //hmm, not sure about the line below..
      ResolveStudioController
        .getInstance(project)
        .console.attachToProcess(p);
      return p;
    }

    //Compile Java to bytecode and store in /out/ directory
    ProcessHandler javac =
      compileGeneratedJava(project, classPath, outputPath, filePath);
    if (javac != null) {
      return javac;
    }

    //Execute bytecode
    final KillableColoredProcessHandler processHandler =
      new KillableColoredProcessHandler(new GeneralCommandLine(
        "java",
        "-cp", classPath,
        className
      ));
    processHandler.setShouldDestroyProcessRecursively(true);
    return processHandler;
  }

  public boolean generateAndWriteJava(Project project, VirtualFile target, String outputPath) {
    List<String> args = new ArrayList<>();
    args.add("-genCode");
    args.add("-o");
    args.add(outputPath);
    AbstractUserInterfaceControl control =
      ResolveValidateAction.setupAndRunCompiler(project, "gencode", target, args,
                                                null);
    //this means we've succesfully generated (since we passed -genCode in)
    return control.getErrorManager().getErrorCount() == 0;
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

  @NotNull
  private File getOuputDirForFile(@NotNull Project project,
                                  @NotNull String baseOutputPath,
                                  @NotNull String filePath) {
    String path = FileUtil.getRelativePath(project.getBasePath(), filePath, File.separatorChar);
    path = baseOutputPath + File.separator + project.getName() + File.separator + path;
    return new File(path.substring(0, path.lastIndexOf("."))).getParentFile();
  }

  public ProcessHandler compileGeneratedJava(Project project,
                                             String effectiveClassPath,
                                             String outputPath,
                                             String filePath)
    throws ExecutionException {
    List<String> fileNames = new ArrayList<>();
    File baseOutFile = new File(outputPath);
    File newOutputDir = getOuputDirForFile(project, outputPath, filePath);
    final File[] fileList = newOutputDir.listFiles();
    if (fileList == null || fileList.length == 0) {
      return new KillableColoredProcessHandler(new GeneralCommandLine(
        "echo",
        "filelist could not be compiled, make sure .java files are being written to out/ directory"
      ));
    }

    for (File file : fileList) {
      if (file.getAbsolutePath().endsWith(".java")) {
        fileNames.add(file.getAbsolutePath());
      }
    }
    for (String file : fileNames) {
      KillableColoredProcessHandler kcp = new KillableColoredProcessHandler(
        new GeneralCommandLine("javac", "-cp", effectiveClassPath, "-d", baseOutFile.getPath(), file));
      try {
        if (kcp.getProcess().waitFor() > 0) {
          return kcp;
        }
      }
      catch (InterruptedException e) {
        return null;
      }
    }
    return null;
  }
}
