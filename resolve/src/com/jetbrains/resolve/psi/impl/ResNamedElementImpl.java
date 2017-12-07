package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.resolve.psi.ResCompositeElement;
import com.jetbrains.resolve.psi.ResNamedElement;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public abstract class ResNamedElementImpl extends ResCompositeElementImpl
    implements ResCompositeElement, ResNamedElement {

  public ResNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public PsiElement setName(@NonNls @NotNull String newName) throws IncorrectOperationException {
    return this;
  }
}
