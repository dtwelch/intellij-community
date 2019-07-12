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

public class ResMathLambdaExpImpl extends ResMathExpImpl implements ResMathLambdaExp {

  public ResMathLambdaExpImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathLambdaExp(this);
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
  @NotNull
  public ResMathVarDeclGroup getMathVarDeclGroup() {
    return findNotNullChildByClass(ResMathVarDeclGroup.class);
  }

  @Override
  @NotNull
  public PsiElement getComma() {
    return findNotNullChildByType(COMMA);
  }

  @Override
  @NotNull
  public PsiElement getLambda() {
    return findNotNullChildByType(LAMBDA);
  }

}
