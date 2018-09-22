package com.jetbrains.resolve.sdk;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBDefaultTreeCellRenderer;
import com.jetbrains.resolve.ResolveBundle;
import com.jetbrains.resolve.ResolveIcons;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

/*
 * @author dtwelch
 */
public class ResolveSdkType extends SdkType {

  public ResolveSdkType() {
    super("Resolve SDK");
  }

  public static ResolveSdkType getInstance() {

    return SdkType.findInstance(ResolveSdkType.class);
  }

  @Override
  public Icon getIcon() {
    return ResolveIcons.RESOLVE;
  }

  @NotNull
  @Override
  public String getHelpTopic() {
    return "reference.project.structure.sdk.resolve";
  }

  @Override
  @NonNls
  @Nullable
  public String suggestHomePath() {
    VirtualFile suggestedDir = ResolveSdkUtil.suggestSdkDirectory();
    return suggestedDir != null ? suggestedDir.getPath() : null;
  }

  @NotNull
  public Collection<String> suggestHomePaths() {
    return super.suggestHomePaths(); //This one is sufficient for now.
  }

  @Override
  public boolean isValidSdkHome(String path) {
    return ResolveSdkUtil.isValidSdkHome(path);
  }

  @NotNull
  @Override
  public FileChooserDescriptor getHomeChooserDescriptor() {
    return new FileChooserDescriptor(false, true, false, false, false, false) {
      @Override
      public void validateSelectedFiles(VirtualFile[] files) throws Exception {
        if (files.length != 0) {

          if (!isValidSdkHome(files[0].getPath())) {
            throw new Exception(ResolveBundle.message("sdk.error.invalid.compiler.name", files[0].getName()));
          }
        }
      }
    }.withTitle(ResolveBundle.message("sdk.select.compiler.path")).withShowHiddenFiles(false);
  }

  @Nullable
  @Override
  public String getVersionString(String sdkHome) {
    return ResolveSdkUtil.retrieveResolveVersion(sdkHome);
  }

  @Override
  public String suggestSdkName(String currentSdkName, String sdkHome) {
    String version = getVersionString(sdkHome);
    if (version == null) {
      return "Unknown Resolve version at " + sdkHome;
    }
    return "RESOLVE " + version;
  }

  @Nullable
  @Override
  public AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull SdkModel sdkModel,
                                                                     @NotNull SdkModificator sdkModificator) {
    return null;
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return "Resolve SDK";
  }

  @Override
  public void saveAdditionalData(@NotNull SdkAdditionalData additionalData, @NotNull Element additional) {
  }

  @Override
  public void setupSdkPaths(@NotNull Sdk sdk) {
    String versionString = sdk.getVersionString();
    if (versionString == null) {
      throw new RuntimeException("SDK version is not defined");
    }
    SdkModificator modificator = sdk.getSdkModificator();
    String path = sdk.getHomePath();
    if (path == null) return;
    modificator.setHomePath(path);

    for (VirtualFile file : ResolveSdkUtil.getSdkDirectoriesToAttach(path)) {
      modificator.addRoot(file, OrderRootType.CLASSES);
      modificator.addRoot(file, OrderRootType.SOURCES);
    }
    modificator.commitChanges();
  }
}
