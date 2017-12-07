package com.jetbrains.resolve.newProject;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ResolveStudioNewProjectAction extends AnAction {

  public void actionPerformed(final AnActionEvent e) {
    final ResolveStudioNewProjectDialog dlg = new ResolveStudioNewProjectDialog();
    dlg.show();
  }
}
