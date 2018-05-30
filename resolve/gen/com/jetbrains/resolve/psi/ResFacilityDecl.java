// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface ResFacilityDecl extends ResNamedElement {

  @NotNull
  List<ResExtensionPairing> getExtensionPairingList();

  @NotNull
  List<ResModuleIdentifier> getModuleIdentifierList();

  @NotNull
  PsiElement getFacility();

  @Nullable
  PsiElement getSemicolon();

  @Nullable
  PsiElement getBy();

  @Nullable
  PsiElement getExternally();

  @NotNull
  PsiElement getIdentifier();

  @NotNull
  PsiElement getIs();

  @Nullable
  PsiElement getRealized();

  @Nullable
  ResFile resolveSpecification();

  @Nullable
  ResFile resolveImplementation();

}
