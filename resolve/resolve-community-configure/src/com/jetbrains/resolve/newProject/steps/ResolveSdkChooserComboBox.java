package com.jetbrains.resolve.newProject.steps;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Consumer;
import com.intellij.util.containers.ContainerUtil;
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

//TOdO:
//SdkListCellRenderer  (take a look at this for rendering the comboboxwith browse buttons with icons a la goland...
//PyAddSystemWideInterpreterPanel (Interpreter: |__________________| |...|)

//This class is obviously modeled after "PySdkPathChoosingComboBox.kt" in the pycharm sources
//The "<No Interpreter>" default (i.e. <No SDK>) is handled by the SdkCellListRenderer (I Think)...

//When I select an invalid home directory for SDK a box should come up saying this isn't a valid sdk dir (like in
//goland).. a similar thing is done in the file directory browser for pycharm. Check how they do it. Likely a listener
//that calls an SDK isValid..(..) method when ok is pressed...
public class ResolveSdkChooserComboBox extends ComponentWithBrowseButton<JComboBox<Sdk>> {
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
            String homeDir = files.get(0).getPath();

            //Idea: do this in two steps. First call setupSdk (or createSdk), then using "equals", check if it already appears in the
            //combo list
            //I'm not actually sure if I want to "add" the SDK here... think about the options panel... I want to
            //add it to the table as part of this step.
            Sdk c = SdkConfigurationUtil.createAndAddSDK(homeDir, ResolveSdkType.getInstance());
            if (c != null) {
              getChildComponent().addItem(c);
              setSelectedSdk(c);
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
  public List<Sdk> getItems() {
    List<Sdk> result = new ArrayList<>();
    JComboBox<Sdk> child = getChildComponent();
    for (int i = 0; i < child.getItemCount(); i++) {
      result.add(child.getItemAt(i));
    }
    return result;
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
