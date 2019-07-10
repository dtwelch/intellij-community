package com.jetbrains.resolve.runconfig;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.jetbrains.resolve.psi.ResFile;
import com.jetbrains.resolve.runconfig.ui.ResolveRunSettingsEditor;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ResolveRunConfiguration extends LocatableConfigurationBase
    implements
      RunConfigurationWithSuppressedDefaultDebugAction,
      RunConfigurationWithSuppressedDefaultRunAction {

  private static final String FILE_PATH_ATTRIBUTE_NAME = "filePath";

  private String filePath = "";

  protected ResolveRunConfiguration(@NotNull Project project,
                                    @NotNull ConfigurationFactory factory,
                                    String name) {
    super(project, factory, name);
  }

  @Override
  public void readExternal(@NotNull Element element) throws InvalidDataException {
    super.readExternal(element);
    this.filePath = StringUtil.notNullize(
      JDOMExternalizerUtil.getFirstChildValueAttribute(element, FILE_PATH_ATTRIBUTE_NAME));
  }

  @Override
  public void writeExternal(Element element) throws WriteExternalException {
    super.writeExternal(element);
    if (StringUtil.isNotEmpty(filePath)) {
      JDOMExternalizerUtil.addElementWithValueAttribute(element, FILE_PATH_ATTRIBUTE_NAME, filePath);
    }
  }

  @NotNull
  @Override
  public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    return new ResolveRunSettingsEditor(getProject());
  }

  @NotNull
  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(@NotNull String filePath) {
    this.filePath = filePath;
  }

  @Override
  public void checkConfiguration() throws RuntimeConfigurationException {
    //TODO: maybe make sure its a resolve file containing a facility module (that its executable as well)
    // and that it's a path conformal project
    Project project = getProject();

    VirtualFile sdkResolveRootDir = ResolveSdkUtil.getSdkSrcDir(project);
    if (sdkResolveRootDir == null) {
      throw new RuntimeConfigurationError("RESOLVE Sdk missing...");
    }

    if (filePath == null || filePath.isEmpty()) {
      throw new RuntimeConfigurationError("Main Facility not specified");
    }

    //if it is specified, make sure it actually points to a facility file with a main..
    File file = new File(filePath);
    if (!file.exists()) {
      throw new RuntimeConfigurationError("Main Facility file not found");
    }
    VirtualFile targetFileVf = LocalFileSystem.getInstance().findFileByIoFile(file);
    if (targetFileVf == null) {
      throw new RuntimeConfigurationError("Main Facility file not found");
    }

    PsiFile psiFile = PsiManager.getInstance(getProject()).findFile(targetFileVf);
    if (!(psiFile instanceof ResFile)) {
      throw new RuntimeConfigurationError("Main file is invalid (not a .resolve file)");
    }
    if (!ResolveRunUtil.isMainRESOLVEFile(psiFile)) {
      throw new RuntimeConfigurationError("Main file is not a facility and/or doesn't contain main function");
    }
  }

  @Nullable
  @Override
  public RunProfileState getState(@NotNull Executor executor,
                                  @NotNull ExecutionEnvironment environment)
    throws ExecutionException {
    return new ResolveProgramRunningState(environment, this);
  }
}
