// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResRequiresClause extends ResCompositeElement {

  @Nullable
  ResEntailsClause getEntailsClause();

  @Nullable
  ResMathExp getMathExp();

  @Nullable
  PsiElement getSemicolon();

  @NotNull
  PsiElement getRequires();

}
