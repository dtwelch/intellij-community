package com.jetbrains.resolve;

import com.intellij.ide.BrowserUtil;
import com.intellij.ide.actions.SendFeedbackAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ex.ApplicationInfoEx;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class ReportProblemAction extends AnAction implements DumbAware {
  @Override
  public void actionPerformed(AnActionEvent e) {
    launchBrowser(e.getProject());
  }

  public static void launchBrowser(@Nullable Project project) {
    final ApplicationInfoEx appInfo = ApplicationInfoEx.getInstanceEx();
    boolean eap = appInfo.isEAP();
    String urlTemplate = appInfo.getEAPFeedbackUrl();
    urlTemplate = urlTemplate
      .replace("$BUILD", eap ? appInfo.getBuild().asStringWithoutProductCode() : appInfo.getBuild().asString())
      .replace("$TIMEZONE", System.getProperty("user.timezone"))
      .replace("$DESCR", SendFeedbackAction.getDescription());
    BrowserUtil.browse(urlTemplate, project);
  }

  @Override
  public void update(AnActionEvent e) {
    e.getPresentation().setEnabled(ApplicationInfoEx.getInstanceEx() != null);
  }

}
