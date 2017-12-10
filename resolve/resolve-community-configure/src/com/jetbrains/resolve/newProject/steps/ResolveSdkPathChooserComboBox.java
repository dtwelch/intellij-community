// Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jetbrains.resolve.newProject.steps;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ComboboxWithBrowseButton;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

//TOdO:
//SdkListCellRenderer  (take a look at this for rendering the comboboxwith browse buttons with icons a la goland...
//PyAddSystemWideInterpreterPanel (Interpreter: |__________________| |...|)

//This class is obviously modeled after "PySdkPathChoosingComboBox.kt" in the pycharm sources
public class ResolveSdkPathChooserComboBox extends ComponentWithBrowseButton<JComboBox<Sdk>> {


  public ResolveSdkPathChooserComboBox(List<Sdk> existingSdks,
                                       @Nullable VirtualFile suggestedSdkHomeDir) {
    super(new ComboBox<Sdk>(existingSdks.toArray()), browseActionListener);
  }
}
