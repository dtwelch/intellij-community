package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UIUtil;
import com.jetbrains.resolve.ResolveBundle;
import com.jetbrains.resolve.library.ResolveApplicationLibrariesService;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static com.intellij.util.ArrayUtil.toObjectArray;
import static com.intellij.util.containers.ContainerUtil.newArrayList;

public class ResolveSdkSettingsConfigurable extends
                                            SearchableConfigurable.Parent.Abstract implements Configurable.NoScroll {

  @NotNull private final ResolveCompilerSettings myResolveCompilerSettings;

  private final Project myProject;
  private JCheckBox myAutoImportStandardUses;
  private JCheckBox myShowCompilerEnvVariables;

  public ResolveSdkSettingsConfigurable(@NotNull Project project) {
    myProject = project;
    myResolveCompilerSettings = ResolveCompilerSettings.getInstance();
  }

  //We can put the checkboxes up here (in this class).. we just need to return the JPanel holding them in this method..
  //remember: ResolveCompilerFlagsConfigurable implements Configurable as well (just like this class does).
  @Override
  public JComponent createComponent() {
    FormBuilder builder = FormBuilder.createFormBuilder();
    myAutoImportStandardUses = new JCheckBox(ResolveBundle.message("sdk.settings.auto.import.std.uses"));
    myShowCompilerEnvVariables = new JCheckBox(ResolveBundle.message("sdk.settings.show.compiler.env"));
    builder.addComponent(myAutoImportStandardUses);
    builder.addComponent(myShowCompilerEnvVariables);
    JPanel result = builder.getPanel();
    result.setBorder(IdeBorderFactory.createTitledBorder(ResolveBundle.message("sdk.settings.compiler.flags"), true));

    JPanel settingsPanel = new JPanel(new BorderLayout());
    settingsPanel.add(result, BorderLayout.NORTH);
    return settingsPanel;
  }

  @Override
  public boolean isModified() {
    return myResolveCompilerSettings.isNoAutoStandardUses() != myAutoImportStandardUses.isSelected() ||
           myResolveCompilerSettings.isShowCompilerEnvVarsOnRun() != myShowCompilerEnvVariables.isSelected();
  }

  @Override
  public void apply() throws ConfigurationException {
    myResolveCompilerSettings.setNoAutoStandardUses(myAutoImportStandardUses.isSelected());
    myResolveCompilerSettings.setShowCompilerEnvVarsOnRun(myShowCompilerEnvVariables.isSelected());
  }

  @Override
  public void reset() {
    myAutoImportStandardUses.setSelected(myResolveCompilerSettings.isNoAutoStandardUses());
    myShowCompilerEnvVariables.setSelected(myResolveCompilerSettings.isShowCompilerEnvVarsOnRun());
  }

  @Override
  public void disposeUIResources() {
    UIUtil.dispose(myAutoImportStandardUses);
    UIUtil.dispose(myShowCompilerEnvVariables);
    myAutoImportStandardUses = null;
    myShowCompilerEnvVariables = null;
  }

  @NotNull
  @Override
  public String getId() {
    return "";
  }

  @Override
  protected Configurable[] buildConfigurables() {
    List<Configurable> result = newArrayList();
    result.add(new ResolveActiveCompilerConfigurable(myProject));

    String[] urlsFromEnv =
      ContainerUtil.map2Array(ResolveSdkUtil.getResolvePathRootsFromEnvironment(), String.class, VirtualFile::getUrl);
    result.add(new ResolveLibraryPathConfigurable(myProject,
                                                  ResolveApplicationLibrariesService.getInstance(),
                                                  urlsFromEnv));
    return toObjectArray(result, Configurable.class);
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVE";
  }
}
