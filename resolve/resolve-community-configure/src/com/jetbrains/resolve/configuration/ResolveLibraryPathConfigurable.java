package com.jetbrains.resolve.configuration;

import com.intellij.openapi.fileChooser.FileChooserDialog;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.*;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBList;
import com.intellij.util.IconUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.ui.UIUtil;
import com.jetbrains.resolve.library.ResolveApplicationLibrariesService;
import com.jetbrains.resolve.library.ResolveLibrariesService;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A configurable for adjusting the project's resolve path settings. Note that much of this class is derived from functionality
 * already present in the go plugin located here: https://github.com/go-lang-plugin-org/go-lang-idea-plugin
 * <p>
 * Here are some things that need doing still that have implications to the design and orginization of this configurable:
 * <ul>
 * <li>Adjust {@code ResolveLibrariesService} to track only a single url</li>
 * <li>Change this class accordingly; make sure users can only add a single url</li>
 * <li>Figure out what the difference between {@link ResolveApplicationLibrariesService} and
 * {@link com.jetbrains.resolve.library.ResolveProjectLibrariesService}</li>
 * <li>Change this class accordingly; make sure users can only add a single url</li>
 * </ul>
 */
public class ResolveLibraryPathConfigurable implements Configurable {

  private final ResolveLibrariesService<?> librariesService;
  private final Project project;
  private JPanel mainPanel = new JPanel(new BorderLayout());
  private final String[] defaultPaths;
  private final CollectionListModel<ListItem> listModel = new CollectionListModel<>();
  private final JBCheckBox useEnvResolvePathCheckBox = new JBCheckBox("Defer to RESOLVEPATH that's defined in the system environment");

  public ResolveLibraryPathConfigurable(@NotNull Project project,
                                        @NotNull ResolveLibrariesService librariesService,
                                        String... defaultUrls) {
    this.project = project;
    this.librariesService = librariesService;
    this.defaultPaths = defaultUrls;

    JBList filesList = new JBList(listModel);
    filesList.setCellRenderer(new ColoredListCellRenderer() {
      @Override
      protected void customizeCellRenderer(@NotNull JList list, Object value, int index, boolean selected, boolean hasFocus) {
        ListItem item = (ListItem)value;
        String url = item.url;
        if (item.defaultUrl) {
          append("[Default] ", SimpleTextAttributes.GRAY_ATTRIBUTES);
        }
        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(url);
        if (file != null) {
          append(file.getPresentableUrl(),
                 item.defaultUrl ? SimpleTextAttributes.GRAY_ATTRIBUTES : SimpleTextAttributes.REGULAR_ATTRIBUTES);
          setIcon(IconUtil.getIcon(file, Iconable.ICON_FLAG_READ_STATUS, null));
        }
        else {
          append(VfsUtilCore.urlToPath(url), SimpleTextAttributes.ERROR_ATTRIBUTES);
        }
      }
    });
    AnActionButtonRunnable addAction = new AnActionButtonRunnable() {
      @Override
      public void run(AnActionButton button) {
        FileChooserDialog fileChooser = FileChooserFactory.getInstance()
          .createFileChooser(ResolveSdkUtil.getResolveWorkspaceChooserDescriptor(), null, filesList);

        VirtualFile fileToSelect = null;
        ListItem lastItem = ContainerUtil.getLastItem(listModel.getItems());
        if (lastItem != null) {
          fileToSelect = VirtualFileManager.getInstance().findFileByUrl(lastItem.url);
        }

        VirtualFile[] newDirectories = fileChooser.choose(null, fileToSelect);
        if (newDirectories.length > 0) {
          for (VirtualFile newDirectory : newDirectories) {
            String newDirectoryUrl = newDirectory.getUrl();
            boolean alreadyAdded = false;
            for (ListItem item : listModel.getItems()) {
              if (newDirectoryUrl.equals(item.url) && !item.defaultUrl) {
                filesList.clearSelection();
                filesList.setSelectedValue(item, true);
                // scrollToSelection(filesList);
                alreadyAdded = true;
                break;
              }
            }
            if (!alreadyAdded) {
              listModel.add(new ListItem(newDirectoryUrl, false));
            }
          }
        }
      }
    };
    ToolbarDecorator decorator = ToolbarDecorator.createDecorator(filesList).disableUpDownActions()
      .setAddAction(addAction)
      .setAddActionUpdater(e -> !useEnvResolvePathCheckBox.isSelected())
      .setRemoveActionUpdater(event -> {
        for (Object selectedValue : filesList.getSelectedValuesList()) {
          if (((ListItem)selectedValue).defaultUrl) {
            return false;
          }
        }
        return true;
      })
      .setRemoveAction(button -> {
        for (Object selectedValue : filesList.getSelectedValuesList()) {
          listModel.remove((ListItem)selectedValue);
        }
      });
    mainPanel.add(decorator.createPanel(), BorderLayout.CENTER);
    if (librariesService instanceof ResolveApplicationLibrariesService) {
      useEnvResolvePathCheckBox.addActionListener(event -> {
        if (useEnvResolvePathCheckBox.isSelected()) {
          addDefaultReadOnlyPaths();
        }
        else {
          removeDefaultReadOnlyPaths();
        }
      });
      mainPanel.add(useEnvResolvePathCheckBox, BorderLayout.SOUTH);
    }
  }

