// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public interface ResRealizBlock extends ResBlock {

  @NotNull
  List<ResFacilityDecl> getFacilityDeclList();

  @NotNull
  List<ResMathStandardDefnDecl> getMathStandardDefnDeclList();

  @NotNull
  List<ResOperationProcedureDecl> getOperationProcedureDeclList();

  @NotNull
  List<ResProcedureDecl> getProcedureDeclList();

  @NotNull
  List<ResTypeReprDecl> getTypeReprDeclList();

  boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place);

}
