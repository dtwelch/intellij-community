package com.jetbrains.resolve.newProject.steps;

import com.intellij.ide.util.projectWizard.AbstractNewProjectStep;
import com.intellij.ide.util.projectWizard.ProjectSettingsStepBase;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.platform.DirectoryProjectGenerator;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.HideableDecorator;
import com.intellij.util.Consumer;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.newProject.ResolveProjectGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProjectSpecificSettingsStep<T> extends ProjectSettingsStepBase<T> implements DumbAware {
  private boolean myInstallFramework;
  //@Nullable private ResolveAddSdkBox mySdkSelectionBox;

  public ProjectSpecificSettingsStep(@NotNull final DirectoryProjectGenerator<T> projectGenerator,
                                     @NotNull final AbstractNewProjectStep.AbstractCallback callback) {
    super(projectGenerator, callback);
  }

  @Override
  protected JPanel createAndFillContentPanel() {
    if (myProjectGenerator instanceof ResolveProjectGenerator) {
      // Allow generator to display custom error
      ((ResolveProjectGenerator<?>)myProjectGenerator).setErrorCallback(this::setErrorText);
    }
    return createContentPanelWithAdvancedSettingsPanel();
  }

  //This will use a box to just select the SDK we're going to use...
  @Nullable
  public Sdk getSdk() {
    /*if (!(myProjectGenerator instanceof ResolveProjectGenerator)) return null;
    final PyAddSdkGroupPanel interpreterPanel = mySdkSelectionBox;
    if (interpreterPanel == null) return null;
    final PyAddSdkPanel panel = interpreterPanel.getSelectedPanel();
    if (panel instanceof PyAddNewEnvironmentPanel) {
      final PyAddNewEnvironmentPanel newEnvironmentPanel = (PyAddNewEnvironmentPanel)panel;
      return new PyLazySdk("Uninitialized environment", newEnvironmentPanel::getOrCreateSdk);
    }
    else if (panel instanceof PyAddExistingSdkPanel) {
      return panel.getSdk();
    }
    else {*/
      return null;
   // }
  }
/*
  @Nullable
  private Sdk getInterpreterPanelSdk() {
    final PyAddSdkGroupPanel interpreterPanel = mySdkSelectionBox;
    if (interpreterPanel == null) return null;
    return interpreterPanel.getSdk();
  }

  public boolean installFramework() {
    return myInstallFramework;
  }

  @Override
  protected void registerValidators() {
    super.registerValidators();
    if (myProjectGenerator instanceof PythonProjectGenerator) {
      addLocationChangeListener(event -> {
        final String fileName = PathUtil.getFileName(getNewProjectPath());
        ((PythonProjectGenerator)myProjectGenerator).locationChanged(fileName);
      });
      final PyAddSdkGroupPanel interpreterPanel = mySdkSelectionBox;
      if (interpreterPanel != null) {
        UiNotifyConnector.doWhenFirstShown(interpreterPanel, this::checkValid);
      }
    }
  }

  /**
   * @return path for project on remote side provided by user
   */
  /*@Nullable
  final String getRemotePath() {
    final PyAddSdkGroupPanel interpreterPanel = mySdkSelectionBox;
    if (interpreterPanel == null) return null;
    final PyAddExistingSdkPanel panel = ObjectUtils.tryCast(interpreterPanel.getSelectedPanel(), PyAddExistingSdkPanel.class);
    if (panel == null) return null;
    return panel.getRemotePath();
  }*/

  @Override
  protected void initGeneratorListeners() {
    super.initGeneratorListeners();
   /* if (myProjectGenerator instanceof PythonProjectGenerator) {
      ((PythonProjectGenerator)myProjectGenerator).addSettingsStateListener(this::checkValid);
      myErrorLabel.addMouseListener(((PythonProjectGenerator)myProjectGenerator).getErrorLabelMouseListener());
    }*/
  }

  //THIS IS ACTUALLY IT RIGHT HERE....THIS IS WHAT WE WANT to MESS WITH TO ACHIEVE THE WAY THE GO PLUGIN DOES IT:
  @Override
  protected JPanel createBasePanel() {
    if (myProjectGenerator instanceof ResolveProjectGenerator) {
      final BorderLayout layout = new BorderLayout();

      final JPanel locationPanel = new JPanel(layout);

      final JPanel panel = new JPanel(new VerticalFlowLayout(0, 2));
      final LabeledComponent<TextFieldWithBrowseButton> location = createLocationComponent();

      locationPanel.add(location, BorderLayout.CENTER);
      panel.add(locationPanel);
      //panel.add(createSdkSelectionPanel());
      return panel;
    }

    return super.createBasePanel();
  }

  /*@NotNull
  private JPanel createSdkSelectionPanel() {
    final JPanel container = new JPanel(new BorderLayout());
    final JPanel decoratorPanel = new JPanel(new VerticalFlowLayout());

  }

  @NotNull
  private JPanel createInterpretersPanel() {
    final JPanel container = new JPanel(new BorderLayout());
    final JPanel decoratorPanel = new JPanel(new VerticalFlowLayout());

    final List<Sdk> existingSdks = getValidPythonSdks();
    final Sdk preferredSdk = getPreferredSdk(existingSdks);

    final String newProjectPath = getNewProjectPath();
    final PyAddNewEnvironmentPanel newEnvironmentPanel = new PyAddNewEnvironmentPanel(existingSdks, newProjectPath);
    final PyAddExistingSdkPanel existingSdkPanel = new PyAddExistingSdkPanel(null, existingSdks, newProjectPath, preferredSdk);

    final PyAddSdkPanel defaultPanel = PySdkSettings.getInstance().getUseNewEnvironmentForNewProject() ?
                                       newEnvironmentPanel : existingSdkPanel;
    final HideableDecorator decorator = new HideableDecorator(decoratorPanel, getProjectInterpreterTitle(defaultPanel), false);
    decorator.setContentComponent(container);

    final List<PyAddSdkPanel> panels = Arrays.asList(newEnvironmentPanel, existingSdkPanel);
    mySdkSelectionBox = new PyAddSdkGroupPanel("New project interpreter", getIcon(), panels, defaultPanel);
    mySdkSelectionBox.addChangeListener(() -> {
      decorator.setTitle(getProjectInterpreterTitle(mySdkSelectionBox.getSelectedPanel()));
      final boolean useNewEnvironment = mySdkSelectionBox.getSelectedPanel() instanceof PyAddNewEnvironmentPanel;
      PySdkSettings.getInstance().setUseNewEnvironmentForNewProject(useNewEnvironment);
      checkValid();
    });

    addLocationChangeListener(event -> mySdkSelectionBox.setNewProjectPath(getNewProjectPath()));

    container.add(mySdkSelectionBox, BorderLayout.NORTH);
    return decoratorPanel;
  }*/

  @NotNull
  private String getNewProjectPath() {
    final TextFieldWithBrowseButton field = myLocationField;
    if (field == null) return "";
    return field.getText().trim();
  }

  private void addLocationChangeListener(@NotNull Consumer<DocumentEvent> listener) {
    final TextFieldWithBrowseButton field = myLocationField;
    if (field == null) return;
    field.getTextField().getDocument().addDocumentListener(new DocumentAdapter() {
      @Override
      protected void textChanged(DocumentEvent e) {
        listener.consume(e);
      }
    });
  }
/*
  @NotNull
  private static String getProjectInterpreterTitle(@NotNull PyAddSdkPanel panel) {
    final String name;
    if (panel instanceof PyAddNewEnvironmentPanel) {
      name = "New " + ((PyAddNewEnvironmentPanel)panel).getSelectedPanel().getEnvName() + " environment";
    }
    else {
      final Sdk sdk = panel.getSdk();
      name = sdk != null ? sdk.getName() : panel.getPanelName();
    }
    return "Project Interpreter: " + name;
  }*/

  /*@Nullable
  private Sdk getPreferredSdk(@NotNull List<Sdk> sdks) {
    final PyFrameworkProjectGenerator projectGenerator = ObjectUtils.tryCast(getProjectGenerator(), PyFrameworkProjectGenerator.class);
    final boolean onlyPython2 = projectGenerator != null && !projectGenerator.supportsPython3();
    final Sdk preferred = ContainerUtil.getFirstItem(sdks);
    if (preferred == null) return null;
    if (onlyPython2 && PythonSdkType.getLanguageLevelForSdk(preferred).isAtLeast(LanguageLevel.PYTHON30)) {
      final Sdk python2Sdk = PythonSdkType.findPython2Sdk(sdks);
      return python2Sdk != null ? python2Sdk : preferred;
    }
    return preferred;
  }

  @NotNull
  private static List<Sdk> getValidPythonSdks() {
    final List<Sdk> pythonSdks = PyConfigurableInterpreterList.getInstance(null).getAllPythonSdks();
    Iterables.removeIf(pythonSdks, sdk -> !(sdk.getSdkType() instanceof PythonSdkType) ||
                                          PythonSdkType.isInvalid(sdk) ||
                                          PySdkExtKt.getAssociatedProjectPath(sdk) != null);
    Collections.sort(pythonSdks, new PreferredSdkComparator());
    return pythonSdks;
  }*/
}
