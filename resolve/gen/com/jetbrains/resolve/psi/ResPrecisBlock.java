// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

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

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(ResPrecisBlock, ...)
  //methods are not found in ResPsiImplUtil

}
