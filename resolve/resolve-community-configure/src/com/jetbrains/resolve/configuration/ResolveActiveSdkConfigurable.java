package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.util.NullableConsumer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Set;

public class ResolveActiveSdkConfigurable extends UnnamedConfigurable {

  @NotNull private final Project myProject;
  private MySdkModelListener mySdkModelListener = new MySdkModelListener();
  private ResolveConfigurableCompilerList myCompilerList;
  private ProjectSdksModel myProjectSdksModel;
  private NullableConsumer<Sdk> myAddSdkCallback;
  private boolean mySdkSettingsWereModified = false;

  private JPanel myMainPanel;
  private ComboboxWithBrowseButton mySdkCombo;
  private Set<Sdk> myInitialSdkSet;

}
