// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.jetbrains.resolve.psi.ResCompositeElementType;
import com.jetbrains.resolve.psi.ResTokenType;
import com.jetbrains.resolve.psi.impl.*;

public interface ResTypes {

  IElementType ARGUMENT_LIST = new ResCompositeElementType("ARGUMENT_LIST");
  IElementType ENTAILS_CLAUSE = new ResCompositeElementType("ENTAILS_CLAUSE");
  IElementType EXP = new ResCompositeElementType("EXP");
  IElementType INFIX_EXP = new ResCompositeElementType("INFIX_EXP");
  IElementType LITERAL_EXP = new ResCompositeElementType("LITERAL_EXP");
  IElementType MATH_ALTERNATIVES_LIST = new ResCompositeElementType("MATH_ALTERNATIVES_LIST");
  IElementType MATH_ALTERNATIVE_EXP = new ResCompositeElementType("MATH_ALTERNATIVE_EXP");
  IElementType MATH_ALTERNATIVE_ITEM_EXP = new ResCompositeElementType("MATH_ALTERNATIVE_ITEM_EXP");
  IElementType MATH_ASSERTION_EXP = new ResCompositeElementType("MATH_ASSERTION_EXP");
  IElementType MATH_BIG_UNION_EXP = new ResCompositeElementType("MATH_BIG_UNION_EXP");
  IElementType MATH_CART_PROD_EXP = new ResCompositeElementType("MATH_CART_PROD_EXP");
  IElementType MATH_CATEGORICAL_DEFN_DECL = new ResCompositeElementType("MATH_CATEGORICAL_DEFN_DECL");
  IElementType MATH_CLSSFTN_ASSRT_EXP = new ResCompositeElementType("MATH_CLSSFTN_ASSRT_EXP");
  IElementType MATH_EXP = new ResCompositeElementType("MATH_EXP");
  IElementType MATH_FUNCTION_APP_LIST = new ResCompositeElementType("MATH_FUNCTION_APP_LIST");
  IElementType MATH_INCOMING_EXP = new ResCompositeElementType("MATH_INCOMING_EXP");
  IElementType MATH_INDUCTIVE_DEFN_DECL = new ResCompositeElementType("MATH_INDUCTIVE_DEFN_DECL");
  IElementType MATH_INFIX_APPLY_EXP = new ResCompositeElementType("MATH_INFIX_APPLY_EXP");
  IElementType MATH_INFIX_DEFN_SIG = new ResCompositeElementType("MATH_INFIX_DEFN_SIG");
  IElementType MATH_LAMBDA_EXP = new ResCompositeElementType("MATH_LAMBDA_EXP");
  IElementType MATH_MIXFIX_APPLY_EXP = new ResCompositeElementType("MATH_MIXFIX_APPLY_EXP");
  IElementType MATH_NESTED_EXP = new ResCompositeElementType("MATH_NESTED_EXP");
  IElementType MATH_OUTFIX_APPLY_EXP = new ResCompositeElementType("MATH_OUTFIX_APPLY_EXP");
  IElementType MATH_OUTFIX_DEFN_SIG = new ResCompositeElementType("MATH_OUTFIX_DEFN_SIG");
  IElementType MATH_POSTFIX_DEFN_SIG = new ResCompositeElementType("MATH_POSTFIX_DEFN_SIG");
  IElementType MATH_PREFIX_APPLY_EXP = new ResCompositeElementType("MATH_PREFIX_APPLY_EXP");
  IElementType MATH_PREFIX_DEFN_SIG = new ResCompositeElementType("MATH_PREFIX_DEFN_SIG");
  IElementType MATH_QUANTIFIED_EXP = new ResCompositeElementType("MATH_QUANTIFIED_EXP");
  IElementType MATH_RECOGNITION_DECL = new ResCompositeElementType("MATH_RECOGNITION_DECL");
  IElementType MATH_REFERENCE_EXP = new ResCompositeElementType("MATH_REFERENCE_EXP");
  IElementType MATH_SELECTOR_EXP = new ResCompositeElementType("MATH_SELECTOR_EXP");
  IElementType MATH_STANDARD_DEFN_DECL = new ResCompositeElementType("MATH_STANDARD_DEFN_DECL");
  IElementType MATH_SYMBOL_NAME = new ResCompositeElementType("MATH_SYMBOL_NAME");
  IElementType MATH_THEOREM_DECL = new ResCompositeElementType("MATH_THEOREM_DECL");
  IElementType MATH_VAR_DECL = new ResCompositeElementType("MATH_VAR_DECL");
  IElementType MATH_VAR_DECL_GROUP = new ResCompositeElementType("MATH_VAR_DECL_GROUP");
  IElementType MATH_VAR_DEF = new ResCompositeElementType("MATH_VAR_DEF");
  IElementType MODULE_IDENTIFIER = new ResCompositeElementType("MODULE_IDENTIFIER");
  IElementType MODULE_IDENTIFIER_SPEC = new ResCompositeElementType("MODULE_IDENTIFIER_SPEC");
  IElementType MODULE_LIBRARY_IDENTIFIER = new ResCompositeElementType("MODULE_LIBRARY_IDENTIFIER");
  IElementType NESTED_EXP = new ResCompositeElementType("NESTED_EXP");
  IElementType PARAMETER_MODE = new ResCompositeElementType("PARAMETER_MODE");
  IElementType PARAM_DECL = new ResCompositeElementType("PARAM_DECL");
  IElementType PARAM_DEF = new ResCompositeElementType("PARAM_DEF");
  IElementType PARAM_EXP = new ResCompositeElementType("PARAM_EXP");
  IElementType PRECIS_BLOCK = new ResCompositeElementType("PRECIS_BLOCK");
  IElementType PRECIS_MODULE_DECL = new ResCompositeElementType("PRECIS_MODULE_DECL");
  IElementType PROG_SYMBOL_NAME = new ResCompositeElementType("PROG_SYMBOL_NAME");
  IElementType REALIZATION_MODULE_DECL = new ResCompositeElementType("REALIZATION_MODULE_DECL");
  IElementType REALIZ_BLOCK = new ResCompositeElementType("REALIZ_BLOCK");
  IElementType REALIZ_MODULE_PARAMETERS = new ResCompositeElementType("REALIZ_MODULE_PARAMETERS");
  IElementType REFERENCE_EXP = new ResCompositeElementType("REFERENCE_EXP");
  IElementType REQUIRES_CLAUSE = new ResCompositeElementType("REQUIRES_CLAUSE");
  IElementType SELECTOR_EXP = new ResCompositeElementType("SELECTOR_EXP");
  IElementType TYPE_PARAM_DECL = new ResCompositeElementType("TYPE_PARAM_DECL");
  IElementType USES_LIST = new ResCompositeElementType("USES_LIST");

