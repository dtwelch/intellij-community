package com.jetbrains.resolve.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.jetbrains.resolve.ResolveStudioController;
import com.jetbrains.resolve.psi.ResFile;
import com.jetbrains.resolve.psi.ResPrecisModuleDecl;
import edu.clemson.resolve.core.Main;
import edu.clemson.resolve.core.control.WindowUserInterfaceControl;
import edu.clemson.resolve.verifier.gui.MainWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;

public class VerificationConditionDerivationAction
  extends
  ResolveAction implements AnAction.TransparentUpdate {

  @Override
  public void update(AnActionEvent e) {
    PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
    if (file instanceof ResFile) {
      ResFile fileAsResFile = (ResFile)file;
      if (fileAsResFile.getEnclosedModule() instanceof ResPrecisModuleDecl) {
        e.getPresentation().setEnabled(false);
      }
    }
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getData(CommonDataKeys.PROJECT);
    if (project == null) return;

    VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
    if (resolveFile == null) {
      return;
    }

    ResolveStudioController controller = ResolveStudioController.getInstance(project);
    ResolveStudioController.showVerifierWindow(project);

    MainWindow mainWindow = controller.getMainVerifierWindowFrame();
    mainWindow.getUserInterface().registerSupplementalASCIIAbbreviations();
    mainWindow.getUserInterface().getEnvironment().vcs = true;
    ProgressManager.getInstance().run(new Task.Backgroundable(project, "Running VCGen...", false) {
      @Override
      public void run(@NotNull ProgressIndicator indicator) {
        mainWindow.loadProgram(new File(resolveFile.getPath()));
      }
    });

    //load all assertive code blocks into current session..


    //MainWindow actually instantiates control... which need
    //WindowUserInterfaceControl control = new WindowUserInterfaceControl(env, )
    //VerifierPanel verifierPanel = controller.getVerifierPanel();

  }
}
