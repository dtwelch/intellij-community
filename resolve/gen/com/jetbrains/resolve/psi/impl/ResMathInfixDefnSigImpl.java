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

public class ResMathInfixDefnSigImpl extends ResAbstractMathSigImpl implements ResMathInfixDefnSig {

  public ResMathInfixDefnSigImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathInfixDefnSig(this);
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
  public ResMathSymbolName getMathSymbolName() {
    return findNotNullChildByClass(ResMathSymbolName.class);
  }

  @Override
  @NotNull
  public List<ResMathVarDecl> getMathVarDeclList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathVarDecl.class);
  }

  @Override
  @Nullable
  public PsiElement getColon() {
    return findChildByType(COLON);
  }

  @Override
  @Nullable
  public PsiElement getEtricolon() {
    return findChildByType(ETRICOLON);
  }

  @Override
  @Nullable
  public PsiElement getTricolon() {
    return findChildByType(TRICOLON);
  }

}