  @Override
  public void reset() {
    listModel.removeAll();
    resetWorkspaceFromEnvironment();
    for (String url : librariesService.getLibraryRootUrls()) {
      listModel.add(new ListItem(url, false));
    }
  }

  private void resetWorkspaceFromEnvironment() {
    if (librariesService instanceof ResolveApplicationLibrariesService) {
      useEnvResolvePathCheckBox.setSelected(((ResolveApplicationLibrariesService)librariesService).isUseResolvePathFromSystemEnvironment());
      if (((ResolveApplicationLibrariesService)librariesService).isUseResolvePathFromSystemEnvironment()) {
        addDefaultReadOnlyPaths();
      }
      else {
        removeDefaultReadOnlyPaths();
      }
    }
  }

  private void addDefaultReadOnlyPaths() {
    for (String url : defaultPaths) {
      listModel.add(new ListItem(url, true));
    }
  }

  private void removeDefaultReadOnlyPaths() {
    List<ListItem> toRemove = listModel.getItems().stream().filter(item -> item.defaultUrl).collect(Collectors.toList());
    for (ListItem item : toRemove) {
      listModel.remove(item);
    }
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "RESOLVEPATH";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return mainPanel;
  }

  @NotNull
  private Collection<String> getUserDefinedUrls() {
    Collection<String> libraryUrls = ContainerUtil.newArrayList();
    for (ListItem item : listModel.getItems()) {
      if (!item.defaultUrl) {
        libraryUrls.add(item.url);
      }
    }
    return libraryUrls;
  }

  @Override
  public boolean isModified() {
    return !getUserDefinedUrls().equals(librariesService.getLibraryRootUrls()) ||
           librariesService instanceof ResolveApplicationLibrariesService &&
           ((ResolveApplicationLibrariesService)librariesService).isUseResolvePathFromSystemEnvironment() !=
           useEnvResolvePathCheckBox.isSelected();
  }

  @Override
  public void apply() throws ConfigurationException {
    librariesService.setLibraryRootUrls(getUserDefinedUrls());
    if (librariesService instanceof ResolveApplicationLibrariesService) {
      ((ResolveApplicationLibrariesService)librariesService).setUseGoPathFromSystemEnvironment(useEnvResolvePathCheckBox.isSelected());
    }
  }

  @Override
  public void disposeUIResources() {
    UIUtil.dispose(useEnvResolvePathCheckBox);
    UIUtil.dispose(mainPanel);
    listModel.removeAll();
  }

  private static class ListItem {
    final boolean defaultUrl;
    final String url;

    public ListItem(@NotNull String url, boolean defaultUrl) {
      this.defaultUrl = defaultUrl;
      this.url = url;
    }
  }
}
