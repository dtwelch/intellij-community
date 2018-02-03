// This is a generated file. Not intended for manual editing.
package com.jetbrains.resolve.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.jetbrains.resolve.ResTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class ResParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == FACILITY_MODULE_DECL) {
      r = FacilityModuleDecl(b, 0);
    }
    else if (t == PRECIS_MODULE_DECL) {
      r = PrecisModuleDecl(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return ModuleDecl(b, l + 1);
  }

  /* ********************************************************** */
  // 'Facility' identifier ';'
  // 'end' identifier ';'
  public static boolean FacilityModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityModuleDecl")) return false;
    if (!nextTokenIs(b, FACILITY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FACILITY_MODULE_DECL, null);
    r = consumeTokens(b, 3, FACILITY, IDENTIFIER, SEMICOLON);
    p = r; // pin = 3
    r = r && report_error_(b, consumeToken(b, "end"));
    r = p && report_error_(b, consumeTokens(b, -1, IDENTIFIER, SEMICOLON)) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // PrecisModuleDecl
  //     | FacilityModuleDecl
  static boolean ModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleDecl")) return false;
    if (!nextTokenIs(b, "", FACILITY, PRECIS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PrecisModuleDecl(b, l + 1);
    if (!r) r = FacilityModuleDecl(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Precis' identifier ';'
  // //PrecisBlock
  // end identifier ';'
  public static boolean PrecisModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl")) return false;
    if (!nextTokenIs(b, PRECIS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PRECIS_MODULE_DECL, null);
    r = consumeTokens(b, 2, PRECIS, IDENTIFIER, SEMICOLON, END, IDENTIFIER, SEMICOLON);
    p = r; // pin = 2
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ModuleIdentifierSpec (',' ModuleIdentifierSpec)*
  static boolean UsesSpecs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesSpecs")) return false;
    if (!nextTokenIs(b, MODULEIDENTIFIERSPEC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, MODULEIDENTIFIERSPEC);
    p = r; // pin = 1
    r = r && UsesSpecs_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' ModuleIdentifierSpec)*
  private static boolean UsesSpecs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesSpecs_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!UsesSpecs_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "UsesSpecs_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' ModuleIdentifierSpec
  private static boolean UsesSpecs_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesSpecs_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokens(b, 1, COMMA, MODULEIDENTIFIERSPEC);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

}
