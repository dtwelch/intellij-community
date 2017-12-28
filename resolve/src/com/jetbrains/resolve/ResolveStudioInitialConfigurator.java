package com.jetbrains.resolve;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.codeInsight.intention.IntentionActionBean;
import com.intellij.codeInsight.intention.IntentionManager;
import com.intellij.ide.AppLifecycleListener;
import com.intellij.ide.GeneralSettings;
import com.intellij.ide.ui.UISettings;
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
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.*;
import com.intellij.platform.DirectoryProjectConfigurator;
import com.intellij.platform.PlatformProjectViewOpener;
import com.intellij.profile.codeInspection.InspectionProjectProfileManager;
import com.intellij.projectImport.ProjectAttachProcessor;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
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
  @NonNls private static final String CONFIGURED_V3 = "ResolveStudio.InitialConfiguration.V3";
  @NonNls private static final String CONFIGURED_V4 = "ResolveStudio.InitialConfiguration.V4";

  private static final Set<String> HIDDEN_ACTIONS = ContainerUtil.newHashSet("CopyAsPlainText", "CopyAsRichText", "EditorPasteSimple",
                                                                             "Folding", "Generate", "CompareClipboardWithSelection",
                                                                             "ChangeFileEncodingAction", "CloseAllUnmodifiedEditors",
                                                                             "CloseAllUnpinnedEditors", "CloseAllEditorsButActive",
                                                                             "CopyReference", "MoveTabRight", "MoveTabDown",
                                                                             "External Tools",
                                                                             "MoveEditorToOppositeTabGroup", "OpenEditorInOppositeTabGroup",
                                                                             "ChangeSplitOrientation", "PinActiveTab", "Tabs Placement",
                                                                             "TabsAlphabeticalMode", "AddNewTabToTheEndMode", "NextTab",
                                                                             "PreviousTab", "Add to Favorites", "Add All To Favorites",
                                                                             "ValidateXml", "NewHtmlFile", "Images.ShowThumbnails",
                                                                             "CompareFileWithEditor", "SynchronizeCurrentFile",
                                                                             "Mark Directory As", "CompareTwoFiles", "ShowFilePath",
                                                                             "ChangesView.ApplyPatch", "TemplateProjectProperties",
                                                                             "ExportToHTML", "SaveAll", "Export/Import Actions",
                                                                             "Line Separators", "ToggleReadOnlyAttribute",
                                                                             "Macros", "EditorToggleCase", "EditorJoinLines",
                                                                             "FillParagraph",
                                                                             "Convert Indents", "TemplateParametersNavigation",
                                                                             "EscapeEntities",
                                                                             "QuickDefinition", "ExpressionTypeInfo", "EditorContextInfo",
                                                                             "ShowErrorDescription", "RecentChanges", "CompareActions",
                                                                             "GotoCustomRegion", "JumpToLastChange", "JumpToNextChange",
                                                                             "SelectIn", "GotoTypeDeclaration", "QuickChangeScheme",
                                                                             "GotoTest", "GotoRelated", "Hierarchy Actions", "Bookmarks",
                                                                             "Goto Error/Bookmark Actions", "GoToEditPointGroup",
                                                                             "Change Navigation Actions", "Method Navigation Actions",
                                                                             "EvaluateExpression", "Pause", "ViewBreakpoints", "SaveAs");

  /**
   * @noinspection UnusedParameters
   */
  public ResolveStudioInitialConfigurator(MessageBus bus,
                                          CodeInsightSettings codeInsightSettings,
                                          final PropertiesComponent propertiesComponent,
                                          FileTypeManager fileTypeManager,
                                          final ProjectManagerEx projectManager) {
    final UISettings uiSettings = UISettings.getInstance();

    if (!propertiesComponent.getBoolean(CONFIGURED_V2)) {
      EditorSettingsExternalizable editorSettings = EditorSettingsExternalizable.getInstance();
      editorSettings.setEnsureNewLineAtEOF(true);
      propertiesComponent.setValue(CONFIGURED_V2, true);
    }
    if (!propertiesComponent.getBoolean(CONFIGURED_V1)) {
      patchMainMenu();
      uiSettings.setShowNavigationBar(false);
      propertiesComponent.setValue(CONFIGURED_V1, true);
      propertiesComponent.setValue("ShowDocumentationInToolWindow", true);
    }

    if (!propertiesComponent.getBoolean(CONFIGURED)) {
      propertiesComponent.setValue(CONFIGURED, "true");
      propertiesComponent.setValue("toolwindow.stripes.buttons.info.shown", "true");

      uiSettings.setHideToolStripes(false);
      uiSettings.setShowMemoryIndicator(false);
      uiSettings.setShowDirectoryForNonUniqueFilenames(true);
      uiSettings.setShowMainToolbar(false);

      codeInsightSettings.REFORMAT_ON_PASTE = CodeInsightSettings.NO_REFORMAT;

      GeneralSettings.getInstance().setShowTipsOnStartup(false);

      EditorSettingsExternalizable.getInstance().setVirtualSpace(false);
      EditorSettingsExternalizable.getInstance().getOptions().ARE_LINE_NUMBERS_SHOWN = true;
      final CodeStyleSettings settings = CodeStyleSettingsManager.getInstance().getCurrentSettings();
      settings.getCommonSettings(PythonLanguage.getInstance()).ALIGN_MULTILINE_PARAMETERS_IN_CALLS = true;
      uiSettings.setShowDirectoryForNonUniqueFilenames(true);
      uiSettings.setShowMemoryIndicator(false);
      final String ignoredFilesList = fileTypeManager.getIgnoredFilesList();
      ApplicationManager
        .getApplication().invokeLater(() -> ApplicationManager.getApplication()
        .runWriteAction(() -> FileTypeManager.getInstance().setIgnoredFilesList(ignoredFilesList + ";*$py.class")));
      PyCodeInsightSettings.getInstance().SHOW_IMPORT_POPUP = false;
    }
    final EditorColorsScheme editorColorsScheme = EditorColorsManager.getInstance().getScheme(EditorColorsScheme.DEFAULT_SCHEME_NAME);
    editorColorsScheme.setEditorFontSize(14);

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

      //??
      @Override
      public void appFrameCreated(String[] commandLineArgs, @NotNull Ref<Boolean> willOpenProject) {
        if (!propertiesComponent.isValueSet(CONFIGURED_V3)) {
          propertiesComponent.setValue(CONFIGURED_V3, "true");
        }
      }
    });

    connection.subscribe(ProjectManager.TOPIC, new ProjectManagerListener() {
      @Override
      public void projectOpened(final Project project) {
        if (FileChooserUtil.getLastOpenedFile(project) == null) {
          FileChooserUtil.setLastOpenedFile(project, VfsUtil.getUserHomeDir());
        }

        patchProjectAreaExtensions(project);

        StartupManager.getInstance(project).runWhenProjectIsInitialized(new DumbAwareRunnable() {
          @Override
          public void run() {
            if (project.isDisposed()) return;
            updateInspectionsProfile();
            openProjectStructure();
          }

          private void openProjectStructure() {
            ToolWindowManager.getInstance(project).invokeLater(new Runnable() {
              int count = 0;

              @Override
              public void run() {
                if (project.isDisposed()) return;
                if (count++ < 3) { // we need to call this after ToolWindowManagerImpl.registerToolWindowsFromBeans
                  ToolWindowManager.getInstance(project).invokeLater(this);
                  return;
                }
                ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Project");
                if (toolWindow != null && toolWindow.getType() != ToolWindowType.SLIDING) {
                  toolWindow.activate(null);
                }
              }
            });
          }

          private void updateInspectionsProfile() {
            final String[] codes = new String[]{"W29", "E501"};
            final VirtualFile baseDir = project.getBaseDir();
            final PsiDirectory directory = PsiManager.getInstance(project).findDirectory(baseDir);
            if (directory != null) {
              InspectionProjectProfileManager.getInstance(project).getCurrentProfile().modifyToolSettings(
                Key.<PyPep8Inspection>create(PyPep8Inspection.INSPECTION_SHORT_NAME), directory,
                inspection -> Collections.addAll(inspection.ignoredErrors, codes)
              );
            }
          }
        });
      }
    });
  }

  private static void patchRootAreaExtensions() {
    ExtensionsArea rootArea = Extensions.getArea(null);

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
}
