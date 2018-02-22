package com.jetbrains.resolve.library;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;

@State(
  name = "RESOLVELibraries",
  storages = @Storage(file = StoragePathMacros.APP_CONFIG + "/" +
                             "resolveLibraries.xml")
)
public class ResolveApplicationLibrariesService
  extends ResolveLibrariesService<ResolveApplicationLibrariesService.RESOLVEApplicationLibrariesState> {

  @NotNull
  @Override
  protected RESOLVEApplicationLibrariesState createState() {
    return new RESOLVEApplicationLibrariesState();
  }

  public static ResolveApplicationLibrariesService getInstance() {
    return ServiceManager.getService(ResolveApplicationLibrariesService.class);
  }

  public void setUseGoPathFromSystemEnvironment(boolean useResPathFromSystemEnvironment) {
    if (state.isUsingRESOLVEPathFromSystemEnvironment() != useResPathFromSystemEnvironment) {
      state.setUseResolvePathFromSystemEnvironment(useResPathFromSystemEnvironment);
      if (!ResolveSdkUtil.getResolvePathRootsFromEnvironment().isEmpty()) {
        incModificationCount();
        ApplicationManager.getApplication().getMessageBus().syncPublisher(LIBRARIES_TOPIC).librariesChanged(getLibraryRootUrls());
      }
    }
  }

  public boolean isUseResolvePathFromSystemEnvironment() {
    return state.isUsingRESOLVEPathFromSystemEnvironment();
  }

  static class RESOLVEApplicationLibrariesState extends ResolveLibrariesState {
    private boolean useRESOLVEPathFromSystemEnvironment = true;

    boolean isUsingRESOLVEPathFromSystemEnvironment() {
      return useRESOLVEPathFromSystemEnvironment;
    }

    public void setUseResolvePathFromSystemEnvironment(boolean useResolvePathFromSystemEnvironment) {
      this.useRESOLVEPathFromSystemEnvironment = useResolvePathFromSystemEnvironment;
    }
  }
}
