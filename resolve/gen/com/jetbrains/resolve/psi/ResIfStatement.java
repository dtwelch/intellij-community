// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResIfStatement extends ResStatement {

  @Nullable
  ResElseStatement getElseStatement();

  @Nullable
  ResExp getExp();

  @NotNull
  List<ResStatement> getStatementList();

  @NotNull
  PsiElement getIfProg();

  @Nullable
  PsiElement getSemicolon();

  @Nullable
  PsiElement getEnd();

  @Nullable
  PsiElement getThen();

}
