// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResPrecisModuleDecl extends ResModuleDecl {

  @Nullable
  ResReferenceExp getReferenceExp();

  @Nullable
  ResUsesList getUsesList();

  @NotNull
  PsiElement getPrecis();

  @Nullable
  PsiElement getEnd();

  @Nullable
  PsiElement getExtends();

}
