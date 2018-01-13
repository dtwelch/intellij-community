package com.jetbrains.resolve.configuration;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkModel;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.util.Consumer;
import com.intellij.util.ui.JBUI;
import com.jetbrains.resolve.sdk.ResolveSdkListCellRenderer;
import com.jetbrains.resolve.sdk.ResolveSdkType;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RESOLVEROOTConfigurable implements Configurable {

  @NotNull private final Project project;
  private MySdkModelListener sdkModelListener = new MySdkModelListener();
  private ResolveConfigurableCompilerList compilerList;
  private ProjectSdksModel projectSdksModel;

  private JButton detailsButton;
  private JPanel mainPanel;
  private ComboboxWithBrowseButton sdkComboChooser;

  //NOTES:
  //--refreshSdkList() on line 156 in PythonSdkDetailsDialog is where the sdk list gets queried and updated from the InterpreterListModel.
  //Refer to this when you populate the combobox in this class... Also: We want the current projects active Sdk To be preselected
  //in the combobox list refer to PyActiveSdkConfigurable to figure out how this is done....
  //--reset() is where we'll populate the combobox (not init()) see comment on reset() in UnnamedConfigurable.java.

  public RESOLVEROOTConfigurable(@NotNull Project project) {
    this.project = project;
    layoutPanel();
    initContent();
  }

  public void layoutPanel() {
    mainPanel = new JPanel(new GridBagLayout());
    sdkComboChooser = new ComboboxWithBrowseButton();
    //noinspection unchecked
    sdkComboChooser.getComboBox().setRenderer(new ResolveSdkListCellRenderer());

    detailsButton = sdkComboChooser.getButton();
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.NORTH;
    c.insets = JBUI.insets(2);
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.1;
    c.weighty = 1;
    mainPanel.add(sdkComboChooser, c);
  }

  public void initContent() {
    this.compilerList = ResolveConfigurableCompilerList.getInstance(this.project);

    this.projectSdksModel = compilerList.getModel();
    this.projectSdksModel.addListener(sdkModelListener);

    this.detailsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ResolveSdkType sdkType = ResolveSdkType.getInstance();
        FileChooserDescriptor descriptor = sdkType.getHomeChooserDescriptor();
        descriptor.setForcedToUseIdeaFileChooser(true);

        FileChooser.chooseFiles(descriptor, null, ResolveSdkUtil.suggestSdkDirectory(), new Consumer<java.util.List<VirtualFile>>() {
          @Override
          public void consume(List<VirtualFile> files) {
            if (files.size() != 1) return;
            VirtualFile homeDir = files.get(0);
            final ResolveConfigurableCompilerList interpreterList = ResolveConfigurableCompilerList.getInstance(null);
            final Sdk[] sdks = interpreterList.getModel().getSdks();
            Sdk sdk = SdkConfigurationUtil.setupSdk(sdks, homeDir, ResolveSdkType.getInstance(), true, null, null);
            if (sdk != null) {
              if (projectSdksModel.findSdk(sdk.getName()) == null) {
                projectSdksModel.addSdk(sdk);
              }
              updateSdkList(false);
              setSelectedSdk(sdk);
            }
          }
        });
      }
    });
  }

  @Override
  public void reset() {
    updateSdkList(false);
    final Sdk sdk = getCurrentProjectSdk();
    setSelectedSdk(sdk);
  }

  private void setSelectedSdk(@Nullable final Sdk selectedSdk) {
    sdkComboChooser.getComboBox().setSelectedItem(
      selectedSdk == null ? null : this.projectSdksModel.findSdk(selectedSdk.getName()));
  }

  @Nullable
  protected Sdk getCurrentProjectSdk() {
    return ProjectRootManager.getInstance(project).getProjectSdk();
  }

  private void updateSdkList(boolean preserveSelection) {
    final List<Sdk> sdkList = compilerList.getAllResolveSdks(project);
    Sdk selection = preserveSelection ? (Sdk)sdkComboChooser.getComboBox().getSelectedItem() : null;
    if (!sdkList.contains(selection)) {
      selection = null;
    }
    // if the selection is a non-matching virtualenv, show it anyway
    if (selection != null && !sdkList.contains(selection)) {
      sdkList.add(0, selection);
    }
    List<Object> items = new ArrayList<>(sdkList);
    //noinspection unchecked
    sdkComboChooser.getComboBox().setRenderer(new ResolveSdkListCellRenderer());
    //noinspection unchecked
    sdkComboChooser.getComboBox().setModel(new CollectionComboBoxModel<>(items, selection));
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return mainPanel;
  }

  @Nullable
  private Sdk getSelectedSdk() {
    final Sdk selectedItem = (Sdk)sdkComboChooser.getComboBox().getSelectedItem();
    return selectedItem == null ? null : projectSdksModel.findSdk(selectedItem);
  }

  @Override
  public boolean isModified() {
    Sdk projectSdk = getCurrentProjectSdk();
    final Sdk selectedSdk = getSelectedSdk();
    return !Comparing.equal(projectSdk, selectedSdk);
  }

  @Override
  public void apply() throws ConfigurationException {
    final Sdk selectedSdk = getSelectedSdk();

    if (selectedSdk != null) {
      updateSdkList(false);
      projectSdksModel.apply();
      setSelectedSdk(selectedSdk);
    }
    setSdk(selectedSdk);
  }

  protected void setSdk(final Sdk item) {
    ApplicationManager.getApplication()
      .runWriteAction(() -> ProjectRootManager.getInstance(this.project).setProjectSdk(item));
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVEROOT";
  }

  private class MySdkModelListener implements SdkModel.Listener {

    @Override
    public void sdkAdded(Sdk sdk) {
      updateSdkList(true);
    }

    @Override
    public void beforeSdkRemove(Sdk sdk) {
    }

    @Override
    public void sdkChanged(Sdk sdk, String previousName) {
    }

    @Override
    public void sdkHomeSelected(Sdk sdk, String newSdkHome) {
    }
  }
}
