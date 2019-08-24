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

  @NotNull
  List<ResMathMetaProperty> getMathMetaPropertyList();

  @Nullable
  ResMathOutfixDefnSig getMathOutfixDefnSig();

  @Nullable
  ResMathPrefixDefnSig getMathPrefixDefnSig();

  @Nullable
  PsiElement getCoercer();

  @NotNull
  PsiElement getDefinition();

  @Nullable
  PsiElement getImplicit();

  @Nullable
  PsiElement getIs();

  @Nullable
  PsiElement getLiteral();

  @Nullable
  PsiElement getLparen();

  @Nullable
  PsiElement getRparen();

  @Nullable
  PsiElement getSemicolon();

  @Nullable
  PsiElement getTriangleq();

  @Nullable
  PsiElement getValued();

}
