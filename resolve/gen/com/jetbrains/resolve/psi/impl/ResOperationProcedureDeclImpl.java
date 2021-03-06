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

public class ResOperationProcedureDeclImpl extends ResAbstractOperationLikeNode implements ResOperationProcedureDecl {

  public ResOperationProcedureDeclImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitOperationProcedureDecl(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResCloseIdentifier getCloseIdentifier() {
    return findChildByClass(ResCloseIdentifier.class);
  }

  @Override
  @Nullable
  public ResEnsuresClause getEnsuresClause() {
    return findChildByClass(ResEnsuresClause.class);
  }

  @Override
  @Nullable
  public ResOpBlock getOpBlock() {
    return findChildByClass(ResOpBlock.class);
  }

  @Override
  @NotNull
  public List<ResParamDecl> getParamDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResParamDecl.class);
  }

  @Override
  @Nullable
  public ResRequiresClause getRequiresClause() {
    return findChildByClass(ResRequiresClause.class);
  }

  @Override
  @Nullable
  public ResType getType() {
    return findChildByClass(ResType.class);
  }

  @Override
  @Nullable
  public PsiElement getColon() {
    return findChildByType(COLON);
  }

  @Override
  @Nullable
  public PsiElement getLparen() {
    return findChildByType(LPAREN);
  }

  @Override
  @NotNull
  public PsiElement getOperation() {
    return findNotNullChildByType(OPERATION);
  }

  @Override
  @Nullable
  public PsiElement getProcedure() {
    return findChildByType(PROCEDURE);
  }

  @Override
  @Nullable
  public PsiElement getRecursive() {
    return findChildByType(RECURSIVE);
  }

  @Override
  @Nullable
  public PsiElement getRparen() {
    return findChildByType(RPAREN);
  }

  @Override
  @Nullable
  public PsiElement getEnd() {
    return findChildByType(END);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}
