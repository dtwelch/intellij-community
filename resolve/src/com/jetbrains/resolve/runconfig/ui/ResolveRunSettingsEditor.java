package com.jetbrains.resolve.runconfig.ui;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.jetbrains.resolve.runconfig.ResolveRunConfiguration;
import com.jetbrains.resolve.runconfig.ResolveRunUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ResolveRunSettingsEditor extends SettingsEditor<ResolveRunConfiguration> {
  private JPanel panel;
  private TextFieldWithBrowseButton mainFacility;

  public ResolveRunSettingsEditor(Project p) {
    ResolveRunUtil.installRESOLVEWithMainFileChooser(p, mainFacility);
  }

  @Override
  protected void resetEditorFrom(@NotNull ResolveRunConfiguration s) {
  }

  @Override
  protected void applyEditorTo(@NotNull ResolveRunConfiguration s)
    throws ConfigurationException {
    s.setFilePath(mainFacility.getText());
  }

  @NotNull
  @Override
  protected JComponent createEditor() {
    return panel;
  }

}
