// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class ResVisitor extends PsiElementVisitor {

  public void visitArgumentList(@NotNull ResArgumentList o) {
    visitCompositeElement(o);
  }

  public void visitBinaryExp(@NotNull ResBinaryExp o) {
    visitExp(o);
  }

  public void visitExp(@NotNull ResExp o) {
    visitTypeOwner(o);
  }

  public void visitInfixExp(@NotNull ResInfixExp o) {
    visitBinaryExp(o);
  }

  public void visitLiteralExp(@NotNull ResLiteralExp o) {
    visitExp(o);
  }

  public void visitModuleIdentifier(@NotNull ResModuleIdentifier o) {
    visitCompositeElement(o);
  }

  public void visitModuleIdentifierSpec(@NotNull ResModuleIdentifierSpec o) {
    visitCompositeElement(o);
  }

  public void visitModuleLibraryIdentifier(@NotNull ResModuleLibraryIdentifier o) {
    visitCompositeElement(o);
  }

  public void visitNestedExp(@NotNull ResNestedExp o) {
    visitExp(o);
  }

  public void visitParamExp(@NotNull ResParamExp o) {
    visitExp(o);
  }

  public void visitPrecisModuleDecl(@NotNull ResPrecisModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitProgSymbolName(@NotNull ResProgSymbolName o) {
    visitCompositeElement(o);
  }

  public void visitReferenceExp(@NotNull ResReferenceExp o) {
    visitExp(o);
    // visitReferenceExpBase(o);
  }

  public void visitSelectorExp(@NotNull ResSelectorExp o) {
    visitBinaryExp(o);
  }

  public void visitUsesList(@NotNull ResUsesList o) {
    visitCompositeElement(o);
  }

  public void visitModuleDecl(@NotNull ResModuleDecl o) {
    visitCompositeElement(o);
  }

  public void visitTypeOwner(@NotNull ResTypeOwner o) {
    visitCompositeElement(o);
  }

  public void visitCompositeElement(@NotNull ResCompositeElement o) {
    visitElement(o);
  }

}
