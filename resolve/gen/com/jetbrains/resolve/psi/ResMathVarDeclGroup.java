// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathVarDeclGroup extends ResCompositeElement {

  @Nullable
  ResMathExp getMathExp();

  @NotNull
  List<ResMathVarDef> getMathVarDefList();

  @Nullable
  PsiElement getColon();

  @Nullable
  PsiElement getEtricolon();

  @Nullable
  PsiElement getTricolon();

}
