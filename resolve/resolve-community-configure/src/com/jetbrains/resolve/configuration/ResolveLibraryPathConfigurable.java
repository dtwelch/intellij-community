package com.jetbrains.resolve.configuration;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.FixedSizeButton;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.IconUtil;
import com.intellij.util.ui.JBUI;
import com.jetbrains.resolve.library.ResolveEnvUtil;
import com.jetbrains.resolve.library.ResolveLibrariesService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class ResolveLibraryPathConfigurable implements Configurable {

  private final ResolveLibrariesService<?> librariesService;
  private final Project project;
  private JPanel mainPanel;
  private JButton detailsButton;

  private final JBCheckBox useEnvResolvePathCheckBox = new JBCheckBox("Use RESOLVEPATH that's defined in the system environment");
  private ComboboxWithBrowseButton pathChooserCombo = new ComboboxWithBrowseButton();

  public ResolveLibraryPathConfigurable(@NotNull Project project, @NotNull ResolveLibrariesService librariesService) {
    this.project = project;
    this.librariesService = librariesService;
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
          append(file.getPresentableUrl(), item.defaultUrl ? SimpleTextAttributes.GRAY_ATTRIBUTES : SimpleTextAttributes.REGULAR_ATTRIBUTES);
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
  }

  public void initContent() {
    if (SystemInfo.isMac) {
      //OK, maybe just have some text that says on mac that the system will only see env vars if launched from
      //the terminal... it it is lauched from the dock, just give the default suggested home "$HOME/Documents/resolvework"   :-)
      useEnvResolvePathCheckBox.setEnabled(false);
    }
    //mess with useEnvResolvePathCheckBox, add listener to button, etc
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
    librariesService.setLibraryRootUrl(getSelectedPathUrl());
    if (librariesService instanceof GoApplicationLibrariesService) {
      ((GoApplicationLibrariesService)myLibrariesService).setUseGoPathFromSystemEnvironment(myUseEnvGoPathCheckBox.isSelected());
    }
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
