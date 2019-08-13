package com.jetbrains.resolve;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.codeInsight.daemon.impl.DaemonEditorPopup;
import com.intellij.codeInsight.intention.IntentionActionBean;
import com.intellij.codeInsight.intention.IntentionManager;
import com.intellij.ide.AppLifecycleListener;
import com.intellij.ide.GeneralSettings;
import com.intellij.ide.SelectInTarget;
import com.intellij.ide.projectView.impl.AbstractProjectViewPane;
import com.intellij.ide.scopeView.ScopeViewPane;
import com.intellij.ide.ui.UISettings;
import com.intellij.ide.ui.customization.ActionUrl;
import com.intellij.ide.ui.customization.CustomActionsSchema;
import com.intellij.ide.ui.customization.CustomizationUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.EventLog;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.EditorSettingsExternalizable;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.extensions.ExtensionsArea;
import com.intellij.openapi.fileChooser.impl.FileChooserUtil;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import com.intellij.openapi.keymap.impl.KeymapImpl;
import com.intellij.openapi.keymap.impl.ui.Group;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.*;
import com.intellij.platform.DirectoryProjectConfigurator;
import com.intellij.platform.PlatformProjectViewOpener;
import com.intellij.projectImport.ProjectAttachProcessor;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.util.ui.tree.TreeUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dtwelch
 */
@SuppressWarnings({"UtilityClassWithoutPrivateConstructor", "UtilityClassWithPublicConstructor"})
public class ResolveStudioInitialConfigurator {
  @NonNls private static final String DISPLAYED_PROPERTY = "ResolveStudio.initialConfigurationShown";

  @NonNls private static final String CONFIGURED = "ResolveStudio.InitialConfiguration";
  @NonNls private static final String CONFIGURED_V1 = "ResolveStudio.InitialConfiguration.V1";
  @NonNls private static final String CONFIGURED_V2 = "ResolveStudio.InitialConfiguration.V2";

  private static final Set<String> HIDDEN_ACTIONS =
    ContainerUtil.newHashSet("CopyAsPlainText", "CopyAsRichText", "EditorPasteSimple", "Local History", "Column Selection Mode",
                             "Folding", "Generate", "CompareClipboardWithSelection", "ChangeFileEncodingAction",
                             "CloseAllUnmodifiedEditors", "CloseAllUnpinnedEditors", "CloseAllEditorsButActive", "CopyReference",
                             "MoveTabRight", "MoveTabDown", "External Tools", "MoveEditorToOppositeTabGroup",
                             "OpenEditorInOppositeTabGroup", "ChangeSplitOrientation", "PinActiveTab", "Tabs Placement",
                             "TabsAlphabeticalMode", "AddNewTabToTheEndMode", "NextTab", "PreviousTab", "Add to Favorites",
                             "Add All To Favorites", "ValidateXml", "NewHtmlFile", "Images.ShowThumbnails",
                             "CompareFileWithEditor", "SynchronizeCurrentFile", "EditorToggleColumnMode",
                             "Mark Directory As", "CompareTwoFiles", "ShowFilePath",
                             "ChangesView.ApplyPatch", "TemplateProjectProperties",
                             "ExportToHTML", "SaveAll", "Export/Import Actions",
                             "Synchronize",
                             "Line Separators", "ToggleReadOnlyAttribute",
                             "Macros", "EditorToggleCase", "EditorJoinLines",
                             "FillParagraph",
                             "Convert Indents", "TemplateParametersNavigation",
                             "EscapeEntities", "Inspect Code",
                             "QuickDefinition", "ExpressionTypeInfo", "EditorContextInfo",
                             "ShowErrorDescription", "RecentChanges", "CompareActions",
                             "GotoCustomRegion", "JumpToLastChange", "JumpToNextChange",
                             "SelectIn", "GotoTypeDeclaration", "QuickChangeScheme",
                             "GotoTest", "GotoRelated", "Hierarchy Actions", "Bookmarks",
                             "Goto Error/Bookmark Actions", "GoToEditPointGroup",
                             "Change Navigation Actions", "Method Navigation Actions",
                             "EvaluateExpression", "Pause", "ViewBreakpoints", "SaveAs");

  public static class First {
    public First() {
      patchRootAreaExtensions();
    }
  }

