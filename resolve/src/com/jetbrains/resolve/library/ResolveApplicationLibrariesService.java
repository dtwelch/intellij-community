package com.jetbrains.resolve.library;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import org.jetbrains.annotations.NotNull;

@State(
  name = "RESOLVELibraries",
  storages = @Storage(file = StoragePathMacros.APP_CONFIG + "/" +
                             "resolveLibraries.xml")
)
public class ResolveApplicationLibrariesService extends
                                                ResolveLibrariesService<ResolveApplicationLibrariesService
                                                                                                  .RESOLVEApplicationLibrariesState> {

  @NotNull
  @Override
  protected RESOLVEApplicationLibrariesState createState() {
    return new RESOLVEApplicationLibrariesState();
  }

  public static ResolveApplicationLibrariesService getInstance() {
    return ServiceManager.getService(ResolveApplicationLibrariesService.class);
  }

  public boolean isUsingRESOLVEPathFromSystemEnvironment() {
    return state.isUsingRESOLVEPathFromSystemEnvironment();
  }

  static class RESOLVEApplicationLibrariesState extends ResolveLibrariesState {
    private boolean useRESOLVEPathFromSystemEnvironment = true;

    boolean isUsingRESOLVEPathFromSystemEnvironment() {
      return useRESOLVEPathFromSystemEnvironment;
    }
  }
}
