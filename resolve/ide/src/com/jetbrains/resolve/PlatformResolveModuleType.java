package com.jetbrains.resolve;

import com.intellij.ide.util.projectWizard.EmptyModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import org.jetbrains.jps.model.module.JpsModuleSourceRootType;

/**
 * @author dtwelch
 */
public class PlatformResolveModuleType extends ResolveModuleTypeBase<EmptyModuleBuilder> {
  @NotNull
  @Override
  public EmptyModuleBuilder createModuleBuilder() {
    return new EmptyModuleBuilder() {
      @Override
      public ModuleType getModuleType() {
        return getInstance();
      }
    };
  }

  @Override
  public boolean isSupportedRootType(JpsModuleSourceRootType type) {
    return type == JavaSourceRootType.SOURCE;
  }
}
