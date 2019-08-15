// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathTermTyExp extends ResMathExp {

  @NotNull
  List<ResMathExp> getMathExpList();

  @NotNull
  PsiElement getColon();

  @NotNull
  PsiElement getLparen();

  @NotNull
  PsiElement getRparen();

  @Nullable
  PsiElement getFormula();

  @Nullable
  PsiElement getTerm();

}
