// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public interface ResConceptBlock extends ResBlock {

  @NotNull
  List<ResConstraintsClause> getConstraintsClauseList();

  @NotNull
  List<ResMathStandardDefnDecl> getMathStandardDefnDeclList();

  @NotNull
  List<ResOperationDecl> getOperationDeclList();

  @NotNull
  List<ResTypeModelDecl> getTypeModelDeclList();

  boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place);

}
