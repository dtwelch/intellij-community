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

public class ResIfStatementImpl extends ResStatementImpl implements ResIfStatement {

  public ResIfStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitIfStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public ResElseStatement getElseStatement() {
    return findChildByClass(ResElseStatement.class);
  }

  @Override
  @Nullable
  public ResExp getExp() {
    return findChildByClass(ResExp.class);
  }

  @Override
  @NotNull
  public List<ResStatement> getStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResStatement.class);
  }

  @Override
  @NotNull
  public PsiElement getIfProg() {
    return findNotNullChildByType(IF_PROG);
  }

  @Override
  @Nullable
  public PsiElement getSemicolon() {
    return findChildByType(SEMICOLON);
  }

  @Override
  @Nullable
  public PsiElement getEnd() {
    return findChildByType(END);
  }

  @Override
  @Nullable
  public PsiElement getThen() {
    return findChildByType(THEN);
  }

}