  IElementType ALTERS = new ResTokenType("alters");
  IElementType BAD_CHARACTER = new ResTokenType("\"\\\\\"");
  IElementType BAR = new ResTokenType("|");
  IElementType BIGOPERATOR = new ResTokenType("bigoperator");
  IElementType CART_PROD = new ResTokenType("Cart_Prod");
  IElementType CATEGORICAL = new ResTokenType("Categorical");
  IElementType CHAINABLE = new ResTokenType("Chainable");
  IElementType CHAR = new ResTokenType("char");
  IElementType CLEARS = new ResTokenType("clears");
  IElementType COERCER = new ResTokenType("Coercer");
  IElementType COLON = new ResTokenType(":");
  IElementType COLON_COLON = new ResTokenType("::");
  IElementType COLON_EQUALS = new ResTokenType(":=");
  IElementType COLON_EQUALS_COLON = new ResTokenType(":=:");
  IElementType COMMA = new ResTokenType(",");
  IElementType COROLLARY = new ResTokenType("Corollary");
  IElementType DBL_BAR = new ResTokenType("∥");
  IElementType DBL_LBRACE = new ResTokenType("{{");
  IElementType DBL_RBRACE = new ResTokenType("}}");
  IElementType DEFINITION = new ResTokenType("Definition");
  IElementType DOT = new ResTokenType(".");
  IElementType END = new ResTokenType("end");
  IElementType EVALUATES = new ResTokenType("evaluates");
  IElementType EXISTS = new ResTokenType("∃");
  IElementType EXTENDS = new ResTokenType("extends");
  IElementType FALSE = new ResTokenType("false");
  IElementType FOR = new ResTokenType("for");
  IElementType FORALL = new ResTokenType("∀");
  IElementType FROM = new ResTokenType("from");
  IElementType IDENTIFIER = new ResTokenType("identifier");
  IElementType IF = new ResTokenType("if");
  IElementType IMPLICIT = new ResTokenType("Implicit");
  IElementType INDUCTIVE = new ResTokenType("Inductive");
  IElementType IND_BASE = new ResTokenType("(i.)");
  IElementType IND_HYPO = new ResTokenType("(ii.)");
  IElementType INT = new ResTokenType("int");
  IElementType IS = new ResTokenType("is");
  IElementType LAMBDA = new ResTokenType("λ");
  IElementType LANGLE = new ResTokenType("⟨");
  IElementType LBRACE = new ResTokenType("{");
  IElementType LBRACK = new ResTokenType("[");
  IElementType LCEIL = new ResTokenType("⌈");
  IElementType LPAREN = new ResTokenType("(");
  IElementType MATHSYMBOL = new ResTokenType("mathsymbol");
  IElementType OF = new ResTokenType("of");
  IElementType OPERATION = new ResTokenType("Operation");
  IElementType OTHERWISE = new ResTokenType("otherwise");
  IElementType POUND = new ResTokenType("#");
  IElementType PRECIS = new ResTokenType("Precis");
  IElementType PRESERVES = new ResTokenType("preserves");
  IElementType PRIME = new ResTokenType("′");
  IElementType RANGLE = new ResTokenType("⟩");
  IElementType RAW_STRING = new ResTokenType("raw_string");
  IElementType RBRACE = new ResTokenType("}");
  IElementType RBRACK = new ResTokenType("]");
  IElementType RCEIL = new ResTokenType("⌉");
  IElementType REALIZATION = new ResTokenType("Realization");
  IElementType RECOGNITION = new ResTokenType("Recognition");
  IElementType REPLACES = new ResTokenType("replaces");
  IElementType REQUIRES = new ResTokenType("requires");
  IElementType RESTORES = new ResTokenType("restores");
  IElementType RPAREN = new ResTokenType(")");
  IElementType SEMICOLON = new ResTokenType(";");
  IElementType STRING = new ResTokenType("string");
  IElementType SYMBOL = new ResTokenType("symbol");
  IElementType THEOREM = new ResTokenType("Theorem");
  IElementType TRICOLON = new ResTokenType("ː");
  IElementType TRI_EQUALS = new ResTokenType("≜");
  IElementType TRUE = new ResTokenType("true");
  IElementType TYPE_PARAM = new ResTokenType("type");
  IElementType UPDATES = new ResTokenType("updates");
  IElementType USES = new ResTokenType("uses");
  IElementType VALUED = new ResTokenType("Valued");
  IElementType WHICH_ENTAILS = new ResTokenType("which_entails");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ARGUMENT_LIST) {
        return new ResArgumentListImpl(node);
      }
      else if (type == ENTAILS_CLAUSE) {
        return new ResEntailsClauseImpl(node);
      }
      else if (type == INFIX_EXP) {
        return new ResInfixExpImpl(node);
      }
      else if (type == LITERAL_EXP) {
        return new ResLiteralExpImpl(node);
      }
      else if (type == MATH_ALTERNATIVES_LIST) {
        return new ResMathAlternativesListImpl(node);
      }
      else if (type == MATH_ALTERNATIVE_EXP) {
        return new ResMathAlternativeExpImpl(node);
      }
      else if (type == MATH_ALTERNATIVE_ITEM_EXP) {
        return new ResMathAlternativeItemExpImpl(node);
      }
      else if (type == MATH_BIG_UNION_EXP) {
        return new ResMathBigUnionExpImpl(node);
      }
      else if (type == MATH_CART_PROD_EXP) {
        return new ResMathCartProdExpImpl(node);
      }
      else if (type == MATH_CATEGORICAL_DEFN_DECL) {
        return new ResMathCategoricalDefnDeclImpl(node);
      }
      else if (type == MATH_CLSSFTN_ASSRT_EXP) {
        return new ResMathClssftnAssrtExpImpl(node);
      }
      else if (type == MATH_FUNCTION_APP_LIST) {
        return new ResMathFunctionAppListImpl(node);
      }
      else if (type == MATH_INCOMING_EXP) {
        return new ResMathIncomingExpImpl(node);
      }
      else if (type == MATH_INDUCTIVE_DEFN_DECL) {
        return new ResMathInductiveDefnDeclImpl(node);
      }
      else if (type == MATH_INFIX_APPLY_EXP) {
        return new ResMathInfixApplyExpImpl(node);
      }
      else if (type == MATH_INFIX_DEFN_SIG) {
        return new ResMathInfixDefnSigImpl(node);
      }
      else if (type == MATH_LAMBDA_EXP) {
        return new ResMathLambdaExpImpl(node);
      }
      else if (type == MATH_MIXFIX_APPLY_EXP) {
        return new ResMathMixfixApplyExpImpl(node);
      }
      else if (type == MATH_NESTED_EXP) {
        return new ResMathNestedExpImpl(node);
      }
      else if (type == MATH_OUTFIX_APPLY_EXP) {
        return new ResMathOutfixApplyExpImpl(node);
      }
      else if (type == MATH_OUTFIX_DEFN_SIG) {
        return new ResMathOutfixDefnSigImpl(node);
      }
      else if (type == MATH_POSTFIX_DEFN_SIG) {
        return new ResMathPostfixDefnSigImpl(node);
      }
      else if (type == MATH_PREFIX_APPLY_EXP) {
        return new ResMathPrefixApplyExpImpl(node);
      }
      else if (type == MATH_PREFIX_DEFN_SIG) {
        return new ResMathPrefixDefnSigImpl(node);
      }
      else if (type == MATH_QUANTIFIED_EXP) {
        return new ResMathQuantifiedExpImpl(node);
      }
      else if (type == MATH_RECOGNITION_DECL) {
        return new ResMathRecognitionDeclImpl(node);
      }
      else if (type == MATH_REFERENCE_EXP) {
        return new ResMathReferenceExpImpl(node);
      }
      else if (type == MATH_SELECTOR_EXP) {
        return new ResMathSelectorExpImpl(node);
      }
      else if (type == MATH_STANDARD_DEFN_DECL) {
        return new ResMathStandardDefnDeclImpl(node);
      }
      else if (type == MATH_SYMBOL_NAME) {
        return new ResMathSymbolNameImpl(node);
      }
      else if (type == MATH_THEOREM_DECL) {
        return new ResMathTheoremDeclImpl(node);
      }
      else if (type == MATH_VAR_DECL) {
        return new ResMathVarDeclImpl(node);
      }
      else if (type == MATH_VAR_DECL_GROUP) {
        return new ResMathVarDeclGroupImpl(node);
      }
      else if (type == MATH_VAR_DEF) {
        return new ResMathVarDefImpl(node);
      }
      else if (type == MODULE_IDENTIFIER) {
        return new ResModuleIdentifierImpl(node);
      }
      else if (type == MODULE_IDENTIFIER_SPEC) {
        return new ResModuleIdentifierSpecImpl(node);
      }
      else if (type == MODULE_LIBRARY_IDENTIFIER) {
        return new ResModuleLibraryIdentifierImpl(node);
      }
      else if (type == NESTED_EXP) {
        return new ResNestedExpImpl(node);
      }
      else if (type == PARAMETER_MODE) {
        return new ResParameterModeImpl(node);
      }
      else if (type == PARAM_DECL) {
        return new ResParamDeclImpl(node);
      }
      else if (type == PARAM_DEF) {
        return new ResParamDefImpl(node);
      }
      else if (type == PARAM_EXP) {
        return new ResParamExpImpl(node);
      }
      else if (type == PRECIS_BLOCK) {
        return new ResPrecisBlockImpl(node);
      }
      else if (type == PRECIS_MODULE_DECL) {
        return new ResPrecisModuleDeclImpl(node);
      }
      else if (type == PROG_SYMBOL_NAME) {
        return new ResProgSymbolNameImpl(node);
      }
      else if (type == REALIZATION_MODULE_DECL) {
        return new ResRealizationModuleDeclImpl(node);
      }
      else if (type == REALIZ_BLOCK) {
        return new ResRealizBlockImpl(node);
      }
      else if (type == REALIZ_MODULE_PARAMETERS) {
        return new ResRealizModuleParametersImpl(node);
      }
      else if (type == REFERENCE_EXP) {
        return new ResReferenceExpImpl(node);
      }
      else if (type == REQUIRES_CLAUSE) {
        return new ResRequiresClauseImpl(node);
      }
      else if (type == SELECTOR_EXP) {
        return new ResSelectorExpImpl(node);
      }
      else if (type == TYPE_PARAM_DECL) {
        return new ResTypeParamDeclImpl(node);
      }
      else if (type == USES_LIST) {
        return new ResUsesListImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
