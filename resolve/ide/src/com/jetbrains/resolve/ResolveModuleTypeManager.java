package com.jetbrains.resolve;

import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.impl.ModuleTypeManagerImpl;

/**
 * @author dtwelch
 */
public class ResolveModuleTypeManager extends ModuleTypeManagerImpl {
  @Override
  public ModuleType getDefaultModuleType() {
    return new PlatformResolveModuleType();
  }
}
