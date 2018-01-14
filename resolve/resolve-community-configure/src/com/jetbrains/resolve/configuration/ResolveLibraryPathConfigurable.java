package com.jetbrains.resolve.configuration;

import com.intellij.openapi.fileChooser.FileChooser;
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
import com.jetbrains.resolve.ResolveBundle;
import com.jetbrains.resolve.ResolveConstants;
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

public class ResolveLibraryPathConfigurable implements Configurable {

  private final ResolveLibrariesService<?> librariesService;
  private final Project project;
  private JPanel mainPanel;
  private JButton detailsButton;
  private ListItem defaultItem;
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
    useEnvResolvePathCheckBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        FileChooserDescriptor descriptor = ResolveSdkUtil.getResolveWorkspaceChooserDescriptor();
        descriptor.setForcedToUseIdeaFileChooser(true);

        FileChooser.chooseFiles(descriptor, null, ResolveSdkUtil.suggestSdkDirectory(), new Consumer<List<VirtualFile>>() {
          @Override
          public void consume(List<VirtualFile> files) {
            if (files.size() != 1) return;
            VirtualFile homeDir = files.get(0);
            final ResolveConfigurableCompilerList interpreterList = ResolveConfigurableCompilerList.getInstance(null);
            final Sdk[] sdks = interpreterList.getModel().getSdks();

            Sdk sdk = SdkConfigurationUtil.setupSdk(sdks, homeDir, ResolveSdkType.getInstance(), true, null, null);
            //do this + the Project Model stuff here as well (see python srcs).
            if (sdk != null) {
              final ProjectSdksModel projectSdksModel = interpreterList.getModel();
              if (projectSdksModel.findSdk(sdk) == null) {
                projectSdksModel.addSdk(sdk);
                try {
                  projectSdksModel.apply();
                }
                catch (ConfigurationException e) {
                  LOG.error("Error adding new resolve compiler " + e.getMessage());
                }
              }
            }
            getChildComponent().addItem(sdk);
            setSelectedSdk(sdk);
            ResolveSdkChooserCombo.this.notifyChanged(null);
          }
        });
      }
    });
  }

  @Override
  public void reset() {
    updatePathList(false);
    //final Sdk sdk = getCurrentProjectSdk();
    //setSelectedSdk(sdk);
  }

  public void updatePathList(boolean preserveSelection) {
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
