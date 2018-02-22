// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResRealizationModuleDecl extends ResModuleDecl {

  @NotNull
  List<ResModuleIdentifierSpec> getModuleIdentifierSpecList();

  @Nullable
  ResRealizBlock getRealizBlock();

  @Nullable
  ResRealizModuleParameters getRealizModuleParameters();

  @Nullable
  ResRequiresClause getRequiresClause();

  @Nullable
  ResUsesList getUsesList();

  @NotNull
  PsiElement getRealization();

  @Nullable
  PsiElement getEnd();

  @Nullable
  PsiElement getFor();

  @Nullable
  PsiElement getOf();

}
