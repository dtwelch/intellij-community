// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResOperationProcedureDecl extends ResOperationLikeNode {

  @Nullable
  ResCloseIdentifier getCloseIdentifier();

  @Nullable
  ResEnsuresClause getEnsuresClause();

  @Nullable
  ResOpBlock getOpBlock();

  @NotNull
  List<ResParamDecl> getParamDeclList();

  @Nullable
  ResRequiresClause getRequiresClause();

  @Nullable
  ResType getType();

  @Nullable
  PsiElement getColon();

  @Nullable
  PsiElement getLparen();

  @NotNull
  PsiElement getOperation();

  @Nullable
  PsiElement getProcedure();

  @Nullable
  PsiElement getRecursive();

  @Nullable
  PsiElement getRparen();

  @Nullable
  PsiElement getEnd();

  @NotNull
  PsiElement getIdentifier();

}
