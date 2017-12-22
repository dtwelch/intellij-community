package com.jetbrains.resolve.project;

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
public class RESOLVEApplicationLibrariesService extends
                                                RESOLVELibrariesService<RESOLVEApplicationLibrariesService
                                                  .RESOLVEApplicationLibrariesState> {

  @NotNull
  @Override
  protected RESOLVEApplicationLibrariesState createState() {
    return new RESOLVEApplicationLibrariesState();
  }

  public static RESOLVEApplicationLibrariesService getInstance() {
    return ServiceManager.getService(RESOLVEApplicationLibrariesService.class);
  }

  public boolean isUsingRESOLVEPathFromSystemEnvironment() {
    return state.isUsingRESOLVEPathFromSystemEnvironment();
  }

  public void setUsingRESOLVEPathFromSystemEnvironment(boolean useRESPATHfromEnv) {
    if (state.isUsingRESOLVEPathFromSystemEnvironment() != useRESPATHfromEnv) {
      state.setUsingRESOLVEPathFromSystemEnvironment(useRESPATHfromEnv);
      if (!ResolveSdkUtil.getResolvePathsRootsFromEnvironment().isEmpty()) {
        incModificationCount();
        ApplicationManager.getApplication()
          .getMessageBus()
          .syncPublisher(LIBRARIES_TOPIC)
          .librariesChanged(getLibraryRootUrls());
      }
    }
  }

  static class RESOLVEApplicationLibrariesState extends RESOLVELibrariesState {
    private boolean useRESOLVEPathFromSystemEnvironment = true;

    boolean isUsingRESOLVEPathFromSystemEnvironment() {
      return useRESOLVEPathFromSystemEnvironment;
    }

    void setUsingRESOLVEPathFromSystemEnvironment(boolean useResPathFromSysEnv) {
      useRESOLVEPathFromSystemEnvironment = useResPathFromSysEnv;
    }
  }
}
