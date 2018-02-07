package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.resolve.psi.ResMathDefnDecl;
import com.jetbrains.resolve.psi.ResMathDefnSig;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * An abstract base class from which all RESOLVE math definition-like constructs extend. See {@link ResMathDefnDecl}
 * an explanation as to why this don't extend {@link com.jetbrains.resolve.psi.impl.ResNamedElementImpl}.
 */
public abstract class ResAbstractMathDefnImpl extends ResCompositeElementImpl implements ResMathDefnDecl {

  public ResAbstractMathDefnImpl(@NotNull ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public List<ResMathDefnSig> getSignatures() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResMathDefnSig.class);
  }
}
