// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathFunctionAppList extends ResCompositeElement {

  @NotNull
  List<ResMathExp> getMathExpList();

  @NotNull
  PsiElement getLparen();

  @Nullable
  PsiElement getRparen();

}
