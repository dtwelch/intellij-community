package com.jetbrains.resolve.action;

import edu.clemson.resolve.core.ResolveCompilerListener;
import edu.clemson.resolve.core.ResolveMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CompilerIssueListener implements ResolveCompilerListener {
  public final List<ResolveValidateAction.Issue> issues = new ArrayList<>();

  @Override
  public void reportStatus(@NotNull String s) {
  }

  @Override
  public void resetStatus() {
  }

  @Override
  public void reportError(@NotNull ResolveMessage message) {
    issues.add(new ResolveValidateAction.Issue(message));
  }

  @Override
  public void reportWarning(@NotNull ResolveMessage message) {
    issues.add(new ResolveValidateAction.Issue(message));
  }
}