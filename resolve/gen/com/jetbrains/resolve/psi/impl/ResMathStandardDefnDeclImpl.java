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

public class ResMathStandardDefnDeclImpl extends ResAbstractMathDefnImpl implements ResMathStandardDefnDecl {

  public ResMathStandardDefnDeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ResVisitor visitor) {
    visitor.visitMathStandardDefnDecl(this);
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
  @Nullable
  public ResMathPrefixDefnSig getMathPrefixDefnSig() {
    return findChildByClass(ResMathPrefixDefnSig.class);
  }

  @Override
  @Nullable
  public PsiElement getChainable() {
    return findChildByType(CHAINABLE);
  }

  @Override
  @NotNull
  public PsiElement getDefinition() {
    return findNotNullChildByType(DEFINITION);
  }

  @Override
  @Nullable
  public PsiElement getImplicit() {
    return findChildByType(IMPLICIT);
  }

  @Override
  @Nullable
  public PsiElement getSemicolon() {
    return findChildByType(SEMICOLON);
  }

  @Override
  @Nullable
  public PsiElement getTriEquals() {
    return findChildByType(TRI_EQUALS);
  }

  @Override
  @Nullable
  public PsiElement getValued() {
    return findChildByType(VALUED);
  }

}
