package com.jetbrains.resolve.action;

import edu.clemson.resolve.Resolve;
import edu.clemson.resolve.ResolveMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CompilerIssueListener implements Resolve.ResolveListener {
  public final List<ResolveValidateAction.Issue> issues = new ArrayList<>();

  @Override
  public void info(@NotNull String s) {
  }

  @Override
  public void error(@NotNull ResolveMessage resolveMessage) {
    issues.add(new ResolveValidateAction.Issue(resolveMessage));
  }

  @Override
  public void warning(@NotNull ResolveMessage resolveMessage) {
    issues.add(new ResolveValidateAction.Issue(resolveMessage));
  }
}