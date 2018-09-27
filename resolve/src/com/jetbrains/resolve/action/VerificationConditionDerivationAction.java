package com.jetbrains.resolve.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiFile;
import com.jetbrains.resolve.psi.ResFile;
import com.jetbrains.resolve.psi.ResPrecisModuleDecl;

public class VerificationConditionDerivationAction extends ResolveAction {

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

  }
}
