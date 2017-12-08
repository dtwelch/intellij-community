package com.jetbrains.resolve;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.module.JpsModuleSourceRootType;

import javax.swing.*;

public abstract class ResolveModuleTypeBase<T extends ModuleBuilder> extends ModuleType<T> {

  public static ModuleType getInstance() {
    return ModuleTypeManager.getInstance().findByID(RESOLVE_MODULE);
  }

  @NonNls public static final String RESOLVE_MODULE = "RESOLVE_MODULE";

  protected ResolveModuleTypeBase() {
    super(RESOLVE_MODULE);
  }

  @NotNull
  public String getName() {
    return "Resolve Module";
  }

  @NotNull
  public String getDescription() {
    return "Resolve modules are used for developing <b>RESOLVE</b> applications.";
  }

  public Icon getNodeIcon(final boolean isOpened) {
    return ResolveIcons.RESOLVE;
  }

  @Override
  public boolean isMarkInnerSupportedFor(JpsModuleSourceRootType type) {
    return type == JavaSourceRootType.SOURCE;
  }
}
