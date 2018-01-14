package com.jetbrains.resolve.configuration;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.IconUtil;
import com.intellij.util.ui.JBUI;
import com.jetbrains.resolve.library.ResolveApplicationLibrariesService;
import com.jetbrains.resolve.library.ResolveEnvUtil;
import com.jetbrains.resolve.library.ResolveLibrariesService;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class ResolveLibraryPathConfigurable implements Configurable {

  private final ResolveLibrariesService<?> librariesService;
  private final Project project;
  private JPanel mainPanel;
  private final String[] defaultPaths;

  private JButton detailsButton;
  private ListItem defaultItem;
  private final JBCheckBox useEnvResolvePathCheckBox = new JBCheckBox("Use RESOLVEPATH that's defined in the system environment");
  private ComboboxWithBrowseButton pathChooserCombo = new ComboboxWithBrowseButton();

  public ResolveLibraryPathConfigurable(@NotNull Project project, @NotNull ResolveLibrariesService librariesService, String... defaultUrls) {
    this.project = project;
    this.librariesService = librariesService;
    this.defaultPaths = defaultUrls;
    layoutPanel();
    initContent();
  }

  public void layoutPanel() {
    mainPanel = new JPanel(new GridBagLayout());
    pathChooserCombo.getComboBox().setRenderer(new ColoredListCellRenderer() {
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
    detailsButton = pathChooserCombo.getButton();

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.NORTH;
    c.insets = JBUI.insets(2);
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.1;
    c.weighty = 1;
    mainPanel.add(pathChooserCombo, c);
    c.gridy = 1;
    c.anchor = GridBagConstraints.NORTH;
    c.weighty = 1;
    mainPanel.add(useEnvResolvePathCheckBox, c);
  }

  public void initContent() {
    defaultItem = new ListItem(ResolveEnvUtil.getDefaultResolvePath(), true);
    pathChooserCombo.getComboBox().addItem(defaultItem);
    useEnvResolvePathCheckBox.setSelected(true);
    useEnvResolvePathCheckBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        FileChooserDescriptor descriptor = ResolveSdkUtil.getResolveWorkspaceChooserDescriptor();
        descriptor.setForcedToUseIdeaFileChooser(true);
        /*FileChooser.chooseFiles(descriptor, null, ResolveSdkUtil.suggestResolveWorkspaceDirectory(), new Consumer<List<VirtualFile>>() {
          @Override
          public void consume(java.util.List<VirtualFile> files) {
            if (files.isEmpty()) return;
            VirtualFile newWorkspace = files.get(0);
            String newWorkspaceUrl = newWorkspace.getUrl();

            //I don't really want to add it if it matches the default though....
            pathChooserCombo.getComboBox().addItem(files.get(0));
            pathChooserCombo.getComboBox().addItem();
            getChildComponent().addItem(sdk);
            setSelectedSdk(sdk);
            ResolveSdkChooserCombo.this.notifyChanged(null);
          }
        });*/
      }
    });
  }

  @Override
  public void reset() {
    pathChooserCombo.getComboBox().removeAll();
    resetWorkspaceFromEnvironment();

  }

  private void resetWorkspaceFromEnvironment() {
    if (librariesService instanceof ResolveApplicationLibrariesService) {
      useEnvResolvePathCheckBox.setSelected(((ResolveApplicationLibrariesService)librariesService).isUseResolvePathFromSystemEnvironment());
      if (((ResolveApplicationLibrariesService)librariesService).isUseResolvePathFromSystemEnvironment()) {
        addReadOnlyPaths();
      }
      else {
        removeReadOnlyPaths();
      }
    }
  }

  private void addReadOnlyPaths() {
    for (String url : defaultPaths) {
      pathChooserCombo.getComboBox().addItem(new ListItem(url, true));
    }
  }

  private void removeReadOnlyPaths() {
    //for each element in the combo box, remove those that are marked as default.
    /*List<ListItem> toRemove = pathChooserCombo.getComboBox().get.stream().filter(item -> item.readOnly).collect(Collectors.toList());
    for (ListItem item : toRemove) {
      myListModel.remove(item);
    }*/
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
