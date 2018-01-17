package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.util.ui.FormBuilder;
import com.jetbrains.resolve.ResolveBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ResolveCompilerFlagsConfigurable extends JPanel implements Configurable {

  private final Project project;
  private JCheckBox autoImportStandardUses;
  private JCheckBox compilerEnvVariables;

  public ResolveCompilerFlagsConfigurable(final Project project) {
    this.project = project;

    setLayout(new BorderLayout());
    FormBuilder builder = FormBuilder.createFormBuilder();
    autoImportStandardUses = new JCheckBox(ResolveBundle.message("sdk.settings.auto.import.std.uses"));
    compilerEnvVariables = new JCheckBox(ResolveBundle.message("sdk.settings.show.compiler.env"));
    builder.addComponent(autoImportStandardUses);
    builder.addComponent(compilerEnvVariables);
    JPanel result = builder.getPanel();
    result.setBorder(IdeBorderFactory.createTitledBorder(ResolveBundle.message("sdk.settings.compiler.flags"), true));

    add(result, BorderLayout.NORTH);
  }

  public void apply() throws ConfigurationException {
/*
    VcsConfiguration settings = VcsConfiguration.getInstance(myProject);

    settings.PERFORM_COMMIT_IN_BACKGROUND = myCompilerEnvVariables.isSelected();
    settings.PERFORM_UPDATE_IN_BACKGROUND = myAutoImportStandardUses.isSelected();
    settings.PERFORM_CHECKOUT_IN_BACKGROUND = myCbCheckoutInBackground.isSelected();
    settings.PERFORM_EDIT_IN_BACKGROUND = myCbEditInBackground.isSelected();
    settings.PERFORM_ADD_REMOVE_IN_BACKGROUND = myCbAddRemoveInBackground.isSelected();
    settings.PERFORM_ROLLBACK_IN_BACKGROUND = myPerformRevertInBackgroundCheckBox.isSelected();

    boolean remoteCacheStateChanged = settings.CHECK_LOCALLY_CHANGED_CONFLICTS_IN_BACKGROUND != myTrackChangedOnServer.isSelected();
    if (! myProject.isDefault()) {
      settings.CHECK_LOCALLY_CHANGED_CONFLICTS_IN_BACKGROUND = myTrackChangedOnServer.isSelected();
      settings.CHANGED_ON_SERVER_INTERVAL = ((Number) myChangedOnServerInterval.getValue()).intValue();

      myCacheSettingsPanel.apply();
    }

    for (VcsShowOptionsSettingImpl setting : myPromptOptions.keySet()) {
      setting.setValue(myPromptOptions.get(setting).isSelected());
    }
    // will check if should + was started -> inside
    RemoteRevisionsCache.getInstance(myProject).updateAutomaticRefreshAlarmState(remoteCacheStateChanged);*/
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return this;
  }

  public boolean isModified() {
/*
    VcsConfiguration settings = VcsConfiguration.getInstance(myProject);
    if (settings.PERFORM_COMMIT_IN_BACKGROUND != myCompilerEnvVariables.isSelected()) {
      return true;
    }

    if (settings.PERFORM_UPDATE_IN_BACKGROUND != myAutoImportStandardUses.isSelected()) {
      return true;
    }

    if (settings.PERFORM_CHECKOUT_IN_BACKGROUND != myCbCheckoutInBackground.isSelected()) {
      return true;
    }

    if (settings.PERFORM_EDIT_IN_BACKGROUND != myCbEditInBackground.isSelected()) {
      return true;
    }
    if (settings.PERFORM_ADD_REMOVE_IN_BACKGROUND != myCbAddRemoveInBackground.isSelected()) {
      return true;
    }
    if (settings.PERFORM_ROLLBACK_IN_BACKGROUND != myPerformRevertInBackgroundCheckBox.isSelected()) {
      return true;
    }

    if (! myProject.isDefault()) {
      if (settings.CHECK_LOCALLY_CHANGED_CONFLICTS_IN_BACKGROUND != myTrackChangedOnServer.isSelected()) {
        return true;
      }
      if (myCacheSettingsPanel.isModified()) return true;
      if (settings.CHANGED_ON_SERVER_INTERVAL != ((Number) myChangedOnServerInterval.getValue()).intValue()) return true;
    }*/
    return false;
  }

  public void reset() {
    /*VcsConfiguration settings = VcsConfiguration.getInstance(myProject);
    myCompilerEnvVariables.setSelected(settings.PERFORM_COMMIT_IN_BACKGROUND);
    myAutoImportStandardUses.setSelected(settings.PERFORM_UPDATE_IN_BACKGROUND);
    myCbCheckoutInBackground.setSelected(settings.PERFORM_CHECKOUT_IN_BACKGROUND);
    myCbEditInBackground.setSelected(settings.PERFORM_EDIT_IN_BACKGROUND);
    myCbAddRemoveInBackground.setSelected(settings.PERFORM_ADD_REMOVE_IN_BACKGROUND);
    myPerformRevertInBackgroundCheckBox.setSelected(settings.PERFORM_ROLLBACK_IN_BACKGROUND);
    for (VcsShowOptionsSettingImpl setting : myPromptOptions.keySet()) {
      myPromptOptions.get(setting).setSelected(setting.getValue());
    }

    if (! myProject.isDefault()) {
      myTrackChangedOnServer.setSelected(settings.CHECK_LOCALLY_CHANGED_CONFLICTS_IN_BACKGROUND);
      myChangedOnServerInterval.setValue(settings.CHANGED_ON_SERVER_INTERVAL);
      myChangedOnServerInterval.setEnabled(myTrackChangedOnServer.isSelected());
      myCacheSettingsPanel.reset();
    }*/
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "Mappings";
  }
}
