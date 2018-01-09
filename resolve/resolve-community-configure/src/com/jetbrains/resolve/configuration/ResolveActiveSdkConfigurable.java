package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkModel;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.util.NullableConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Set;

public class ResolveActiveSdkConfigurable implements UnnamedConfigurable {

  @NotNull private final Project myProject;
  private MySdkModelListener mySdkModelListener = new MySdkModelListener();
  private ResolveConfigurableCompilerList myCompilerList;
  private ProjectSdksModel myProjectSdksModel;
  private NullableConsumer<Sdk> myAddSdkCallback;
  private boolean mySdkSettingsWereModified = false;

  private JPanel myMainPanel;
  private ComboboxWithBrowseButton mySdkCombo;
  private Set<Sdk> myInitialSdkSet;


  //NOTES:
  //--refreshSdkList() on line 156 in PythonSdkDetailsDialog is where the sdk list gets queried and updated from the InterpreterListModel.
  //Refer to this when you populate the combobox in this class... Also: We want the current projects active Sdk To be preselected
  //in the combobox list refer to PyActiveSdkConfigurable to figure out how this is done....
  //--reset() is where we'll populate the combobox (not init()) see comment on reset() in UnnamedConfigurable.java.

  public ResolveActiveSdkConfigurable(@NotNull Project project) {
    myProject = project;
    //layoutPanel();
    //initContent();
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return null;
  }

  @Override
  public boolean isModified() {
    return false;
  }

  @Override
  public void apply() throws ConfigurationException {

  }

  private class MySdkModelListener implements SdkModel.Listener {

    public MySdkModelListener() {
    }

    @Override
    public void sdkAdded(Sdk sdk) {
      updateSdkList(true);
    }

    @Override
    public void beforeSdkRemove(Sdk sdk) {
      updateSdkList(true);
    }

    @Override
    public void sdkChanged(Sdk sdk, String previousName) {
      updateSdkList(true);
    }

    @Override
    public void sdkHomeSelected(Sdk sdk, String newSdkHome) {
    }
  }

  private class SdkAddedCallback implements NullableConsumer<Sdk> {
    @Override
    public void consume(Sdk sdk) {
      if (sdk == null) return;

      if (myProjectSdksModel.findSdk(sdk.getName()) == null) {
        myProjectSdksModel.addSdk(sdk);
      }
      updateSdkList(false);
      setSelectedSdk(sdk);
    }
  }
}
