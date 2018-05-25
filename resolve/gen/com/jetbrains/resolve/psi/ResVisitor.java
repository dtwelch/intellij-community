// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class ResVisitor extends PsiElementVisitor {

  public void visitArgumentList(@NotNull ResArgumentList o) {
    visitCompositeElement(o);
  }

  public void visitAssignStatement(@NotNull ResAssignStatement o) {
    visitCompositeElement(o);
  }

  public void visitBinaryExp(@NotNull ResBinaryExp o) {
    visitExp(o);
  }

  public void visitChangingClause(@NotNull ResChangingClause o) {
    visitCompositeElement(o);
  }

  public void visitCloseIdentifier(@NotNull ResCloseIdentifier o) {
    visitCompositeElement(o);
  }

  public void visitConceptBlock(@NotNull ResConceptBlock o) {
    visitBlock(o);
  }

  public void visitConceptModuleDecl(@NotNull ResConceptModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitConstraintsClause(@NotNull ResConstraintsClause o) {
    visitCompositeElement(o);
  }

  public void visitDecreasingClause(@NotNull ResDecreasingClause o) {
    visitCompositeElement(o);
  }

  public void visitElseStatement(@NotNull ResElseStatement o) {
    visitCompositeElement(o);
  }

  public void visitEnsuresClause(@NotNull ResEnsuresClause o) {
    visitCompositeElement(o);
  }

  public void visitEntailsClause(@NotNull ResEntailsClause o) {
    visitCompositeElement(o);
  }

  public void visitExemplarDecl(@NotNull ResExemplarDecl o) {
    visitNamedElement(o);
  }

  public void visitExp(@NotNull ResExp o) {
    visitTypeOwner(o);
  }

  public void visitIfStatement(@NotNull ResIfStatement o) {
    visitCompositeElement(o);
  }

  public void visitInfixExp(@NotNull ResInfixExp o) {
    visitBinaryExp(o);
  }

  public void visitIntializationClause(@NotNull ResIntializationClause o) {
    visitCompositeElement(o);
  }

  public void visitLiteralExp(@NotNull ResLiteralExp o) {
    visitExp(o);
  }

  public void visitMaintainingClause(@NotNull ResMaintainingClause o) {
    visitCompositeElement(o);
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

  public void visitMathCategoricalDefnDecl(@NotNull ResMathCategoricalDefnDecl o) {
    visitMathDefnDecl(o);
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

  public void visitMathInductiveDefnDecl(@NotNull ResMathInductiveDefnDecl o) {
    visitMathDefnDecl(o);
  }

  public void visitMathInfixApplyExp(@NotNull ResMathInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathInfixDefnSig(@NotNull ResMathInfixDefnSig o) {
    visitMathDefnSig(o);
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

  public void visitMathOutfixDefnSig(@NotNull ResMathOutfixDefnSig o) {
    visitMathDefnSig(o);
  }

  public void visitMathPostfixDefnSig(@NotNull ResMathPostfixDefnSig o) {
    visitMathDefnSig(o);
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

  public void visitMathRecognitionDecl(@NotNull ResMathRecognitionDecl o) {
    visitCompositeElement(o);
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

  public void visitMathTheoremDecl(@NotNull ResMathTheoremDecl o) {
    visitNamedElement(o);
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

  public void visitNoticeStatement(@NotNull ResNoticeStatement o) {
    visitCompositeElement(o);
  }

  public void visitOpBlock(@NotNull ResOpBlock o) {
    visitBlock(o);
  }

  public void visitOperationDecl(@NotNull ResOperationDecl o) {
    visitOperationLikeNode(o);
  }

  public void visitOperationProcedureDecl(@NotNull ResOperationProcedureDecl o) {
    visitOperationLikeNode(o);
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

  public void visitProcedureDecl(@NotNull ResProcedureDecl o) {
    visitOperationLikeNode(o);
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

  public void visitSimpleStatement(@NotNull ResSimpleStatement o) {
    visitCompositeElement(o);
  }

  public void visitSpecModuleParameters(@NotNull ResSpecModuleParameters o) {
    visitModuleParameters(o);
  }

  public void visitStatement(@NotNull ResStatement o) {
    visitCompositeElement(o);
  }

  public void visitSwapStatement(@NotNull ResSwapStatement o) {
    visitCompositeElement(o);
  }

  public void visitType(@NotNull ResType o) {
    visitMathExp(o);
  }

  public void visitTypeModelDecl(@NotNull ResTypeModelDecl o) {
    visitCompositeElement(o);
  }

  public void visitTypeParamDecl(@NotNull ResTypeParamDecl o) {
    visitNamedElement(o);
  }

  public void visitTypeReferenceExp(@NotNull ResTypeReferenceExp o) {
    visitReferenceExpBase(o);
  }

  public void visitUsesList(@NotNull ResUsesList o) {
    visitCompositeElement(o);
  }

  public void visitVarDeclGroup(@NotNull ResVarDeclGroup o) {
    visitCompositeElement(o);
  }

  public void visitVarDef(@NotNull ResVarDef o) {
    visitNamedElement(o);
  }

  public void visitVarSpec(@NotNull ResVarSpec o) {
    visitCompositeElement(o);
  }

  public void visitWhileStatement(@NotNull ResWhileStatement o) {
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

  public void visitOperationLikeNode(@NotNull ResOperationLikeNode o) {
    visitCompositeElement(o);
  }

  public void visitReferenceExpBase(@NotNull ResReferenceExpBase o) {
    visitCompositeElement(o);
  }

  public void visitTypeOwner(@NotNull ResTypeOwner o) {
    visitCompositeElement(o);
  }

  public void visitCompositeElement(@NotNull ResCompositeElement o) {
    visitElement(o);
  }

}
