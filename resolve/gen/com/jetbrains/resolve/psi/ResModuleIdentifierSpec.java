// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResModuleIdentifierSpec extends ResNamedElement {

  @NotNull
  ResModuleIdentifier getModuleIdentifier();

  @Nullable
  ResModuleLibraryIdentifier getModuleLibraryIdentifier();

  @Nullable
  ResWithClause getWithClause();

  @Nullable
  PsiElement getFrom();

  @Nullable
  PsiElement getIdentifier();

  @Nullable
  ResModuleLibraryIdentifier getFromLibraryIdentifier();

  boolean shouldGoDeeper();

}
