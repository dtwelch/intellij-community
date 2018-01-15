package com.jetbrains.resolve.configuration;

import com.intellij.openapi.fileChooser.FileChooserDialog;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.*;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBList;
import com.intellij.util.IconUtil;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.library.ResolveApplicationLibrariesService;
import com.jetbrains.resolve.library.ResolveLibrariesService;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ResolveLibraryPathConfigurable implements Configurable {

  private final ResolveLibrariesService<?> librariesService;
  private final Project project;
  private JPanel mainPanel = new JPanel(new BorderLayout());
  private final String[] defaultPaths;
  private TextFieldWithBrowseButton pathLocationField;
  private final CollectionListModel<ListItem> listModel = new CollectionListModel<>();

  private final JBCheckBox useEnvResolvePathCheckBox = new JBCheckBox("Defer to RESOLVEPATH that's defined in the system environment");
  private ComboboxWithBrowseButton pathChooserCombo = new ComboboxWithBrowseButton();

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
          append("[DEFAULT] ", SimpleTextAttributes.GRAY_ATTRIBUTES);
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
    ToolbarDecorator decorator = ToolbarDecorator.createDecorator(filesList).disableUpDownActions()
      .setAddAction(button -> {
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
      })
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

  @Override
  public boolean isModified() {
    return false;
  }

  @Override
  public void apply() throws ConfigurationException {
   /* librariesService.setLibraryRootUrl(getSelectedPathUrl());
    if (librariesService instanceof GoApplicationLibrariesService) {
      ((GoApplicationLibrariesService)myLibrariesService).setUseGoPathFromSystemEnvironment(myUseEnvGoPathCheckBox.isSelected());
    }*/
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
