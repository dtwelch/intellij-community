// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class ResVisitor extends PsiElementVisitor {

  public void visitFacilityModuleDecl(@NotNull ResFacilityModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitPrecisModuleDecl(@NotNull ResPrecisModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitModuleDecl(@NotNull ResModuleDecl o) {
    visitCompositeElement(o);
  }

  public void visitCompositeElement(@NotNull ResCompositeElement o) {
    visitElement(o);
  }

}
