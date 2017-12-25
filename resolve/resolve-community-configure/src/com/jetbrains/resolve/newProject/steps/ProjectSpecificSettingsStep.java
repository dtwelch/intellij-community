package com.jetbrains.resolve.newProject.steps;

import com.google.common.collect.Iterables;
import com.intellij.BundleBase;
import com.intellij.ide.impl.ProjectUtil;
import com.intellij.ide.util.projectWizard.AbstractNewProjectStep;
import com.intellij.ide.util.projectWizard.ProjectSettingsStepBase;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.ui.DialogWrapperPeer;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.DirectoryProjectGenerator;
import com.intellij.util.ui.update.UiNotifyConnector;
import com.jetbrains.resolve.configuration.ResolveConfigurableCompilerList;
import com.jetbrains.resolve.newProject.ResolveNewProjectSettings;
import com.jetbrains.resolve.newProject.ResolveProjectGenerator;
import com.jetbrains.resolve.sdk.ResolveDetectedSdk;
import com.jetbrains.resolve.sdk.ResolveSdkType;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import com.jetbrains.resolve.sdk.add.ResolveSdkPathChooserComboBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class ProjectSpecificSettingsStep<T> extends ProjectSettingsStepBase<T> implements DumbAware {

  @Nullable private ResolveSdkPathChooserComboBox mySdkAddChooserComboBox;

  public ProjectSpecificSettingsStep(@NotNull final DirectoryProjectGenerator<T> projectGenerator,
                                     @NotNull final AbstractNewProjectStep.AbstractCallback callback) {
    super(projectGenerator, callback);
  }

  @Override
  protected JPanel createAndFillContentPanel() {
    if (myProjectGenerator instanceof ResolveProjectGenerator) {
      // Allow generator to display custom error
      ((ResolveProjectGenerator<?>)myProjectGenerator).setErrorCallback(this::setErrorText);
    }
    return createContentPanelWithAdvancedSettingsPanel();
  }

  @Nullable
  public Sdk getOrCreateSdk() {
    if (!(myProjectGenerator instanceof ResolveProjectGenerator)) return null;
    if (mySdkAddChooserComboBox != null) {
      Sdk sdk = mySdkAddChooserComboBox.getSelectedSdk();
      if (sdk instanceof ResolveDetectedSdk) {
        List<Sdk> allSdks = ResolveSdkUtil.findBaseSdks();
        VirtualFile homeDir = sdk.getHomeDirectory();
        if (homeDir == null) return null;
        return SdkConfigurationUtil.setupSdk(allSdks.toArray(new Sdk[0]), homeDir, ResolveSdkType.getInstance(),
                                      false, null, null);
      }
      return sdk;
    }
    return null;
  }

  @Override
  protected void initGeneratorListeners() {
    super.initGeneratorListeners();
    if (myProjectGenerator instanceof ResolveProjectGenerator) {
      ((ResolveProjectGenerator<ResolveNewProjectSettings>)myProjectGenerator).addSettingsStateListener(this::checkValid);
      //myErrorLabel.addMouseListener(((PythonProjectGenerator)myProjectGenerator).getErrorLabelMouseListener());
    }
  }

  @Override
  public boolean checkValid() {
    if (!super.checkValid()) {
      return false;
    }
    if (mySdkAddChooserComboBox != null) {
      final List<ValidationInfo> validationInfos = mySdkAddChooserComboBox.validateAll();

      if (!validationInfos.isEmpty()) {
        setErrorText(StringUtil.join(validationInfos, info -> info.message, "\n"));
        return false;
      }
    }
    return true;
  }

  @Override
  protected JPanel createBasePanel() {
    if (myProjectGenerator instanceof ResolveProjectGenerator) {
      JPanel result = new JPanel();

      // Create the layout
      GroupLayout layout = new GroupLayout(result);
      result.setLayout(layout);
      layout.setAutoCreateGaps(true);

      // Create the components we will put in the form
      JLabel locationLabel = new JLabel("Location:");
      TextFieldWithBrowseButton locationTextField = createLocationComponentNoLabel();
      JLabel sdkLabel = new JLabel("Sdk:");
      mySdkAddChooserComboBox = createSdkComboComponentNoLabel();

      // Horizontally, we want to align the labels and the text fields
      // along the left (LEADING) edge
      layout.setHorizontalGroup(layout.createSequentialGroup()
                                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                              .addComponent(locationLabel)
                                              .addComponent(sdkLabel))
                                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                              .addComponent(locationTextField.getTextField())
                                              .addComponent(mySdkAddChooserComboBox.getChildComponent()))
                                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                              .addComponent(locationTextField.getButton())
                                              .addComponent(mySdkAddChooserComboBox.getButton())));

      // Vertically, we want to align each label with his textfield
      // on the baseline of the components
      layout.setVerticalGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(locationLabel)
                                            .addComponent(locationTextField.getTextField())
                                            .addComponent(locationTextField.getButton()))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(sdkLabel)
                                            .addComponent(mySdkAddChooserComboBox.getChildComponent())
                                            .addComponent(mySdkAddChooserComboBox.getButton())));
      return result;
    }
    return super.createBasePanel();
  }

  @NotNull
  private TextFieldWithBrowseButton createLocationComponentNoLabel() {
    myLocationField = new TextFieldWithBrowseButton();
    myProjectDirectory = ResolveSdkUtil.findSequentNonExistingResolveBaseDir();
    final String projectLocation = myProjectDirectory.toString();
    myLocationField.setText(projectLocation);
    final int index = projectLocation.lastIndexOf(File.separator);
    if (index > 0) {
      JTextField textField = myLocationField.getTextField();
      textField.select(index + 1, projectLocation.length());
      textField.putClientProperty(DialogWrapperPeer.HAVE_INITIAL_SELECTION, true);
    }

    final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
    myLocationField.addBrowseFolderListener("Select Base Directory", "Select base directory for the project", null, descriptor);
    return myLocationField;
  }

  @NotNull
  private ResolveSdkPathChooserComboBox createSdkComboComponentNoLabel() {
    final List<Sdk> existingSdks = getValidResolveSdks();
    final Sdk preferredSdk = getPreferredSdk(existingSdks); //eventually perhaps.

    List<Sdk> baseSdks = ResolveSdkUtil.findBaseSdks(existingSdks);
    return new ResolveSdkPathChooserComboBox(baseSdks);
  }

  /**
   * Right now, just the first if existingSdks isn't empty; null otherwise. In the future we might change this to automatically
   * select the Sdk with the latest version (would require a version comparator, for example).
   */
  @Nullable
  private Sdk getPreferredSdk(@NotNull List<Sdk> existingSdks) {
    return null;
  }

  @NotNull
  private static List<Sdk> getValidResolveSdks() {
    final List<Sdk> resolvesdks = ResolveConfigurableCompilerList.getInstance(null).getAllResolveSdks();
    Iterables.removeIf(resolvesdks, sdk -> !(sdk.getSdkType() instanceof ResolveSdkType) /*||
                                          PythonSdkType.isInvalid(sdk) ||
                                          PySdkExtKt.getAssociatedProjectPath(sdk) != null*/);
    //Collections.sort(pythonSdks, new PreferredSdkComparator());
    return resolvesdks;
  }
}
