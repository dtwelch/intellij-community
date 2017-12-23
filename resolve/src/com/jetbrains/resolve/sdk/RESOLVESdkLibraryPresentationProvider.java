package com.jetbrains.resolve.sdk;

import com.intellij.openapi.roots.libraries.DummyLibraryProperties;
import com.intellij.openapi.roots.libraries.LibraryKind;
import com.intellij.openapi.roots.libraries.LibraryPresentationProvider;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RESOLVESdkLibraryPresentationProvider extends LibraryPresentationProvider<DummyLibraryProperties> {

  private static final LibraryKind KIND = LibraryKind.create("resolve");

  public RESOLVESdkLibraryPresentationProvider() {
    super(KIND);
  }

  @Override
  @Nullable
  public DummyLibraryProperties detect(
    @NotNull List<VirtualFile> classesRoots) {
    for (VirtualFile root : classesRoots) {
      if (ResolveSdkUtil.isResolveSdkLibRoot(root)) {
        return DummyLibraryProperties.INSTANCE;
      }
    }
    return null;
  }
}
