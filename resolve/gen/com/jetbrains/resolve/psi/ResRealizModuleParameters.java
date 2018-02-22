// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResRealizModuleParameters extends ResModuleParameters {

  @NotNull
  List<ResParamDecl> getParamDeclList();

  @NotNull
  PsiElement getLparen();

  @Nullable
  PsiElement getRparen();

}
