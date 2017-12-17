package com.jetbrains.resolve.sdk.add;

import com.intellij.openapi.projectRoots.Sdk;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ResolveAddSdkPanel extends JPanel {

  private final ResolveSdkPathChooserComboBox sdkChooserComboBox;
  private final List<Sdk> existingSdks = new ArrayList<>();

  public ResolveAddSdkPanel(List<Sdk> existingSdks) {
    this.sdkChooserComboBox = new ResolveSdkPathChooserComboBox(existingSdks);
    this.existingSdks.addAll(existingSdks);
  }
}
