package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UIUtil;
import com.jetbrains.resolve.ResolveBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ResolveCompilerFlagsConfigurable extends JPanel implements Configurable {

  @NotNull private final ResolveCompilerSettings myResolveCompilerSettings;

  private final Project myProject;
  private JCheckBox myAutoImportStandardUses;
  private JCheckBox myShowCompilerEnvVariables;

  //format settings
  private JCheckBox myUseMathAsciiAbbreviations;
  private JCheckBox myUseMathUnicodeSymbols;

  public ResolveCompilerFlagsConfigurable(final Project project) {
    myProject = project;
    myResolveCompilerSettings = ResolveCompilerSettings.getInstance();

    setLayout(new BorderLayout());
    FormBuilder builder = FormBuilder.createFormBuilder();
    myAutoImportStandardUses = new JCheckBox(ResolveBundle.message("sdk.settings.auto.import.std.uses"));
    myShowCompilerEnvVariables = new JCheckBox(ResolveBundle.message("sdk.settings.show.compiler.env"));
    myUseMathAsciiAbbreviations = new JCheckBox("sdk.settings.use.math.ascii.abbreviations");
    myUseMathUnicodeSymbols = new JCheckBox("sdk.settings.use.math.unicode.symbols");

    builder.addComponent(myAutoImportStandardUses);
    builder.addComponent(myShowCompilerEnvVariables);
    builder.addComponent(myAutoImportStandardUses);
    builder.addComponent(myUseMathAsciiAbbreviations);
    builder.addComponent(myUseMathUnicodeSymbols);

    JPanel result = builder.getPanel();
    result.setBorder(IdeBorderFactory.createTitledBorder(ResolveBundle.message("sdk.settings.compiler.flags"), true));

    add(result, BorderLayout.NORTH);
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return this;
  }

  @Override
  public boolean isModified() {
    return myResolveCompilerSettings.isNoAutoStandardUses() != myAutoImportStandardUses.isSelected() ||
           myResolveCompilerSettings.isShowCompilerEnvVarsOnRun() != myShowCompilerEnvVariables.isSelected() ||
           myResolveCompilerSettings.isUseMathAsciiAbbreviations() != myUseMathAsciiAbbreviations.isSelected() ||
           myResolveCompilerSettings.isUseMathUnicodeSymbols() != myUseMathUnicodeSymbols.isSelected();
  }

  @Override
  public void apply() throws ConfigurationException {
    myResolveCompilerSettings.setNoAutoStandardUses(myAutoImportStandardUses.isSelected());
    myResolveCompilerSettings.setShowCompilerEnvVarsOnRun(myShowCompilerEnvVariables.isSelected());
    myResolveCompilerSettings.setUseMathAsciiAbbreviations(myUseMathAsciiAbbreviations.isSelected());
    myResolveCompilerSettings.setUseMathUnicodeSymbols(myUseMathUnicodeSymbols.isSelected());
  }

  @Override
  public void reset() {
    myAutoImportStandardUses.setSelected(myResolveCompilerSettings.isNoAutoStandardUses());
    myShowCompilerEnvVariables.setSelected(myResolveCompilerSettings.isNoAutoStandardUses());
    myUseMathAsciiAbbreviations.setSelected(myUseMathAsciiAbbreviations.isSelected());
    myUseMathUnicodeSymbols.setSelected(myUseMathUnicodeSymbols.isSelected());
  }

  @Override
  public void disposeUIResources() {
    UIUtil.dispose(myAutoImportStandardUses);
    UIUtil.dispose(myShowCompilerEnvVariables);
    myAutoImportStandardUses = null;
    myShowCompilerEnvVariables = null;
    myUseMathAsciiAbbreviations = null;
    myUseMathUnicodeSymbols = null;
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVE";
  }
}
