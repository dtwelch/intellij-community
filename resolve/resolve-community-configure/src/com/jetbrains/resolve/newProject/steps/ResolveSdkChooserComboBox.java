package com.jetbrains.resolve.newProject.steps;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Consumer;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.configuration.ResolveConfigurableCompilerList;
import com.jetbrains.resolve.sdk.ResolveSdkListCellRenderer;
import com.jetbrains.resolve.sdk.ResolveSdkType;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ResolveSdkChooserComboBox extends ComponentWithBrowseButton<JComboBox<Sdk>> {
  private static final Logger LOG = Logger.getInstance(ResolveSdkChooserComboBox.class);

  private final List<ActionListener> myChangedListeners = ContainerUtil.createLockFreeCopyOnWriteList();

  public ResolveSdkChooserComboBox(List<Sdk> existingSdks) {
    super(new ComboBox<>(existingSdks.toArray(new Sdk[existingSdks.size()])), null);
    JComboBox<Sdk> childComponent = getChildComponent(); //ok since getChildComponent() is final
    childComponent.setRenderer(new ResolveSdkListCellRenderer());
    childComponent.addItem(null);
    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ResolveSdkType sdkType = ResolveSdkType.getInstance();
        FileChooserDescriptor descriptor = sdkType.getHomeChooserDescriptor();
        descriptor.setForcedToUseIdeaFileChooser(true);

        FileChooser.chooseFiles(descriptor, null, ResolveSdkUtil.suggestSdkDirectory(), new Consumer<List<VirtualFile>>() {
          @Override
          public void consume(List<VirtualFile> files) {
            if (files.size() != 1) return;
            VirtualFile homeDir = files.get(0);
            final ResolveConfigurableCompilerList interpreterList = ResolveConfigurableCompilerList.getInstance(null);
            final Sdk[] sdks = interpreterList.getModel().getSdks();

            Sdk sdk = SdkConfigurationUtil.setupSdk(sdks, homeDir, ResolveSdkType.getInstance(), true, null, null);
            //do this + the Project Model stuff here as well (see python srcs).
            if (sdk != null) {
              final ProjectSdksModel projectSdksModel = interpreterList.getModel();
              if (projectSdksModel.findSdk(sdk) == null) {
                projectSdksModel.addSdk(sdk);
                try {
                  projectSdksModel.apply();
                }
                catch (ConfigurationException e) {
                  LOG.error("Error adding new resolve compiler " + e.getMessage());
                }
                getChildComponent().addItem(sdk);
              }
              setSelectedSdk(sdk);
            }
            ResolveSdkChooserComboBox.this.notifyChanged(null);
          }
        });
      }
    });
  }

  @Nullable
  public Sdk getSelectedSdk() {
    Sdk result = null;
    JComboBox<Sdk> child = getChildComponent();
    if (child.getSelectedItem() instanceof Sdk) {
      result = (Sdk)child.getSelectedItem();
    }
    return result;
  }

  public void setSelectedSdk(Sdk value) {
    getChildComponent().setSelectedItem(value);
  }

  private void notifyChanged(ActionEvent e) {
    for (ActionListener changedListener : myChangedListeners) {
      changedListener.actionPerformed(e);
    }
  }

  public void addChangedListener(ActionListener listener) {
    myChangedListeners.add(listener);
  }

  @NotNull
  public List<ValidationInfo> validateAll() {
    Sdk selected = getSelectedSdk();
    List<ValidationInfo> result = new ArrayList<>();
    if (selected == null) {
      result.add(new ValidationInfo("No RESOLVE compiler Sdk selected"));
    }
    else if (!ResolveSdkType.getInstance().isValidSdkHome(selected.getHomePath())) {
      result.add(new ValidationInfo("Invalid compiler Sdk selected (missing resolve.jar)"));
    }
    return result;
  }
}
