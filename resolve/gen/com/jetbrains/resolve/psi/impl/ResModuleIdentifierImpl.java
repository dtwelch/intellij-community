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
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiReference;

public class ResModuleIdentifierImpl extends ResCompositeElementImpl implements ResModuleIdentifier {

  public ResModuleIdentifierImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitModuleIdentifier(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

  @NotNull
  public PsiReference[] getReferences() {
    return ResPsiImplUtil.getReferences(this);
  }

  @NotNull
  public TextRange getModuleIdentiferTextRange() {
    return ResPsiImplUtil.getModuleIdentiferTextRange(this);
  }

  @Nullable
  public PsiElement resolve() {
    return ResPsiImplUtil.resolve(this);
  }

}
