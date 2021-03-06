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
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public class ResFacilityBlockImpl extends ResCompositeElementImpl implements ResFacilityBlock {

  public ResFacilityBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitFacilityBlock(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ResFacilityDecl> getFacilityDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResFacilityDecl.class);
  }

  @Override
  @NotNull
  public List<ResMathStandardDefnDecl> getMathStandardDefnDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathStandardDefnDecl.class);
  }

  @Override
  @NotNull
  public List<ResOperationProcedureDecl> getOperationProcedureDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResOperationProcedureDecl.class);
  }

  @Override
  @NotNull
  public List<ResTypeReprDecl> getTypeReprDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResTypeReprDecl.class);
  }

  public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
    return ResPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
  }

}
