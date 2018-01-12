package com.jetbrains.resolve.configuration;

import com.intellij.openapi.application.ApplicationBundle;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkModel;
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.FixedSizeButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.CollectionComboBoxModel;
import com.intellij.ui.FontComboBox;
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

  private Set<Sdk> initialSdkSet;

  private JButton detailsButton;
  private JPanel mainPanel;
  private ComboBox<Object> sdkComboChooser;

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
    this.sdkComboChooser = new ComboBox<>();
    this.sdkComboChooser.setRenderer(new ResolveSdkListCellRenderer());
    this.mainPanel = new JPanel(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = JBUI.insets(2);
    c.anchor = GridBagConstraints.NORTH;
    c.gridx = 0;
    c.gridy = 0;
    c.weighty = 1;
    c.weightx = 1;
    this.mainPanel.add(this.sdkComboChooser, c);

    this.detailsButton = new FixedSizeButton();
    c.insets = JBUI.insets(2, 0, 2, 2);
    c.gridx = 1;
    c.weighty = 0.0;
    c.weightx = 0.0;
    this.mainPanel.add(this.detailsButton, c);
  }

  public void initContent() {
    this.compilerList = ResolveConfigurableCompilerList.getInstance(this.project);

    this.projectSdksModel = compilerList.getModel();
    this.initialSdkSet = projectSdksModel.getProjectSdks().keySet();
    this.projectSdksModel.addListener(sdkModelListener);
/*
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
              //setSelectedSdk(sdk);
            }
          }
        });
      }
    });*/
  }

  @Override
  public void reset() {
    /*updateSdkList(false);
    final Sdk sdk = getSdk();
    setSelectedSdk(sdk);*/
  }

  private void setSelectedSdk(@Nullable final Sdk selectedSdk) {
    sdkComboChooser.getModel().setSelectedItem(
      selectedSdk == null ? null : this.projectSdksModel.findSdk(selectedSdk.getName()));
  }

  @Nullable
  protected Sdk getSdk() {
    return ProjectRootManager.getInstance(project).getProjectSdk();
  }

  private void updateSdkList(boolean preserveSelection) {
    /*final List<Sdk> sdkList = compilerList.getAllResolveSdks(project);
    Sdk selection = preserveSelection ? (Sdk)sdkComboChooser.getSelectedItem() : null;
    if (!sdkList.contains(selection)) {
      selection = null;
    }
    // if the selection is a non-matching virtualenv, show it anyway
    if (selection != null && !sdkList.contains(selection)) {
      sdkList.add(0, selection);
    }
    List<Object> items = new ArrayList<>(sdkList);
    items.add(null);
    this.sdkComboChooser.setRenderer(new ResolveSdkListCellRenderer());
    this.sdkComboChooser.setModel(new CollectionComboBoxModel<>(items, selection));*/
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

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVEROOT";
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
    }

    @Override
    public void sdkChanged(Sdk sdk, String previousName) {
    }

    @Override
    public void sdkHomeSelected(Sdk sdk, String newSdkHome) {
    }
  }
}