  /**
   * @noinspection UnusedParameters
   */
  public ResolveStudioInitialConfigurator(MessageBus bus,
                                          CodeInsightSettings codeInsightSettings,
                                          final PropertiesComponent propertiesComponent,
                                          FileTypeManager fileTypeManager,
                                          final ProjectManagerEx projectManager) {
    final UISettings uiSettings = UISettings.getInstance();

    //if (!propertiesComponent.getBoolean(CONFIGURED_V2)) {
      EditorSettingsExternalizable editorSettings = EditorSettingsExternalizable.getInstance();
      editorSettings.setEnsureNewLineAtEOF(true);
      propertiesComponent.setValue(CONFIGURED_V2, true);
    //}
   // if (!propertiesComponent.getBoolean(CONFIGURED_V1)) {
      patchMainMenu();
      propertiesComponent.setValue(CONFIGURED_V1, true);
      propertiesComponent.setValue("ShowDocumentationInToolWindow", true);
    //}

    if (!propertiesComponent.getBoolean(CONFIGURED)) {
      propertiesComponent.setValue(CONFIGURED, "true");
      propertiesComponent.setValue("toolwindow.stripes.buttons.info.shown", "true");

      uiSettings.setHideToolStripes(false);
      uiSettings.setShowMemoryIndicator(false);
      uiSettings.setShowDirectoryForNonUniqueFilenames(true);
      uiSettings.setShowMainToolbar(false);
      uiSettings.setShowNavigationBar(false);
      codeInsightSettings.REFORMAT_ON_PASTE = CodeInsightSettings.NO_REFORMAT;

      GeneralSettings.getInstance().setShowTipsOnStartup(false);
      EditorSettingsExternalizable.getInstance().setVirtualSpace(false);
      EditorSettingsExternalizable.getInstance().getOptions().ARE_LINE_NUMBERS_SHOWN = true;

      uiSettings.setShowDirectoryForNonUniqueFilenames(true);
      uiSettings.setShowMemoryIndicator(false);
    }
    //TODO: Maybe move the next 5 lines into the !CONFIGURED if stmt above.
    final EditorColorsScheme editorColorsScheme = EditorColorsManager.getInstance().getScheme(EditorColorsScheme.DEFAULT_SCHEME_NAME);
    editorColorsScheme.setEditorFontSize(12);
    editorColorsScheme.setEditorFontName("IsabelleText");
    editorColorsScheme.setLineSpacing(1);

    EditorColorsManager.getInstance().getGlobalScheme().setEditorFontSize(12);
    EditorColorsManager.getInstance().getGlobalScheme().setEditorFontName("IsabelleText");
    EditorColorsManager.getInstance().getGlobalScheme().setLineSpacing(1);

    MessageBusConnection connection = bus.connect();
    connection.subscribe(AppLifecycleListener.TOPIC, new AppLifecycleListener() {
      @Override
      public void welcomeScreenDisplayed() {
        if (!propertiesComponent.isValueSet(DISPLAYED_PROPERTY)) {
          ApplicationManager.getApplication().invokeLater(() -> {
            if (!propertiesComponent.isValueSet(DISPLAYED_PROPERTY)) {
              GeneralSettings.getInstance().setShowTipsOnStartup(false);
              patchKeymap();
              propertiesComponent.setValue(DISPLAYED_PROPERTY, "true");
            }
          });
        }
      }
    });

    connection.subscribe(ProjectManager.TOPIC, new ProjectManagerListener() {
      @Override
      public void projectOpened(final Project project) {
        patchProjectAreaExtensions(project);
      }
    });
  }

  private static void patchMainMenu() {
    final CustomActionsSchema schema = new CustomActionsSchema();

    final JTree actionsTree = new Tree();
    Group rootGroup = new Group("root", null, null);
    final DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootGroup);
    DefaultTreeModel model = new DefaultTreeModel(root);
    actionsTree.setModel(model);

