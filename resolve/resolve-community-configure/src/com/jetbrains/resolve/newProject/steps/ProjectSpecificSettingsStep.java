package com.jetbrains.resolve.newProject.steps;

import com.intellij.ide.util.projectWizard.AbstractNewProjectStep;
import com.intellij.ide.util.projectWizard.ProjectSettingsStepBase;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.ui.DialogWrapperPeer;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.platform.DirectoryProjectGenerator;
import com.jetbrains.resolve.newProject.ResolveNewProjectSettings;
import com.jetbrains.resolve.newProject.ResolveProjectGenerator;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectSpecificSettingsStep<T> extends ProjectSettingsStepBase<T> implements DumbAware {

  @Nullable private ResolveSdkChooserComboBox mySdkAddChooserComboBox;

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
  public Sdk getSdk() {
    if (!(myProjectGenerator instanceof ResolveProjectGenerator)) return null;
    if (mySdkAddChooserComboBox != null) {
      return mySdkAddChooserComboBox.getSelectedSdk();
    }
    return null;
  }

  @Override
  protected void initGeneratorListeners() {
    super.initGeneratorListeners();
    if (myProjectGenerator instanceof ResolveProjectGenerator) {
      ((ResolveProjectGenerator<ResolveNewProjectSettings>)myProjectGenerator).addSettingsStateListener(this::checkValid);
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
  private ResolveSdkChooserComboBox createSdkComboComponentNoLabel() {
    List<Sdk> baseSdks = new ArrayList<>(); //ResolveSdkUtil.findBaseSdks();
    ResolveSdkChooserComboBox combo = new ResolveSdkChooserComboBox(baseSdks);
    combo.addChangedListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        checkValid();
      }
    });
    return combo;
  }
}
