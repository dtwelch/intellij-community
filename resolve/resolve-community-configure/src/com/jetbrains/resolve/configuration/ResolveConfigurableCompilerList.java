package com.jetbrains.resolve.configuration;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.jetbrains.resolve.sdk.ResolveSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * From yole: Manages the SDK model shared between PythonSdkConfigurable and PyActiveSdkConfigurable.
 *
 * @author dtwelch (tweaked from original sources by yole)
 */
public class ResolveConfigurableCompilerList {
  private ProjectSdksModel myModel;

  public static ResolveConfigurableCompilerList getInstance(@Nullable Project project) {
    final Project effectiveProject = project != null ? project : ProjectManager.getInstance().getDefaultProject();
    final ResolveConfigurableCompilerList instance = ServiceManager.getService(effectiveProject, ResolveConfigurableCompilerList.class);
    if (effectiveProject != project) {
      instance.disposeModel();
    }
    return instance;
  }

  public ProjectSdksModel getModel() {
    if (myModel == null) {
      myModel = new ProjectSdksModel();
      myModel.reset(null);
    }
    return myModel;
  }

  public void disposeModel() {
    if (myModel != null) {
      myModel.disposeUIResources();
      myModel = null;
    }
  }

  @NotNull
  public List<Sdk> getAllResolveSdks(@Nullable final Project project) {
    List<Sdk> result = new ArrayList<>();
    Sdk[] sdks = getModel().getSdks();
    for (Sdk sdk : sdks) {
      if (sdk.getSdkType() instanceof ResolveSdkType) {
        String x = sdk.getName();
        result.add(sdk);
      }
    }
    //Collections.sort(result, new PyInterpreterComparator(project));
    return result;
  }

  public List<Sdk> getAllResolveSdks() {
    return getAllResolveSdks(null);
  }

}
