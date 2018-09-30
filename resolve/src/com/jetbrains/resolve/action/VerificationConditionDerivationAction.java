package com.jetbrains.resolve.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.jetbrains.resolve.ResolveStudioController;
import com.jetbrains.resolve.psi.ResFile;
import com.jetbrains.resolve.psi.ResPrecisModuleDecl;
import edu.clemson.resolve.core.Main;

import javax.swing.*;

public class VerificationConditionDerivationAction
  extends
  ResolveAction implements AnAction.TransparentUpdate {

  @Override
  public void update(AnActionEvent e) {
    PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
    if (file instanceof ResFile) {
      ResFile fileAsResFile = (ResFile) file;
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

    ResolveStudioController controller = ResolveStudioController.getInstance(project);
    ResolveStudioController.showVerifierWindow(project);

    Main.InitConfig env = new Main.InitConfig();

    //MainWindow actually instantiates control... which need
    //WindowUserInterfaceControl control = new WindowUserInterfaceControl(env, )
    //VerifierPanel verifierPanel = controller.getVerifierPanel();

  }
}
