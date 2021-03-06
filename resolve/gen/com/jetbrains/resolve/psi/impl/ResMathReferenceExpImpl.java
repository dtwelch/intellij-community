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

public class ResMathReferenceExpImpl extends ResMathExpImpl implements ResMathReferenceExp {

  public ResMathReferenceExpImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathReferenceExp(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public ResMathSymbolName getMathSymbolName() {
    return findNotNullChildByClass(ResMathSymbolName.class);
  }

  @NotNull
  public ResMathVarLikeReference getReference() {
    return ResPsiImplUtil.getReference(this);
  }

  @Nullable
  public ResMathReferenceExp getQualifier() {
    return ResPsiImplUtil.getQualifier(this);
  }

  @NotNull
  public PsiElement getIdentifier() {
    return ResPsiImplUtil.getIdentifier(this);
  }

}
