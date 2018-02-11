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

  public void visitEntailsClause(@NotNull ResEntailsClause o) {
    visitCompositeElement(o);
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

  public void visitMathAlternativeExp(@NotNull ResMathAlternativeExp o) {
    visitMathExp(o);
  }

  public void visitMathAlternativeItemExp(@NotNull ResMathAlternativeItemExp o) {
    visitMathExp(o);
  }

  public void visitMathAlternativesList(@NotNull ResMathAlternativesList o) {
    visitCompositeElement(o);
  }

  public void visitMathAssertionExp(@NotNull ResMathAssertionExp o) {
    visitMathExp(o);
  }

  public void visitMathBigUnionExp(@NotNull ResMathBigUnionExp o) {
    visitMathExp(o);
  }

  public void visitMathCartProdExp(@NotNull ResMathCartProdExp o) {
    visitMathExp(o);
  }

  public void visitMathClssftnAssrtExp(@NotNull ResMathClssftnAssrtExp o) {
    visitMathExp(o);
  }

  public void visitMathExp(@NotNull ResMathExp o) {
    visitCompositeElement(o);
  }

  public void visitMathFunctionAppList(@NotNull ResMathFunctionAppList o) {
    visitCompositeElement(o);
  }

  public void visitMathIncomingExp(@NotNull ResMathIncomingExp o) {
    visitMathExp(o);
  }

  public void visitMathInfixApplyExp(@NotNull ResMathInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathLambdaExp(@NotNull ResMathLambdaExp o) {
    visitMathExp(o);
  }

  public void visitMathMixfixApplyExp(@NotNull ResMathMixfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathNestedExp(@NotNull ResMathNestedExp o) {
    visitMathExp(o);
  }

  public void visitMathOutfixApplyExp(@NotNull ResMathOutfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathPrefixApplyExp(@NotNull ResMathPrefixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathPrefixDefnSig(@NotNull ResMathPrefixDefnSig o) {
    visitMathDefnSig(o);
  }

  public void visitMathQuantifiedExp(@NotNull ResMathQuantifiedExp o) {
    visitMathExp(o);
  }

  public void visitMathReferenceExp(@NotNull ResMathReferenceExp o) {
    visitMathExp(o);
    // visitReferenceExpBase(o);
  }

  public void visitMathSelectorExp(@NotNull ResMathSelectorExp o) {
    visitMathExp(o);
  }

  public void visitMathStandardDefnDecl(@NotNull ResMathStandardDefnDecl o) {
    visitMathDefnDecl(o);
  }

  public void visitMathSymbolName(@NotNull ResMathSymbolName o) {
    visitCompositeElement(o);
  }

  public void visitMathVarDecl(@NotNull ResMathVarDecl o) {
    visitMathVarDeclGroup(o);
  }

  public void visitMathVarDeclGroup(@NotNull ResMathVarDeclGroup o) {
    visitCompositeElement(o);
  }

  public void visitMathVarDef(@NotNull ResMathVarDef o) {
    visitNamedElement(o);
  }

  public void visitModuleIdentifier(@NotNull ResModuleIdentifier o) {
    visitCompositeElement(o);
  }

  public void visitModuleIdentifierSpec(@NotNull ResModuleIdentifierSpec o) {
    visitNamedElement(o);
  }

  public void visitModuleLibraryIdentifier(@NotNull ResModuleLibraryIdentifier o) {
    visitCompositeElement(o);
  }

  public void visitNestedExp(@NotNull ResNestedExp o) {
    visitExp(o);
  }

  public void visitParamDecl(@NotNull ResParamDecl o) {
    visitCompositeElement(o);
  }

  public void visitParamDef(@NotNull ResParamDef o) {
    visitNamedElement(o);
  }

  public void visitParamExp(@NotNull ResParamExp o) {
    visitExp(o);
  }

  public void visitParameterMode(@NotNull ResParameterMode o) {
    visitCompositeElement(o);
  }

  public void visitPrecisBlock(@NotNull ResPrecisBlock o) {
    visitBlock(o);
  }

  public void visitPrecisModuleDecl(@NotNull ResPrecisModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitProgSymbolName(@NotNull ResProgSymbolName o) {
    visitCompositeElement(o);
  }

  public void visitRealizBlock(@NotNull ResRealizBlock o) {
    visitBlock(o);
  }

  public void visitRealizModuleParameters(@NotNull ResRealizModuleParameters o) {
    visitModuleParameters(o);
  }

  public void visitRealizationModuleDecl(@NotNull ResRealizationModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitReferenceExp(@NotNull ResReferenceExp o) {
    visitExp(o);
    // visitReferenceExpBase(o);
  }

  public void visitRequiresClause(@NotNull ResRequiresClause o) {
    visitCompositeElement(o);
  }

  public void visitSelectorExp(@NotNull ResSelectorExp o) {
    visitBinaryExp(o);
  }

  public void visitTypeParamDecl(@NotNull ResTypeParamDecl o) {
    visitNamedElement(o);
  }

  public void visitUsesList(@NotNull ResUsesList o) {
    visitCompositeElement(o);
  }

  public void visitBlock(@NotNull ResBlock o) {
    visitCompositeElement(o);
  }

  public void visitMathDefnDecl(@NotNull ResMathDefnDecl o) {
    visitCompositeElement(o);
  }

  public void visitMathDefnSig(@NotNull ResMathDefnSig o) {
    visitCompositeElement(o);
  }

  public void visitModuleDecl(@NotNull ResModuleDecl o) {
    visitCompositeElement(o);
  }

  public void visitModuleParameters(@NotNull ResModuleParameters o) {
    visitCompositeElement(o);
  }

  public void visitNamedElement(@NotNull ResNamedElement o) {
    visitCompositeElement(o);
  }

  public void visitTypeOwner(@NotNull ResTypeOwner o) {
    visitCompositeElement(o);
  }

  public void visitCompositeElement(@NotNull ResCompositeElement o) {
    visitElement(o);
  }

}
