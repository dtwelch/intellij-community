package com.jetbrains.resolve.sdk.add;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.util.ui.FormBuilder;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;

import javax.swing.*;
import java.awt.*;

public class ResolveAddNewSdkPanel extends JPanel {

  public ResolveAddNewSdkPanel(java.util.List<Sdk> existingSdks) {
    setLayout(new BorderLayout());
    java.util.List<Sdk> baseSdks = ResolveSdkUtil.findBaseSdks();
    ResolveSdkPathChooserComboBox baseSdkField = new ResolveSdkPathChooserComboBox(existingSdks);
    JPanel formPanel = FormBuilder.createFormBuilder()
      .addLabeledComponent("SDK:", baseSdkField)
      .getPanel();
    add(formPanel, BorderLayout.NORTH);
  }
}
