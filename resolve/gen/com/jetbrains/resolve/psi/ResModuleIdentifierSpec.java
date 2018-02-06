// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResModuleIdentifierSpec extends ResCompositeElement {

  @NotNull
  ResModuleIdentifier getModuleIdentifier();

  @Nullable
  ResModuleLibraryIdentifier getModuleLibraryIdentifier();

  @Nullable
  PsiElement getFrom();

  @Nullable
  ResModuleLibraryIdentifier getFromLibraryIdentifier();

  @NotNull
  String getName();

  @Nullable
  PsiElement getIdentifier();

  boolean shouldGoDeeper();

}
