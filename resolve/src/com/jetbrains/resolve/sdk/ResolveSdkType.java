package com.jetbrains.resolve.sdk;

import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.ResolveIcons;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/*
 * @author dtwelch
 */
public class ResolveSdkType extends SdkType {

  public ResolveSdkType() {
    super("Resolve SDK");
  }

  @Override
  public Icon getIcon() {
    return ResolveIcons.Resolve;
  }

  @NotNull
  @Override
  public String getHelpTopic() {
    return "reference.project.structure.sdk.resolve";
  }

  //TEMP
  @Override
  @NonNls
  @Nullable
  public String suggestHomePath() {
    if (SystemInfo.isMac || SystemInfo.isLinux) {
      return "~/Documents/resolve-lite";
    }
    return null;
  }

  @Override
  public boolean isValidSdkHome(String path) {
    if (getVersionString(path) == null) {
      //RESOLVESdkService.LOG.debug("Cannot retrieve version for sdk (or the compiler-jar): " + sdkHomePath);
      return false;
    }
    return true;
  }

  @Nullable
  @Override
  public String getVersionString(@NotNull String sdkHome) {
    return "0.0.1";
  }

  @Override
  public String suggestSdkName(String currentSdkName, String sdkHome) {
    String version = getVersionString(sdkHome);
    if (version == null) {
      return "Unknown Resolve version at " + sdkHome;
    }
    return "Resolve " + version;
  }

  @Nullable
  @Override
  public AdditionalDataConfigurable createAdditionalDataConfigurable(
      @NotNull SdkModel sdkModel, @NotNull SdkModificator sdkModificator) {
    return null;
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return "Resolve SDK";
  }

  @Override
  public void saveAdditionalData(
      @NotNull SdkAdditionalData additionalData, @NotNull Element additional) {}
}
