// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;

public interface ResExemplarDecl extends ResNamedElement {

  @Nullable
  PsiElement getSemicolon();

  @NotNull
  PsiElement getExemplar();

  @NotNull
  PsiElement getIdentifier();

  @Nullable
  ResMathExp getResMathMetaTypeExpInner(@Nullable ResolveState context);

}
