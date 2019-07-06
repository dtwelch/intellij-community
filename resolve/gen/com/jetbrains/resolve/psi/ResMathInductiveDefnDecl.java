// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResMathInductiveDefnDecl extends ResMathDefnDecl {

  @NotNull
  List<ResMathExp> getMathExpList();

  @Nullable
  ResMathInfixDefnSig getMathInfixDefnSig();

  @Nullable
  ResMathOutfixDefnSig getMathOutfixDefnSig();

  @Nullable
  ResMathPrefixDefnSig getMathPrefixDefnSig();

  @NotNull
  PsiElement getDefinition();

  @NotNull
  PsiElement getInductive();

  @Nullable
  PsiElement getIndBase();

  @Nullable
  PsiElement getIndHypo();

  @Nullable
  PsiElement getIs();

}
