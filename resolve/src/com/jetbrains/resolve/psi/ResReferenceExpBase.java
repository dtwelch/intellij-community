package com.jetbrains.resolve.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResReferenceExpBase extends ResCompositeElement {

  @NotNull
  PsiElement getIdentifier();

  @Nullable
  ResReferenceExpBase getQualifier();
}
