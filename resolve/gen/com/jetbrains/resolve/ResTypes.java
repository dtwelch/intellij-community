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
  IElementType EXP = new ResCompositeElementType("EXP");
  IElementType INFIX_EXP = new ResCompositeElementType("INFIX_EXP");
  IElementType LITERAL_EXP = new ResCompositeElementType("LITERAL_EXP");
  IElementType MODULE_IDENTIFIER = new ResCompositeElementType("MODULE_IDENTIFIER");
  IElementType MODULE_IDENTIFIER_SPEC = new ResCompositeElementType("MODULE_IDENTIFIER_SPEC");
  IElementType MODULE_LIBRARY_IDENTIFIER = new ResCompositeElementType("MODULE_LIBRARY_IDENTIFIER");
  IElementType NESTED_EXP = new ResCompositeElementType("NESTED_EXP");
  IElementType PARAM_EXP = new ResCompositeElementType("PARAM_EXP");
  IElementType PRECIS_MODULE_DECL = new ResCompositeElementType("PRECIS_MODULE_DECL");
  IElementType PROG_SYMBOL_NAME = new ResCompositeElementType("PROG_SYMBOL_NAME");
  IElementType REFERENCE_EXP = new ResCompositeElementType("REFERENCE_EXP");
  IElementType SELECTOR_EXP = new ResCompositeElementType("SELECTOR_EXP");
  IElementType USES_LIST = new ResCompositeElementType("USES_LIST");

  IElementType BAD_CHARACTER = new ResTokenType("\"\\\\\"");
  IElementType BAR = new ResTokenType("|");
  IElementType CHAR = new ResTokenType("char");
  IElementType COLON = new ResTokenType(":");
  IElementType COLON_COLON = new ResTokenType("::");
  IElementType COLON_EQUALS = new ResTokenType(":=");
  IElementType COLON_EQUALS_COLON = new ResTokenType(":=:");
  IElementType COMMA = new ResTokenType(",");
  IElementType DBL_BAR = new ResTokenType("∥");
  IElementType DBL_LBRACE = new ResTokenType("{{");
  IElementType DBL_RBRACE = new ResTokenType("}}");
  IElementType DOT = new ResTokenType(".");
  IElementType END = new ResTokenType("end");
  IElementType EXISTS = new ResTokenType("∃");
  IElementType EXTENDS = new ResTokenType("extends");
  IElementType FALSE = new ResTokenType("false");
  IElementType FORALL = new ResTokenType("∀");
  IElementType FROM = new ResTokenType("from");
  IElementType IDENTIFIER = new ResTokenType("identifier");
  IElementType INT = new ResTokenType("int");
  IElementType LAMBDA = new ResTokenType("λ");
  IElementType LANGLE = new ResTokenType("⟨");
  IElementType LBRACE = new ResTokenType("{");
  IElementType LBRACK = new ResTokenType("[");
  IElementType LCEIL = new ResTokenType("⌈");
  IElementType LPAREN = new ResTokenType("(");
  IElementType POUND = new ResTokenType("#");
  IElementType PRECIS = new ResTokenType("Precis");
  IElementType PRIME = new ResTokenType("′");
  IElementType RANGLE = new ResTokenType("⟩");
  IElementType RAW_STRING = new ResTokenType("raw_string");
  IElementType RBRACE = new ResTokenType("}");
  IElementType RBRACK = new ResTokenType("]");
  IElementType RCEIL = new ResTokenType("⌉");
  IElementType RPAREN = new ResTokenType(")");
  IElementType SEMICOLON = new ResTokenType(";");
  IElementType STRING = new ResTokenType("string");
  IElementType SYMBOL = new ResTokenType("symbol");
  IElementType TRICOLON = new ResTokenType("ː");
  IElementType TRI_EQUALS = new ResTokenType("≜");
  IElementType TRUE = new ResTokenType("true");
  IElementType USES = new ResTokenType("uses");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ARGUMENT_LIST) {
        return new ResArgumentListImpl(node);
      }
      else if (type == INFIX_EXP) {
        return new ResInfixExpImpl(node);
      }
      else if (type == LITERAL_EXP) {
        return new ResLiteralExpImpl(node);
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
      else if (type == PARAM_EXP) {
        return new ResParamExpImpl(node);
      }
      else if (type == PRECIS_MODULE_DECL) {
        return new ResPrecisModuleDeclImpl(node);
      }
      else if (type == PROG_SYMBOL_NAME) {
        return new ResProgSymbolNameImpl(node);
      }
      else if (type == REFERENCE_EXP) {
        return new ResReferenceExpImpl(node);
      }
      else if (type == SELECTOR_EXP) {
        return new ResSelectorExpImpl(node);
      }
      else if (type == USES_LIST) {
        return new ResUsesListImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
