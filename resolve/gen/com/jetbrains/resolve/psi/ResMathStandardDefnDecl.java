// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathStandardDefnDecl extends ResMathDefnDecl {

  @Nullable
  ResMathExp getMathExp();

  @Nullable
  ResMathInfixDefnSig getMathInfixDefnSig();

  @Nullable
  ResMathOutfixDefnSig getMathOutfixDefnSig();

  @Nullable
  ResMathPostfixDefnSig getMathPostfixDefnSig();

  @Nullable
  ResMathPrefixDefnSig getMathPrefixDefnSig();

  @Nullable
  PsiElement getChainable();

  @Nullable
  PsiElement getCoercer();

  @NotNull
  PsiElement getDefinition();

  @Nullable
  PsiElement getEtriangleq();

  @Nullable
  PsiElement getImplicit();

  @Nullable
  PsiElement getSemicolon();

  @Nullable
  PsiElement getTriequals();

  @Nullable
  PsiElement getValued();

}
