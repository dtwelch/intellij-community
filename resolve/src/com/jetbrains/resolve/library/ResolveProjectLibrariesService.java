package com.jetbrains.resolve.library;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.jetbrains.resolve.ResolveConstants;
import org.jetbrains.annotations.NotNull;

@State(
  name = ResolveConstants.RESOLVE_LIBRARIES_SERVICE_NAME,
  storages = {
    @Storage(id = "default", file = StoragePathMacros.PROJECT_FILE),
    @Storage(id = "dir",
      file = StoragePathMacros.PROJECT_CONFIG_DIR + "/" + ResolveConstants.RESOLVE_LIBRARIES_CONFIG_FILE,
      scheme = StorageScheme.DIRECTORY_BASED)
  }
)
public class ResolveProjectLibrariesService extends ResolveLibrariesService<ResolveLibrariesState> {
  public static ResolveProjectLibrariesService getInstance(@NotNull Project project) {
    return ServiceManager.getService(project, ResolveProjectLibrariesService.class);
  }
}
