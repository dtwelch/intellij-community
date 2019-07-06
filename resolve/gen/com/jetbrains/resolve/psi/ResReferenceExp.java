// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.jetbrains.resolve.psi.impl.ResReference;

public interface ResReferenceExp extends ResExp, ResReferenceExpBase {

  @NotNull
  PsiElement getIdentifier();

  @NotNull
  ResReference getReference();

  @Nullable
  ResReferenceExp getQualifier();

  @Nullable
  PsiElement resolve();

  boolean shouldReferenceModule();

}
