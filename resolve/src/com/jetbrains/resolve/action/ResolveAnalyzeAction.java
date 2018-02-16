package com.jetbrains.resolve.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Key;
import edu.clemson.resolve.ResolveMessage;

public class ResolveAnalyzeAction extends AnAction {

  public static final Logger LOG = Logger.getInstance("RESOLVEAnalyzeAction");
  public static final Key<Issue> ISSUE_ANNOTATION = Key.create("ISSUE_ANNOTATION");

  @Override
  public void actionPerformed(AnActionEvent e) {

  }

  public static class Issue {
    String annotation;
    ResolveMessage msg;
    public Issue(ResolveMessage msg) { this.msg = msg; }
  }
}
