package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractTypeDeclLikeNodeImpl extends ResNamedElementImpl implements ResTypeLikeNodeDecl {

  public ResAbstractTypeDeclLikeNodeImpl(@NotNull ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public PsiElement getIdentifier() {
    return findNotNullChildByType(ResTypes.IDENTIFIER);
  }

  @Nullable
  @Override
  public ResMathExp getMathMetaTypeExp() {
    if (this instanceof ResTypeModelDecl) {
      return ((ResTypeModelDecl)this).getMathExp();
    }
    else {
      ResTypeReprDecl x = (ResTypeReprDecl)this;
      ResType type = x.getType();
      if (type == null || !(type instanceof ResRecordType)) return null;
      return (ResRecordType)type;
    }
  }
}
