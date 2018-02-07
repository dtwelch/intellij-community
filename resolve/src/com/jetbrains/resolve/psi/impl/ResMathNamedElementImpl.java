package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.jetbrains.resolve.psi.ResMathSymbolName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResMathNamedElementImpl extends ResNamedElementImpl {

  public ResMathNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }

  /**
   * Named elements in mathematical contexts can have arbirary symbols as names, not just idents. This method hooks in
   * and returns that symbol as an 'identifier' -- in accordance with normal named elements
   * (e.g. in programmatic contexts).
   */
  @Nullable
  @Override
  public PsiElement getIdentifier() {
    return findChildByClass(ResMathSymbolName.class);
  }
}