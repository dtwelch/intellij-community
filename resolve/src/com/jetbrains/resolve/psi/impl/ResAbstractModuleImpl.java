package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.jetbrains.resolve.psi.ResModuleDecl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractModuleImpl extends ResNamedElementImpl implements ResModuleDecl {

  ResAbstractModuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Nullable
  @Override
  public PsiElement getNameIdentifier() {
    return null;
  }
}
