// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.jetbrains.resolve.ResTypes.*;
import com.jetbrains.resolve.psi.*;

public class ResMathQuantifiedExpImpl extends ResMathExpImpl implements ResMathQuantifiedExp {

  public ResMathQuantifiedExpImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathQuantifiedExp(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResMathExp getMathExp() {
    return findChildByClass(ResMathExp.class);
  }

  @Override
  @Nullable
  public ResMathVarDeclGroup getMathVarDeclGroup() {
    return findChildByClass(ResMathVarDeclGroup.class);
  }

  @Override
  @Nullable
  public PsiElement getComma() {
    return findChildByType(COMMA);
  }

  @Override
  @Nullable
  public PsiElement getEexists() {
    return findChildByType(EEXISTS);
  }

  @Override
  @Nullable
  public PsiElement getEforall() {
    return findChildByType(EFORALL);
  }

  @Override
  @Nullable
  public PsiElement getExists() {
    return findChildByType(EXISTS);
  }

  @Override
  @Nullable
  public PsiElement getForall() {
    return findChildByType(FORALL);
  }

}
