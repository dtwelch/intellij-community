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

public class ResSpecModuleParametersImpl extends ResCompositeElementImpl implements ResSpecModuleParameters {

  public ResSpecModuleParametersImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitSpecModuleParameters(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ResMathParameterDefnDecl> getMathParameterDefnDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathParameterDefnDecl.class);
  }

  @Override
  @NotNull
  public List<ResParamDecl> getParamDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResParamDecl.class);
  }

  @Override
  @NotNull
  public List<ResTypeParamDecl> getTypeParamDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResTypeParamDecl.class);
  }

  @Override
  @NotNull
  public PsiElement getLparen() {
    return findNotNullChildByType(LPAREN);
  }

  @Override
  @Nullable
  public PsiElement getRparen() {
    return findChildByType(RPAREN);
  }

}
