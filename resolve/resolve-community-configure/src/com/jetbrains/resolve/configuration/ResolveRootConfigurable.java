package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkModel;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.FixedSizeButton;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.util.NullableConsumer;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class ResolveRootConfigurable implements UnnamedConfigurable {

  @NotNull private final Project project;
  private MySdkModelListener sdkModelListener = new MySdkModelListener();
  private ResolveConfigurableCompilerList compilerList;
  private ProjectSdksModel projectSdksModel;
  private JButton detailsButton;
  private JPanel mainPanel;
  private ComboBox<Sdk> sdkComboChooser;

  //NOTES:
  //--refreshSdkList() on line 156 in PythonSdkDetailsDialog is where the sdk list gets queried and updated from the InterpreterListModel.
  //Refer to this when you populate the combobox in this class... Also: We want the current projects active Sdk To be preselected
  //in the combobox list refer to PyActiveSdkConfigurable to figure out how this is done....
  //--reset() is where we'll populate the combobox (not init()) see comment on reset() in UnnamedConfigurable.java.

  public ResolveRootConfigurable(@NotNull Project project) {
    this.project = project;
    layoutPanel();
    //initContent();
  }

  public void layoutPanel() {
    final GridBagLayout layout = new GridBagLayout();
    this.mainPanel = new JPanel(layout);
    //final Dimension preferredSize = sdkComboChooser.getPreferredSize();
    this.sdkComboChooser = new ComboBox<>();
    this.detailsButton = new FixedSizeButton();

    final GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = JBUI.insets(2);

    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.1;
    this.mainPanel.add(sdkComboChooser, c);

    c.insets = JBUI.insets(2, 0, 2, 2);
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 0.0;
    this.mainPanel.add(this.detailsButton, c);
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return mainPanel;
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
