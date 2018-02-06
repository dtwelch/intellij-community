package com.jetbrains.resolve.library;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ModificationTracker;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.messages.Topic;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class ResolveLibrariesService<T extends ResolveLibrariesState>
  extends
  SimpleModificationTracker implements PersistentStateComponent<T> {

  static final Topic<LibrariesListener> LIBRARIES_TOPIC = new Topic<>("libraries changes", LibrariesListener.class);
  protected final T state = createState();

  @NotNull
  @Override
  public T getState() {
    return state;
  }

  @Override
  public void loadState(T state) {
    XmlSerializerUtil.copyBean(state, this.state);
  }

  @NotNull
  protected T createState() {
    //noinspection unchecked
    return (T)new ResolveLibrariesState();
  }

  /*@NotNull
  public static Collection<? extends VirtualFile> getUserDefinedLibraries(@NotNull Module module) {
    Set<VirtualFile> result = ContainerUtil.newLinkedHashSet();
    ResolveModuleLibrariesService serv = ResolveModuleLibrariesService.getInstance(module);
    result.addAll(resolveLangRootsFromUrls(serv.getLibraryRootUrls()));
    result.addAll(getUserDefinedLibraries(module.getProject()));
    return result;
  }*/

  @NotNull
  public static Collection<? extends VirtualFile> getUserDefinedLibraries(@NotNull Project project) {
    Set<VirtualFile> result = ContainerUtil.newLinkedHashSet();
    result.addAll(resolveLangRootsFromUrls(ResolveProjectLibrariesService.getInstance(project).getLibraryRootUrls()));
    result.addAll(getUserDefinedLibraries());
    return result;
  }

  @NotNull
  public static Collection<? extends VirtualFile> getUserDefinedLibraries() {
    return resolveLangRootsFromUrls(ResolveApplicationLibrariesService.getInstance().getLibraryRootUrls());
  }

  @NotNull
  public static ModificationTracker[] getModificationTrackers(@NotNull Project project) {
    return new ModificationTracker[]{ResolveProjectLibrariesService.getInstance(project),
      ResolveApplicationLibrariesService.getInstance()};
  }


  public void setLibraryRootUrls(@NotNull String... libraryRootUrls) {
    setLibraryRootUrls(Arrays.asList(libraryRootUrls));
  }

  public void setLibraryRootUrls(@NotNull Collection<String> libraryRootUrls) {
    if (!state.getUrls().equals(libraryRootUrls)) {
      state.setUrls(libraryRootUrls);
      incModificationCount();
      ApplicationManager.getApplication()
        .getMessageBus()
        .syncPublisher(LIBRARIES_TOPIC)
        .librariesChanged(libraryRootUrls);
    }
  }

  @NotNull
  public Collection<String> getLibraryRootUrls() {
    return state.getUrls();
  }

  @NotNull
  private static Collection<? extends VirtualFile> resolveLangRootsFromUrls(@NotNull Collection<String> urls) {
    return ContainerUtil.mapNotNull(urls, new Function<String, VirtualFile>() {
      @Override
      public VirtualFile fun(String url) {
        return VirtualFileManager.getInstance().findFileByUrl(url);
      }
    });
  }

  public interface LibrariesListener {
    void librariesChanged(@NotNull Collection<String> newRootUrls);
  }
}
