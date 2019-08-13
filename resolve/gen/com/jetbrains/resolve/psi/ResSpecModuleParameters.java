// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResSpecModuleParameters extends ResModuleParameters {

  @NotNull
  List<ResMathParameterDefnDecl> getMathParameterDefnDeclList();

  @NotNull
  List<ResParamDecl> getParamDeclList();

  @NotNull
  List<ResTypeParamDecl> getTypeParamDeclList();

  @NotNull
  PsiElement getLparen();

  @Nullable
  PsiElement getRparen();

}
