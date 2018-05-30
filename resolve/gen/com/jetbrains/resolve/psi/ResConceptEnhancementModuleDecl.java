// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResConceptEnhancementModuleDecl extends ResModuleDecl {

  @Nullable
  ResConceptBlock getConceptBlock();

  @Nullable
  ResModuleIdentifierSpec getModuleIdentifierSpec();

  @Nullable
  ResRequiresClause getRequiresClause();

  @Nullable
  ResSpecModuleParameters getSpecModuleParameters();

  @Nullable
  ResUsesList getUsesList();

  @NotNull
  PsiElement getEnhancement();

  @Nullable
  PsiElement getEnd();

  @Nullable
  PsiElement getFor();

}
