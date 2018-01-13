package com.jetbrains.resolve.configuration;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.openapi.util.Comparing;
import com.jetbrains.resolve.sdk.ResolveSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * From yole: Manages the SDK model shared between PythonSdkConfigurable and ResolveActiveCompilerConfigurable.
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
/*
  private static class ResolveCompilerComparator implements Comparator<Sdk> {
    @Nullable private final Project myProject;

    public ResolveCompilerComparator(@Nullable final Project project) {
      myProject = project;
    }

    @Override
    public int compare(Sdk o1, Sdk o2) {
      if (!(o1.getSdkType() instanceof PythonSdkType) ||
          !(o2.getSdkType() instanceof PythonSdkType)) {
        return -Comparing.compare(o1.getName(), o2.getName());
      }

      final boolean isVEnv1 = PythonSdkType.isVirtualEnv(o1);
      final boolean isVEnv2 = PythonSdkType.isVirtualEnv(o2);
      final boolean isRemote1 = PySdkUtil.isRemote(o1);
      final boolean isRemote2 = PySdkUtil.isRemote(o2);

      if (isVEnv1) {
        if (isVEnv2) {
          if (myProject != null && associatedWithCurrent(o1, myProject)) {
            if (associatedWithCurrent(o2, myProject)) return compareSdk(o1, o2);
            return -1;
          }
          return compareSdk(o1, o2);
        }
        return -1;
      }
      if (isVEnv2) return 1;
      if (isRemote1) {
        if (isRemote2) {
          return compareSdk(o1, o2);
        }
        return 1;
      }
      if (isRemote2) return -1;

      return compareSdk(o1, o2);
    }

    private static int compareSdk(final Sdk o1, final Sdk o2) {
      final PythonSdkFlavor flavor1 = PythonSdkFlavor.getFlavor(o1);
      final PythonSdkFlavor flavor2 = PythonSdkFlavor.getFlavor(o2);
      final LanguageLevel level1 = flavor1 != null ? flavor1.getLanguageLevel(o1) : LanguageLevel.getDefault();
      final LanguageLevel level2 = flavor2 != null ? flavor2.getLanguageLevel(o2) : LanguageLevel.getDefault();
      final int compare = Comparing.compare(level1, level2);
      if (compare != 0) return -compare;
      return Comparing.compare(o1.getName(), o2.getName());
    }
  }*/

}
