// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathCategoricalDefnDecl extends ResMathDefnDecl {

  @Nullable
  ResMathExp getMathExp();

  @NotNull
  List<ResMathInfixDefnSig> getMathInfixDefnSigList();

  @NotNull
  List<ResMathOutfixDefnSig> getMathOutfixDefnSigList();

  @NotNull
  List<ResMathPrefixDefnSig> getMathPrefixDefnSigList();

  @NotNull
  PsiElement getCategorical();

  @NotNull
  PsiElement getDefinition();

  @Nullable
  PsiElement getSemicolon();

  @Nullable
  PsiElement getFor();

  @Nullable
  PsiElement getIs();

}