    schema.fillActionGroups(root);
    for (int i = 0; i < root.getChildCount(); i++) {
      final DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)root.getChildAt(i);
      if ("Main menu".equals(getItemId(treeNode))) {
        hideActionFromMainMenu(root, schema, treeNode);
      }
      hideActions(schema, root, treeNode, HIDDEN_ACTIONS);
    }
    CustomActionsSchema.getInstance().copyFrom(schema);
  }

  private static void hideActionFromMainMenu(@NotNull final DefaultMutableTreeNode root,
                                             @NotNull final CustomActionsSchema schema, DefaultMutableTreeNode mainMenu) {
    final HashSet<String> menuItems = ContainerUtil.newHashSet("VCS", "Refactor", "Window", "Run");
    hideActions(schema, root, mainMenu, menuItems);
  }

  private static final Set<String> toHide =
    ContainerUtil.newHashSet("Editor Popup Menu Actions (2)", "Editor Popup Menu Actions (1)",
                             "EditorPopupMenu1.FindRefactor", "XML Generate Actions", "Editor Popup Menu", "Debug Actions");

  private static void hideActions(@NotNull CustomActionsSchema schema, @NotNull DefaultMutableTreeNode root,
                                  @NotNull final TreeNode actionGroup, Set<String> items) {
    for (int i = 0; i < actionGroup.getChildCount(); i++) {
      final DefaultMutableTreeNode child = (DefaultMutableTreeNode)actionGroup.getChildAt(i);
      final int childCount = child.getChildCount();
      final String childId = getItemId(child);
      if (toHide.contains(root.toString()) && child.toString().equals("Separator (null)")) {
        final TreePath treePath = TreeUtil.getPath(root, child);
        final ActionUrl url = CustomizationUtil.getActionUrl(treePath, ActionUrl.DELETED);
        schema.addAction(url);
      }
      if (childId != null && items.contains(childId)) {
        final TreePath treePath = TreeUtil.getPath(root, child);
        final ActionUrl url = CustomizationUtil.getActionUrl(treePath, ActionUrl.DELETED);
        schema.addAction(url);
      }
      else if (childCount > 0) {
        hideActions(schema, child, child, items);
      }
    }
  }

  @Nullable
  private static String getItemId(@NotNull final DefaultMutableTreeNode child) {
    final Object userObject = child.getUserObject();
    if (userObject instanceof String) return (String)userObject;
    return userObject instanceof Group ? ((Group)userObject).getName() : null;
  }

  private static void patchRootAreaExtensions() {
    ExtensionsArea rootArea = Extensions.getArea(null);
    //System.setProperty("apple.laf.useScreenMenuBar", "true");

    //rootArea.unregisterExtensionPoint("com.intellij.runLineMarkerContributor");
    for (ToolWindowEP ep : Extensions.getExtensions(ToolWindowEP.EP_NAME)) {
      if (ToolWindowId.FAVORITES_VIEW.equals(ep.id) || ToolWindowId.TODO_VIEW.equals(ep.id) || EventLog.LOG_TOOL_WINDOW_ID.equals(ep.id)) {
        rootArea.getExtensionPoint(ToolWindowEP.EP_NAME).unregisterExtension(ep);
      }
    }

    for (DirectoryProjectConfigurator ep : Extensions.getExtensions(DirectoryProjectConfigurator.EP_NAME)) {
      if (ep instanceof PlatformProjectViewOpener) {
        rootArea.getExtensionPoint(DirectoryProjectConfigurator.EP_NAME).unregisterExtension(ep);
      }
    }

    // unregister unrelated tips
    /*for (TipAndTrickBean tip : Extensions.getExtensions(TipAndTrickBean.EP_NAME)) {
      if (UNRELATED_TIPS.contains(tip.fileName)) {
        rootArea.getExtensionPoint(TipAndTrickBean.EP_NAME).unregisterExtension(tip);
      }
    }*/

    for (IntentionActionBean ep : Extensions.getExtensions(IntentionManager.EP_INTENTION_ACTIONS)) {
      if ("org.intellij.lang.regexp.intention.CheckRegExpIntentionAction".equals(ep.className)) {
        rootArea.getExtensionPoint(IntentionManager.EP_INTENTION_ACTIONS).unregisterExtension(ep);
      }
    }

    final ExtensionPoint<ProjectAttachProcessor> point = Extensions.getRootArea().getExtensionPoint(ProjectAttachProcessor.EP_NAME);
    for (ProjectAttachProcessor attachProcessor : Extensions.getExtensions(ProjectAttachProcessor.EP_NAME)) {
      point.unregisterExtension(attachProcessor);
    }
  }

  private static void patchProjectAreaExtensions(@NotNull final Project project) {
    ExtensionsArea projectArea = Extensions.getArea(project);

    for (SelectInTarget target : Extensions.getExtensions(SelectInTarget.EP_NAME, project)) {
      if (ToolWindowId.FAVORITES_VIEW
        .equals(target.getToolWindowId()) /*|| ToolWindowId.STRUCTURE_VIEW.equals(target.getToolWindowId())*/) {
        projectArea.getExtensionPoint(SelectInTarget.EP_NAME).unregisterExtension(target);
      }
    }

    for (AbstractProjectViewPane pane : Extensions.getExtensions(AbstractProjectViewPane.EP_NAME, project)) {
      if (pane.getId().equals(ScopeViewPane.ID)) {
        Disposer.dispose(pane);
        projectArea.getExtensionPoint(AbstractProjectViewPane.EP_NAME).unregisterExtension(pane);
      }
    }
  }

  private static void patchKeymap() {
    Set<String> droppedActions = ContainerUtil.newHashSet(
      "AddToFavoritesPopup",
      "DatabaseView.ImportDataSources",
      "CompileDirty", "Compile",
      // hidden
      "AddNewFavoritesList", "EditFavorites", "RenameFavoritesList", "RemoveFavoritesList");
    KeymapManagerEx keymapManager = KeymapManagerEx.getInstanceEx();

    for (Keymap keymap : keymapManager.getAllKeymaps()) {
      if (keymap.canModify()) continue;

      KeymapImpl keymapImpl = (KeymapImpl)keymap;

      for (String id : keymapImpl.getOwnActionIds()) {
        if (droppedActions.contains(id)) keymapImpl.clearOwnActionsId(id);
      }
    }
  }
}
