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

public class ResTypeModelDeclImpl extends ResAbstractTypeDeclLikeNodeImpl implements ResTypeModelDecl {

  public ResTypeModelDeclImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitTypeModelDecl(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResConstraintsClause getConstraintsClause() {
    return findChildByClass(ResConstraintsClause.class);
  }

  @Override
  @Nullable
  public ResExemplarDecl getExemplarDecl() {
    return findChildByClass(ResExemplarDecl.class);
  }

  @Override
  @Nullable
  public ResIntializationClause getIntializationClause() {
    return findChildByClass(ResIntializationClause.class);
  }

  @Override
  @Nullable
  public ResMathExp getMathExp() {
    return findChildByClass(ResMathExp.class);
  }

  @Override
  @Nullable
  public PsiElement getSemicolon() {
    return findChildByType(SEMICOLON);
  }

  @Override
  @NotNull
  public PsiElement getTypeFamily() {
    return findNotNullChildByType(TYPE_FAMILY);
  }

  @Override
  @Nullable
  public PsiElement getBy() {
    return findChildByType(BY);
  }

  @Override
  @NotNull
  public PsiElement getFamily() {
    return findNotNullChildByType(FAMILY);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

  @Override
  @Nullable
  public PsiElement getIs() {
    return findChildByType(IS);
  }

  @Override
  @Nullable
  public PsiElement getModeled() {
    return findChildByType(MODELED);
  }

}
