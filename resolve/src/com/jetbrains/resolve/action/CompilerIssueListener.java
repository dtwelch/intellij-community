package com.jetbrains.resolve.action;

import edu.clemson.resolve.Resolve;
import edu.clemson.resolve.ResolveMessage;

import java.util.ArrayList;
import java.util.List;

public class CompilerIssueListener implements Resolve.ResolveListener {
  public final List<ResolveValidateAction.Issue> issues = new ArrayList<>();

  @Override
  public void info(String s) {
  }

  @Override
  public void error(ResolveMessage resolveMessage) {
    issues.add(new ResolveValidateAction.Issue(resolveMessage));
  }

  @Override
  public void warning(ResolveMessage resolveMessage) {
    issues.add(new ResolveValidateAction.Issue(resolveMessage));
  }
}