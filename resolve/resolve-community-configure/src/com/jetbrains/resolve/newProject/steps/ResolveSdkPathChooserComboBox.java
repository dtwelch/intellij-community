package com.jetbrains.resolve.newProject.steps;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.SdkListCellRenderer;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.resolve.sdk.ResolveSdkListCellRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
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
public class ResolveSdkPathChooserComboBox extends ComponentWithBrowseButton<JComboBox<Sdk>> {

  public ResolveSdkPathChooserComboBox(List<Sdk> existingSdks,
                                       @Nullable VirtualFile suggestedSdkHomeDir) {
    super(new ComboBox<>(existingSdks.toArray(new Sdk[existingSdks.size()])), null);
    JComboBox<Sdk> child = getChildComponent(); //ok since getChildComponent() is final
    child.setRenderer(new ResolveSdkListCellRenderer());
    //now: add listener...
  }

  @Nullable
  public Sdk getSelectedSdk() {
    Sdk result = null;
    JComboBox<Sdk> child = getChildComponent();
    if (child.getSelectedItem() instanceof Sdk) {
      result = (Sdk) child.getSelectedItem();
    }
    return result;
  }

  public void setSelectedSdk(@NotNull Sdk value) {
    getChildComponent().setSelectedItem(value);
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
}
