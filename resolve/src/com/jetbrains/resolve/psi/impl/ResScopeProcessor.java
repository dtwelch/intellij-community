package com.jetbrains.resolve.psi.impl;

import com.intellij.psi.scope.BaseScopeProcessor;

public abstract class ResScopeProcessor extends BaseScopeProcessor {
  public boolean isCompletion() {
    return false;
  }
}
