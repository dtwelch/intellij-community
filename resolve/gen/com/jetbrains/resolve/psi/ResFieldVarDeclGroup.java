// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResFieldVarDeclGroup extends ResCompositeElement {

  @NotNull
  List<ResFieldDef> getFieldDefList();

  @Nullable
  ResType getType();

  @Nullable
  PsiElement getColon();

}
