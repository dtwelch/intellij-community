package com.jetbrains.resolve.library;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleServiceManager;
import com.jetbrains.resolve.ResolveConstants;
import org.jetbrains.annotations.NotNull;

@State(
  name = ResolveConstants.RESOLVE_LIBRARIES_SERVICE_NAME,
  storages = @Storage(file = StoragePathMacros.MODULE_FILE)
)
public class ResolveModuleLibrariesService
  extends
  ResolveLibrariesService<ResolveLibrariesState> {

  public static ResolveModuleLibrariesService getInstance(
    @NotNull Module module) {
    return ModuleServiceManager.getService(module,
                                           ResolveModuleLibrariesService.class);
  }
}
