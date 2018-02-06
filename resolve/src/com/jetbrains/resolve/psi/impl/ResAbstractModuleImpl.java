package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.psi.ResModuleDecl;
import com.jetbrains.resolve.psi.ResModuleIdentifierSpec;
import com.jetbrains.resolve.psi.ResReferenceExp;
import com.jetbrains.resolve.psi.ResUsesList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ResAbstractModuleImpl extends ResNamedElementImpl implements ResModuleDecl {

  ResAbstractModuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Nullable
  @Override
  public PsiElement getNameIdentifier() {
    return null;
  }

  @NotNull
  @Override
  public List<ResModuleIdentifierSpec> getModuleIdentifierSpecs() {
    return getUsesList() != null ? getUsesList().getModuleIdentifierSpecList() : ContainerUtil.newArrayList();
  }

  @NotNull
  public List<ResReferenceExp> getModuleHeaderReferences() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResReferenceExp.class);
  }

  @Nullable
  public ResUsesList getUsesList() {
    return PsiTreeUtil.findChildOfType(this, ResUsesList.class);
  }

}
