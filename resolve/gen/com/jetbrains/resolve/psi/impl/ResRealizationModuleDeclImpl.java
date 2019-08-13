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

public class ResRealizationModuleDeclImpl extends ResAbstractModuleImpl implements ResRealizationModuleDecl {

  public ResRealizationModuleDeclImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitRealizationModuleDecl(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ResVisitor) accept((ResVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<ResModuleIdentifierSpec> getModuleIdentifierSpecList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResModuleIdentifierSpec.class);
  }

  @Override
  @Nullable
  public ResRealizBlock getRealizBlock() {
    return findChildByClass(ResRealizBlock.class);
  }

  @Override
  @Nullable
  public ResRealizModuleParameters getRealizModuleParameters() {
    return findChildByClass(ResRealizModuleParameters.class);
  }

  @Override
  @Nullable
  public ResRequiresClause getRequiresClause() {
    return findChildByClass(ResRequiresClause.class);
  }

  @Override
  @Nullable
  public ResUsesList getUsesList() {
    return findChildByClass(ResUsesList.class);
  }

  @Override
  @NotNull
  public PsiElement getRealization() {
    return findNotNullChildByType(REALIZATION);
  }

  @Override
  @Nullable
  public PsiElement getEnd() {
    return findChildByType(END);
  }

  @Override
  @Nullable
  public PsiElement getFor() {
    return findChildByType(FOR);
  }

  @Override
  @Nullable
  public PsiElement getOf() {
    return findChildByType(OF);
  }

}
