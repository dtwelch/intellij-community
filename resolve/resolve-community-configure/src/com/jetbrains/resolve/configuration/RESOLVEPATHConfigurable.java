package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RESOLVEPATHConfigurable implements Configurable {

  private final Project project;
  private JPanel foo;

  public RESOLVEPATHConfigurable(@NotNull Project project) {
    this.project = project;
    this.foo = new JPanel();
    foo.add(new JLabel("PLACEHOLDER"));
    //layoutPanel();
    // initContent();
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVEPATH";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return foo;
  }

  @Override
  public boolean isModified() {
    return false;
  }

  @Override
  public void apply() throws ConfigurationException {

  }
}
