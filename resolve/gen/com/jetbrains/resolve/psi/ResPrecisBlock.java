// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public interface ResPrecisBlock extends ResBlock {

  @NotNull
  List<ResMathCategoricalDefnDecl> getMathCategoricalDefnDeclList();

  @NotNull
  List<ResMathInductiveDefnDecl> getMathInductiveDefnDeclList();

  @NotNull
  List<ResMathRecognitionDecl> getMathRecognitionDeclList();

  @NotNull
  List<ResMathStandardDefnDecl> getMathStandardDefnDeclList();

  @NotNull
  List<ResMathTheoremDecl> getMathTheoremDeclList();

  boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place);

}
