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
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ARGUMENT_LIST) {
      r = ArgumentList(b, 0);
    }
    else if (t == EXP) {
      r = Exp(b, 0, -1);
    }
    else if (t == FACILITY_MODULE_DECL) {
      r = FacilityModuleDecl(b, 0);
    }
    else if (t == MODULE_IDENTIFIER) {
      r = ModuleIdentifier(b, 0);
    }
    else if (t == MODULE_IDENTIFIER_SPEC) {
      r = ModuleIdentifierSpec(b, 0);
    }
    else if (t == MODULE_LIBRARY_IDENTIFIER) {
      r = ModuleLibraryIdentifier(b, 0);
    }
    else if (t == PRECIS_MODULE_DECL) {
      r = PrecisModuleDecl(b, 0);
    }
    else if (t == PROG_SYMBOL_NAME) {
      r = ProgSymbolName(b, 0);
    }
    else if (t == REFERENCE_EXP) {
      r = ReferenceExp(b, 0);
    }
    else if (t == USES_LIST) {
      r = UsesList(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return ModuleDecl(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(EXP, INFIX_EXP, LITERAL_EXP, NESTED_EXP,
      PARAM_EXP, REFERENCE_EXP, SELECTOR_EXP),
  };

  /* ********************************************************** */
  // '(' ExpArgumentList? ')'
  public static boolean ArgumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentList")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENT_LIST, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, ArgumentList_1(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ExpArgumentList?
  private static boolean ArgumentList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentList_1")) return false;
    ExpArgumentList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Exp (',' Exp)*
  static boolean ExpArgumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpArgumentList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = Exp(b, l + 1, -1);
    p = r; // pin = 1
    r = r && ExpArgumentList_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' Exp)*
  private static boolean ExpArgumentList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpArgumentList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ExpArgumentList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ExpArgumentList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' Exp
  private static boolean ExpArgumentList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpArgumentList_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && Exp(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'Facility' identifier ';'
  // end identifier ';'
  public static boolean FacilityModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityModuleDecl")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FACILITY_MODULE_DECL, "<facility module decl>");
    r = consumeToken(b, "Facility");
    r = r && consumeTokens(b, 2, IDENTIFIER, SEMICOLON, END, IDENTIFIER, SEMICOLON);
    p = r; // pin = 3
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // from ModuleLibraryIdentifier
  static boolean FromClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FromClause")) return false;
    if (!nextTokenIs(b, FROM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, FROM);
    p = r; // pin = 1
    r = r && ModuleLibraryIdentifier(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // PrecisModuleDecl
  //     | FacilityModuleDecl
  static boolean ModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleDecl")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PrecisModuleDecl(b, l + 1);
    if (!r) r = FacilityModuleDecl(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  public static boolean ModuleIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleIdentifier")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, MODULE_IDENTIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // ModuleIdentifier [FromClause]
  public static boolean ModuleIdentifierSpec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleIdentifierSpec")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MODULE_IDENTIFIER_SPEC, null);
    r = ModuleIdentifier(b, l + 1);
    p = r; // pin = 1
    r = r && ModuleIdentifierSpec_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [FromClause]
  private static boolean ModuleIdentifierSpec_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleIdentifierSpec_1")) return false;
    FromClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // identifier ('.' identifier)*
  public static boolean ModuleLibraryIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleLibraryIdentifier")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MODULE_LIBRARY_IDENTIFIER, null);
    r = consumeToken(b, IDENTIFIER);
    p = r; // pin = 1
    r = r && ModuleLibraryIdentifier_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ('.' identifier)*
  private static boolean ModuleLibraryIdentifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleLibraryIdentifier_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ModuleLibraryIdentifier_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ModuleLibraryIdentifier_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // '.' identifier
  private static boolean ModuleLibraryIdentifier_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleLibraryIdentifier_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokens(b, 1, DOT, IDENTIFIER);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'Precis' identifier (for ReferenceExp)? ';'
  // UsesList?
  // //PrecisBlock
  // end identifier ';'
  public static boolean PrecisModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl")) return false;
    if (!nextTokenIs(b, PRECIS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PRECIS_MODULE_DECL, null);
    r = consumeTokens(b, 2, PRECIS, IDENTIFIER);
    p = r; // pin = 2
    r = r && report_error_(b, PrecisModuleDecl_2(b, l + 1));
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, PrecisModuleDecl_4(b, l + 1)) && r;
    r = p && report_error_(b, consumeTokens(b, -1, END, IDENTIFIER, SEMICOLON)) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (for ReferenceExp)?
  private static boolean PrecisModuleDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl_2")) return false;
    PrecisModuleDecl_2_0(b, l + 1);
    return true;
  }

  // for ReferenceExp
  private static boolean PrecisModuleDecl_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FOR);
    r = r && ReferenceExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // UsesList?
  private static boolean PrecisModuleDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl_4")) return false;
    UsesList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // identifier|symbol
  public static boolean ProgSymbolName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ProgSymbolName")) return false;
    if (!nextTokenIs(b, "<prog symbol name>", IDENTIFIER, SYMBOL)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PROG_SYMBOL_NAME, "<prog symbol name>");
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, SYMBOL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '::' identifier
  public static boolean QualifiedReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "QualifiedReferenceExp")) return false;
    if (!nextTokenIs(b, COLON_COLON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, REFERENCE_EXP, null);
    r = consumeTokens(b, 0, COLON_COLON, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // identifier
  public static boolean ReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ReferenceExp")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, REFERENCE_EXP, r);
    return r;
  }

  /* ********************************************************** */
  // uses UsesSpecs ';'
  public static boolean UsesList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesList")) return false;
    if (!nextTokenIs(b, USES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, USES_LIST, null);
    r = consumeToken(b, USES);
    p = r; // pin = 1
    r = r && report_error_(b, UsesSpecs(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ModuleIdentifierSpec (',' ModuleIdentifierSpec)*
  static boolean UsesSpecs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesSpecs")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = ModuleIdentifierSpec(b, l + 1);
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
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && ModuleIdentifierSpec(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // Expression root: Exp
  // Operator priority table:
  // 0: BINARY(InfixExp)
  // 1: POSTFIX(ParamExp)
  // 2: ATOM(NestedExp)
  // 3: ATOM(LiteralExp) BINARY(SelectorExp) ATOM(NameExp)
  public static boolean Exp(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "Exp")) return false;
    addVariant(b, "<exp>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<exp>");
    r = NestedExp(b, l + 1);
    if (!r) r = LiteralExp(b, l + 1);
    if (!r) r = NameExp(b, l + 1);
    p = r;
    r = r && Exp_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean Exp_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "Exp_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && ProgSymbolName(b, l + 1)) {
        r = Exp(b, l, 0);
        exit_section_(b, l, m, INFIX_EXP, r, true, null);
      }
      else if (g < 1 && leftMarkerIs(b, REFERENCE_EXP) && ArgumentList(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, PARAM_EXP, r, true, null);
      }
      else if (g < 3 && consumeTokenSmart(b, DOT)) {
        r = Exp(b, l, 3);
        exit_section_(b, l, m, SELECTOR_EXP, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // '(' Exp ')'
  public static boolean NestedExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NestedExp")) return false;
    if (!nextTokenIsSmart(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NESTED_EXP, null);
    r = consumeTokenSmart(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, Exp(b, l + 1, -1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // int|string|true|false
  public static boolean LiteralExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LiteralExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LITERAL_EXP, "<literal exp>");
    r = consumeTokenSmart(b, INT);
    if (!r) r = consumeTokenSmart(b, STRING);
    if (!r) r = consumeTokenSmart(b, TRUE);
    if (!r) r = consumeTokenSmart(b, FALSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ReferenceExp QualifiedReferenceExp?
  public static boolean NameExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NameExp")) return false;
    if (!nextTokenIsSmart(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, REFERENCE_EXP, null);
    r = ReferenceExp(b, l + 1);
    r = r && NameExp_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // QualifiedReferenceExp?
  private static boolean NameExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NameExp_1")) return false;
    QualifiedReferenceExp(b, l + 1);
    return true;
  }

}
