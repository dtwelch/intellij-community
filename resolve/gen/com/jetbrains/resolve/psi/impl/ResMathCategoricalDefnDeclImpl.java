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

public class ResMathCategoricalDefnDeclImpl extends ResAbstractMathDefnImpl implements ResMathCategoricalDefnDecl {

  public ResMathCategoricalDefnDeclImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathCategoricalDefnDecl(this);
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
  public List<ResMathInfixDefnSig> getMathInfixDefnSigList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathInfixDefnSig.class);
  }

  @Override
  @NotNull
  public List<ResMathOutfixDefnSig> getMathOutfixDefnSigList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathOutfixDefnSig.class);
  }

  @Override
  @NotNull
  public List<ResMathPrefixDefnSig> getMathPrefixDefnSigList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathPrefixDefnSig.class);
  }

  @Override
  @NotNull
  public PsiElement getCategorical() {
    return findNotNullChildByType(CATEGORICAL);
  }

  @Override
  @NotNull
  public PsiElement getDefinition() {
    return findNotNullChildByType(DEFINITION);
  }

  @Override
  @Nullable
  public PsiElement getSemicolon() {
    return findChildByType(SEMICOLON);
  }

  @Override
  @Nullable
  public PsiElement getFor() {
    return findChildByType(FOR);
  }

  @Override
  @Nullable
  public PsiElement getIs() {
    return findChildByType(IS);
  }

}
