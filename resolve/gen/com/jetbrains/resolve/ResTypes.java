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
  IElementType CHAR = new ResTokenType("char");
  IElementType END = new ResTokenType("end");
  IElementType FACILITY = new ResTokenType("Facility");
  IElementType IDENTIFIER = new ResTokenType("identifier");
  IElementType INT = new ResTokenType("int");
  IElementType PRECIS = new ResTokenType("Precis");
  IElementType RAW_STRING = new ResTokenType("raw_string");
  IElementType SEMICOLON = new ResTokenType(";");

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
