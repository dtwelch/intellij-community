// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathComprehensionExp extends ResMathExp {

  @Nullable
  ResMathExp getMathExp();

  @NotNull
  ResMathVarDeclGroup getMathVarDeclGroup();

  @NotNull
  PsiElement getBar();

  @NotNull
  PsiElement getLbrace();

  @Nullable
  PsiElement getRbrace();

}
