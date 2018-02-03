// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.jetbrains.resolve.psi.ResCompositeElementType;
import com.jetbrains.resolve.psi.ResTokenType;
import com.jetbrains.resolve.psi.impl.*;

public interface ResTypes {

  IElementType FACILITY_MODULE_DECL = new ResCompositeElementType("FACILITY_MODULE_DECL");
  IElementType PRECIS_MODULE_DECL = new ResCompositeElementType("PRECIS_MODULE_DECL");

  IElementType BAD_CHARACTER = new ResTokenType("\"\\\\\"");
  IElementType BAR = new ResTokenType("|");
  IElementType CART_PROD = new ResTokenType("Cart_Prod");
  IElementType CATEGORICAL = new ResTokenType("Categorical");
  IElementType CHAINABLE = new ResTokenType("Chainable");
  IElementType CHAR = new ResTokenType("char");
  IElementType COLON = new ResTokenType(":");
  IElementType COLON_COLON = new ResTokenType("::");
  IElementType COLON_EQUALS = new ResTokenType(":=");
  IElementType COLON_EQUALS_COLON = new ResTokenType(":=:");
  IElementType COMMA = new ResTokenType(",");
  IElementType CONCEPT = new ResTokenType("Concept");
  IElementType COROLLARY = new ResTokenType("Corollary");
  IElementType DBL_BAR = new ResTokenType("∥");
  IElementType DBL_LBRACE = new ResTokenType("{{");
  IElementType DBL_RBRACE = new ResTokenType("}}");
  IElementType DEFINES = new ResTokenType("Defines");
  IElementType DEFINITION = new ResTokenType("Definition");
  IElementType DOT = new ResTokenType(".");
  IElementType END = new ResTokenType("end");
  IElementType ENHANCEMENT = new ResTokenType("Enhancement");
  IElementType EXISTS = new ResTokenType("∃");
  IElementType EXTENSION = new ResTokenType("Extension");
  IElementType FACILITY = new ResTokenType("Facility");
  IElementType FORALL = new ResTokenType("∀");
  IElementType IDENTIFIER = new ResTokenType("identifier");
  IElementType IF_PROG = new ResTokenType("If");
  IElementType IMPLICIT = new ResTokenType("Implicit");
  IElementType INDUCTIVE = new ResTokenType("Inductive");
  IElementType INT = new ResTokenType("int");
  IElementType LAMBDA = new ResTokenType("λ");
  IElementType LANGLE = new ResTokenType("⟨");
  IElementType LBRACE = new ResTokenType("{");
  IElementType LBRACK = new ResTokenType("[");
  IElementType LCEIL = new ResTokenType("⌈");
  IElementType LPAREN = new ResTokenType("(");
  IElementType MODULEIDENTIFIERSPEC = new ResTokenType("ModuleIdentifierSpec");
  IElementType NOTICE = new ResTokenType("Notice");
  IElementType OPERATION = new ResTokenType("Operation");
  IElementType POUND = new ResTokenType("#");
  IElementType PRECIS = new ResTokenType("Precis");
  IElementType PRIME = new ResTokenType("′");
  IElementType PROCEDURE = new ResTokenType("Procedure");
  IElementType RANGLE = new ResTokenType("⟩");
  IElementType RAW_STRING = new ResTokenType("raw_string");
  IElementType RBRACE = new ResTokenType("}");
  IElementType RBRACK = new ResTokenType("]");
  IElementType RCEIL = new ResTokenType("⌉");
  IElementType REALIZATION = new ResTokenType("Realization");
  IElementType RECOGNITION = new ResTokenType("Recognition");
  IElementType RECORD = new ResTokenType("Record");
  IElementType RECURSIVE = new ResTokenType("Recursive");
  IElementType RPAREN = new ResTokenType(")");
  IElementType SEMICOLON = new ResTokenType(";");
  IElementType THEOREM = new ResTokenType("Theorem");
  IElementType TRICOLON = new ResTokenType("ː");
  IElementType TRI_EQUALS = new ResTokenType("≜");
  IElementType TYPE_FAMILY = new ResTokenType("Type");
  IElementType TYPE_PARAM = new ResTokenType("type");
  IElementType VALUED = new ResTokenType("Valued");
  IElementType VAR = new ResTokenType("Var");
  IElementType WHILE = new ResTokenType("While");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == FACILITY_MODULE_DECL) {
        return new ResFacilityModuleDeclImpl(node);
      }
      else if (type == PRECIS_MODULE_DECL) {
        return new ResPrecisModuleDeclImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
