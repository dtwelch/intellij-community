// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathAlternativeExp extends ResMathExp {

  @NotNull
  ResMathAlternativesList getMathAlternativesList();

  @NotNull
  ResMathExp getMathExp();

  @NotNull
  PsiElement getPiecewise();

  @NotNull
  PsiElement getOtherwise();

}
