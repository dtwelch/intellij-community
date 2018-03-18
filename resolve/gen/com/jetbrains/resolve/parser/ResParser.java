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
    else if (t == ENTAILS_CLAUSE) {
      r = EntailsClause(b, 0);
    }
    else if (t == EXP) {
      r = Exp(b, 0, -1);
    }
    else if (t == MATH_ALTERNATIVE_ITEM_EXP) {
      r = MathAlternativeItemExp(b, 0);
    }
    else if (t == MATH_ALTERNATIVES_LIST) {
      r = MathAlternativesList(b, 0);
    }
    else if (t == MATH_ASSERTION_EXP) {
      r = MathAssertionExp(b, 0);
    }
    else if (t == MATH_CATEGORICAL_DEFN_DECL) {
      r = MathCategoricalDefnDecl(b, 0);
    }
    else if (t == MATH_EXP) {
      r = MathExp(b, 0, -1);
    }
    else if (t == MATH_FUNCTION_APP_LIST) {
      r = MathFunctionAppList(b, 0);
    }
    else if (t == MATH_INDUCTIVE_DEFN_DECL) {
      r = MathInductiveDefnDecl(b, 0);
    }
    else if (t == MATH_INFIX_DEFN_SIG) {
      r = MathInfixDefnSig(b, 0);
    }
    else if (t == MATH_OUTFIX_DEFN_SIG) {
      r = MathOutfixDefnSig(b, 0);
    }
    else if (t == MATH_POSTFIX_DEFN_SIG) {
      r = MathPostfixDefnSig(b, 0);
    }
    else if (t == MATH_PREFIX_DEFN_SIG) {
      r = MathPrefixDefnSig(b, 0);
    }
    else if (t == MATH_QUANTIFIED_EXP) {
      r = MathQuantifiedExp(b, 0);
    }
    else if (t == MATH_RECOGNITION_DECL) {
      r = MathRecognitionDecl(b, 0);
    }
    else if (t == MATH_REFERENCE_EXP) {
      r = MathReferenceExp(b, 0);
    }
    else if (t == MATH_STANDARD_DEFN_DECL) {
      r = MathStandardDefnDecl(b, 0);
    }
    else if (t == MATH_SYMBOL_NAME) {
      r = MathSymbolName(b, 0);
    }
    else if (t == MATH_THEOREM_DECL) {
      r = MathTheoremDecl(b, 0);
    }
    else if (t == MATH_VAR_DECL) {
      r = MathVarDecl(b, 0);
    }
    else if (t == MATH_VAR_DECL_GROUP) {
      r = MathVarDeclGroup(b, 0);
    }
    else if (t == MATH_VAR_DEF) {
      r = MathVarDef(b, 0);
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
    else if (t == PARAM_DECL) {
      r = ParamDecl(b, 0);
    }
    else if (t == PARAM_DEF) {
      r = ParamDef(b, 0);
    }
    else if (t == PARAMETER_MODE) {
      r = ParameterMode(b, 0);
    }
    else if (t == PRECIS_BLOCK) {
      r = PrecisBlock(b, 0);
    }
    else if (t == PRECIS_MODULE_DECL) {
      r = PrecisModuleDecl(b, 0);
    }
    else if (t == PROG_SYMBOL_NAME) {
      r = ProgSymbolName(b, 0);
    }
    else if (t == REALIZ_BLOCK) {
      r = RealizBlock(b, 0);
    }
    else if (t == REALIZ_MODULE_PARAMETERS) {
      r = RealizModuleParameters(b, 0);
    }
    else if (t == REALIZATION_MODULE_DECL) {
      r = RealizationModuleDecl(b, 0);
    }
    else if (t == REFERENCE_EXP) {
      r = ReferenceExp(b, 0);
    }
    else if (t == REQUIRES_CLAUSE) {
      r = RequiresClause(b, 0);
    }
    else if (t == TYPE_PARAM_DECL) {
      r = TypeParamDecl(b, 0);
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
    create_token_set_(MATH_VAR_DECL, MATH_VAR_DECL_GROUP),
    create_token_set_(EXP, INFIX_EXP, LITERAL_EXP, NESTED_EXP,
      PARAM_EXP, REFERENCE_EXP, SELECTOR_EXP),
    create_token_set_(MATH_ALTERNATIVE_EXP, MATH_ALTERNATIVE_ITEM_EXP, MATH_ASSERTION_EXP, MATH_BIG_UNION_EXP,
      MATH_CART_PROD_EXP, MATH_CLSSFTN_ASSRT_EXP, MATH_EXP, MATH_INCOMING_EXP,
      MATH_INFIX_APPLY_EXP, MATH_LAMBDA_EXP, MATH_MIXFIX_APPLY_EXP, MATH_NESTED_EXP,
      MATH_OUTFIX_APPLY_EXP, MATH_PREFIX_APPLY_EXP, MATH_QUANTIFIED_EXP, MATH_REFERENCE_EXP,
      MATH_SELECTOR_EXP),
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
  // !(is)
  static boolean CategoricalSigListRec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CategoricalSigListRec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, IS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // which_entails MathExp
  public static boolean EntailsClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EntailsClause")) return false;
    if (!nextTokenIs(b, WHICH_ENTAILS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ENTAILS_CLAUSE, null);
    r = consumeToken(b, WHICH_ENTAILS);
    p = r; // pin = 1
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
  // !(/*'Type'|'Operation'|'Facility'|'Recursive'|*/'Definition'|/*'Procedure'|*/end)
  static boolean ImplItemRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplItemRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !ImplItemRecover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // 'Definition'|/*'Procedure'|*/end
  private static boolean ImplItemRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplItemRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DEFINITION);
    if (!r) r = consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ImplParamDecl  (';' ImplParamDecl)*
  static boolean ImplModuleParamList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleParamList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = ImplParamDecl(b, l + 1);
    p = r; // pin = 1
    r = r && ImplModuleParamList_1(b, l + 1);
    exit_section_(b, l, m, r, p, ParamListRec_parser_);
    return r || p;
  }

  // (';' ImplParamDecl)*
  private static boolean ImplModuleParamList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleParamList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ImplModuleParamList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ImplModuleParamList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ';' ImplParamDecl
  private static boolean ImplModuleParamList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleParamList_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, SEMICOLON);
    p = r; // pin = 1
    r = r && ImplParamDecl(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ParamDecl
  static boolean ImplParamDecl(PsiBuilder b, int l) {
    return ParamDecl(b, l + 1);
  }

  /* ********************************************************** */
  // !(end)
  static boolean ItemsRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ItemsRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // MathExp (if MathExp ';' | otherwise ';')
  public static boolean MathAlternativeItemExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeItemExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_ALTERNATIVE_ITEM_EXP, "<math alternative item exp>");
    r = MathExp(b, l + 1, -1);
    r = r && MathAlternativeItemExp_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // if MathExp ';' | otherwise ';'
  private static boolean MathAlternativeItemExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeItemExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathAlternativeItemExp_1_0(b, l + 1);
    if (!r) r = parseTokens(b, 0, OTHERWISE, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // if MathExp ';'
  private static boolean MathAlternativeItemExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeItemExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IF);
    r = r && MathExp(b, l + 1, -1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathAlternativeItemExp+
  public static boolean MathAlternativesList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativesList")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_ALTERNATIVES_LIST, "<math alternatives list>");
    r = MathAlternativeItemExp(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!MathAlternativeItemExp(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathAlternativesList", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // MathExp (',' MathExp)*
  static boolean MathArgList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArgList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathExp(b, l + 1, -1);
    p = r; // pin = 1
    r = r && MathArgList_1(b, l + 1);
    exit_section_(b, l, m, r, p, ParamListRec_parser_);
    return r || p;
  }

  // (',' MathExp)*
  private static boolean MathArgList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArgList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathArgList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathArgList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathExp
  private static boolean MathArgList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArgList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathQuantifiedExp | MathExp
  public static boolean MathAssertionExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAssertionExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, MATH_ASSERTION_EXP, "<math assertion exp>");
    r = MathQuantifiedExp(b, l + 1);
    if (!r) r = MathExp(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '⟨'|'⟩'|'⌈'|'⌉'|'∥'|'['|']'|'|'|'{'|'}'
  public static boolean MathBracketName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathBracketName")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_SYMBOL_NAME, "<math bracket name>");
    r = consumeToken(b, LANGLE);
    if (!r) r = consumeToken(b, RANGLE);
    if (!r) r = consumeToken(b, LCEIL);
    if (!r) r = consumeToken(b, RCEIL);
    if (!r) r = consumeToken(b, DBL_BAR);
    if (!r) r = consumeToken(b, LBRACK);
    if (!r) r = consumeToken(b, RBRACK);
    if (!r) r = consumeToken(b, BAR);
    if (!r) r = consumeToken(b, LBRACE);
    if (!r) r = consumeToken(b, RBRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'Categorical' 'Definition' for
  // MathPrefixDefnSigs is MathExp ';'
  public static boolean MathCategoricalDefnDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathCategoricalDefnDecl")) return false;
    if (!nextTokenIs(b, CATEGORICAL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_CATEGORICAL_DEFN_DECL, null);
    r = consumeTokens(b, 2, CATEGORICAL, DEFINITION, FOR);
    p = r; // pin = 2
    r = r && report_error_(b, MathPrefixDefnSigs(b, l + 1));
    r = p && report_error_(b, consumeToken(b, IS)) && r;
    r = p && report_error_(b, MathExp(b, l + 1, -1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '(' MathDefnParamList ')'
  static boolean MathDefinitionParams(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefinitionParams")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, MathDefnParamList(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MathVarDeclGroup (',' MathVarDeclGroup)*
  static boolean MathDefnParamList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefnParamList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathVarDeclGroup(b, l + 1);
    p = r; // pin = 1
    r = r && MathDefnParamList_1(b, l + 1);
    exit_section_(b, l, m, r, p, ParamListRec_parser_);
    return r || p;
  }

  // (',' MathVarDeclGroup)*
  private static boolean MathDefnParamList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefnParamList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathDefnParamList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathDefnParamList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathVarDeclGroup
  private static boolean MathDefnParamList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefnParamList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathVarDeclGroup(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathPrefixDefnSig | MathPostfixDefnSig | MathOutfixDefnSig | MathInfixDefnSig
  static boolean MathDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefnSig")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathPrefixDefnSig(b, l + 1);
    if (!r) r = MathPostfixDefnSig(b, l + 1);
    if (!r) r = MathOutfixDefnSig(b, l + 1);
    if (!r) r = MathInfixDefnSig(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '(' MathArgList ')'
  public static boolean MathFunctionAppList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathFunctionAppList")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_FUNCTION_APP_LIST, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, MathArgList(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'Inductive' 'Definition' MathDefnSig is
  // '(i.)' MathAssertionExp ';'
  // '(ii.)' MathAssertionExp ';'
  public static boolean MathInductiveDefnDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathInductiveDefnDecl")) return false;
    if (!nextTokenIs(b, INDUCTIVE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_INDUCTIVE_DEFN_DECL, null);
    r = consumeTokens(b, 2, INDUCTIVE, DEFINITION);
    p = r; // pin = 2
    r = r && report_error_(b, MathDefnSig(b, l + 1));
    r = p && report_error_(b, consumeTokens(b, -1, IS, IND_BASE)) && r;
    r = p && report_error_(b, MathAssertionExp(b, l + 1)) && r;
    r = p && report_error_(b, consumeTokens(b, -1, SEMICOLON, IND_HYPO)) && r;
    r = p && report_error_(b, MathAssertionExp(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '(' MathVarDecl ')' MathSymbolName '(' MathVarDecl ')' (':'|'ː') MathExp
  public static boolean MathInfixDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathInfixDefnSig")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_INFIX_DEFN_SIG, null);
    r = consumeToken(b, LPAREN);
    r = r && MathVarDecl(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && MathSymbolName(b, l + 1);
    p = r; // pin = 4
    r = r && report_error_(b, consumeToken(b, LPAREN));
    r = p && report_error_(b, MathVarDecl(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    r = p && report_error_(b, MathInfixDefnSig_7(b, l + 1)) && r;
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ':'|'ː'
  private static boolean MathInfixDefnSig_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathInfixDefnSig_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, TRICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathExp (',' MathExp)*
  static boolean MathNonStdAppList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathNonStdAppList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathExp(b, l + 1, -1);
    p = r; // pin = 1
    r = r && MathNonStdAppList_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' MathExp)*
  private static boolean MathNonStdAppList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathNonStdAppList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathNonStdAppList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathNonStdAppList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathExp
  private static boolean MathNonStdAppList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathNonStdAppList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathSymbolExp
  static boolean MathOpSymbolName(PsiBuilder b, int l) {
    return MathSymbolExp(b, l + 1);
  }

  /* ********************************************************** */
  // MathBracketName MathNonStdAppList MathBracketName
  static boolean MathOutfixAppList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathOutfixAppList")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathBracketName(b, l + 1);
    r = r && MathNonStdAppList(b, l + 1);
    r = r && MathBracketName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathBracketName '(' MathDefnParamList ')' MathBracketName (':'|'ː') MathExp
  public static boolean MathOutfixDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathOutfixDefnSig")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_OUTFIX_DEFN_SIG, "<math outfix defn sig>");
    r = MathBracketName(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, LPAREN));
    r = p && report_error_(b, MathDefnParamList(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    r = p && report_error_(b, MathBracketName(b, l + 1)) && r;
    r = p && report_error_(b, MathOutfixDefnSig_5(b, l + 1)) && r;
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ':'|'ː'
  private static boolean MathOutfixDefnSig_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathOutfixDefnSig_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, TRICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '(' MathVarDecl ')' MathBracketName '(' MathVarDecl ')' MathBracketName (':'|'ː') MathExp
  public static boolean MathPostfixDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPostfixDefnSig")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && MathVarDecl(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && MathBracketName(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && MathVarDecl(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && MathBracketName(b, l + 1);
    r = r && MathPostfixDefnSig_8(b, l + 1);
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, m, MATH_POSTFIX_DEFN_SIG, r);
    return r;
  }

  // ':'|'ː'
  private static boolean MathPostfixDefnSig_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPostfixDefnSig_8")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, TRICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathPrefixNameList MathDefinitionParams? (':'|'ː') MathExp
  public static boolean MathPrefixDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSig")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_PREFIX_DEFN_SIG, "<math prefix defn sig>");
    r = MathPrefixNameList(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, MathPrefixDefnSig_1(b, l + 1));
    r = p && report_error_(b, MathPrefixDefnSig_2(b, l + 1)) && r;
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // MathDefinitionParams?
  private static boolean MathPrefixDefnSig_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSig_1")) return false;
    MathDefinitionParams(b, l + 1);
    return true;
  }

  // ':'|'ː'
  private static boolean MathPrefixDefnSig_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSig_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, TRICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathPrefixDefnSig (',' MathPrefixDefnSig)*
  static boolean MathPrefixDefnSigs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSigs")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathPrefixDefnSig(b, l + 1);
    r = r && MathPrefixDefnSigs_1(b, l + 1);
    exit_section_(b, l, m, r, false, CategoricalSigListRec_parser_);
    return r;
  }

  // (',' MathPrefixDefnSig)*
  private static boolean MathPrefixDefnSigs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSigs_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathPrefixDefnSigs_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathPrefixDefnSigs_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathPrefixDefnSig
  private static boolean MathPrefixDefnSigs_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSigs_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathPrefixDefnSig(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(':'|'ː'|';'|'(')
  static boolean MathPrefixListRec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixListRec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !MathPrefixListRec_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ':'|'ː'|';'|'('
  private static boolean MathPrefixListRec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixListRec_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, TRICOLON);
    if (!r) r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, LPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathSymbolName (',' MathSymbolName)*
  static boolean MathPrefixNameList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixNameList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathSymbolName(b, l + 1);
    p = r; // pin = 1
    r = r && MathPrefixNameList_1(b, l + 1);
    exit_section_(b, l, m, r, p, MathPrefixListRec_parser_);
    return r || p;
  }

  // (',' MathSymbolName)*
  private static boolean MathPrefixNameList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixNameList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathPrefixNameList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathPrefixNameList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathSymbolName
  private static boolean MathPrefixNameList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixNameList_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && MathSymbolName(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '::' MathSymbolName
  public static boolean MathQualifiedReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathQualifiedReferenceExp")) return false;
    if (!nextTokenIs(b, COLON_COLON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, MATH_REFERENCE_EXP, null);
    r = consumeToken(b, COLON_COLON);
    r = r && MathSymbolName(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (EFORALL|'∃'|'∀') MathVarDeclGroup ',' MathAssertionExp
  public static boolean MathQuantifiedExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathQuantifiedExp")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _COLLAPSE_, MATH_QUANTIFIED_EXP, "<math quantified exp>");
    r = MathQuantifiedExp_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, MathVarDeclGroup(b, l + 1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && MathAssertionExp(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // EFORALL|'∃'|'∀'
  private static boolean MathQuantifiedExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathQuantifiedExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EFORALL);
    if (!r) r = consumeToken(b, EXISTS);
    if (!r) r = consumeToken(b, FORALL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Recognition' MathAssertionExp ';'
  public static boolean MathRecognitionDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathRecognitionDecl")) return false;
    if (!nextTokenIs(b, RECOGNITION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_RECOGNITION_DECL, null);
    r = consumeToken(b, RECOGNITION);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MathSymbolName
  public static boolean MathReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathReferenceExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_REFERENCE_EXP, "<math reference exp>");
    r = MathSymbolName(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('Chainable')? ('Valued')? ('Implicit')? ('Coercer')?
  // 'Definition'  MathDefnSig ('≜' MathAssertionExp)? ';'
  public static boolean MathStandardDefnDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_STANDARD_DEFN_DECL, "<math standard defn decl>");
    r = MathStandardDefnDecl_0(b, l + 1);
    r = r && MathStandardDefnDecl_1(b, l + 1);
    r = r && MathStandardDefnDecl_2(b, l + 1);
    r = r && MathStandardDefnDecl_3(b, l + 1);
    r = r && consumeToken(b, DEFINITION);
    p = r; // pin = 5
    r = r && report_error_(b, MathDefnSig(b, l + 1));
    r = p && report_error_(b, MathStandardDefnDecl_6(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ('Chainable')?
  private static boolean MathStandardDefnDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_0")) return false;
    consumeToken(b, CHAINABLE);
    return true;
  }

  // ('Valued')?
  private static boolean MathStandardDefnDecl_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_1")) return false;
    consumeToken(b, VALUED);
    return true;
  }

  // ('Implicit')?
  private static boolean MathStandardDefnDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_2")) return false;
    consumeToken(b, IMPLICIT);
    return true;
  }

  // ('Coercer')?
  private static boolean MathStandardDefnDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_3")) return false;
    consumeToken(b, COERCER);
    return true;
  }

  // ('≜' MathAssertionExp)?
  private static boolean MathStandardDefnDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_6")) return false;
    MathStandardDefnDecl_6_0(b, l + 1);
    return true;
  }

  // '≜' MathAssertionExp
  private static boolean MathStandardDefnDecl_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_6_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TRI_EQUALS);
    r = r && MathAssertionExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // backslash identifier|identifier|int|symbol|mathsymbol|true|false
  public static boolean MathSymbolName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSymbolName")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_SYMBOL_NAME, "<math symbol name>");
    r = parseTokens(b, 0, BACKSLASH, IDENTIFIER);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, SYMBOL);
    if (!r) r = consumeToken(b, MATHSYMBOL);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('Corollary'|'Theorem') identifier ':' MathAssertionExp ';'
  public static boolean MathTheoremDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathTheoremDecl")) return false;
    if (!nextTokenIs(b, "<math theorem decl>", COROLLARY, THEOREM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_THEOREM_DECL, "<math theorem decl>");
    r = MathTheoremDecl_0(b, l + 1);
    r = r && consumeTokens(b, 1, IDENTIFIER, COLON);
    p = r; // pin = 2
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // 'Corollary'|'Theorem'
  private static boolean MathTheoremDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathTheoremDecl_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COROLLARY);
    if (!r) r = consumeToken(b, THEOREM);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathVarDef (':'|'ː') MathExp
  public static boolean MathVarDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDecl")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_VAR_DECL, "<math var decl>");
    r = MathVarDef(b, l + 1);
    r = r && MathVarDecl_1(b, l + 1);
    p = r; // pin = 2
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ':'|'ː'
  private static boolean MathVarDecl_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDecl_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, TRICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathVarDeclList (':'|'ː') MathExp
  public static boolean MathVarDeclGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclGroup")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_VAR_DECL_GROUP, "<math var decl group>");
    r = MathVarDeclList(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, MathVarDeclGroup_1(b, l + 1));
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ':'|'ː'
  private static boolean MathVarDeclGroup_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclGroup_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, TRICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathVarDef &(!(')')) (',' MathVarDef)*
  static boolean MathVarDeclList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathVarDef(b, l + 1);
    p = r; // pin = 1
    r = r && MathVarDeclList_1(b, l + 1);
    r = r && MathVarDeclList_2(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // &(!(')'))
  private static boolean MathVarDeclList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = MathVarDeclList_1_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !(')')
  private static boolean MathVarDeclList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' MathVarDef)*
  private static boolean MathVarDeclList_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathVarDeclList_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathVarDeclList_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathVarDef
  private static boolean MathVarDeclList_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathVarDef(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathSymbolName
  public static boolean MathVarDef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDef")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_VAR_DEF, "<math var def>");
    r = MathSymbolName(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PrecisModuleDecl
  //       | RealizationModuleDecl
  static boolean ModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleDecl")) return false;
    if (!nextTokenIs(b, "", PRECIS, REALIZATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PrecisModuleDecl(b, l + 1);
    if (!r) r = RealizationModuleDecl(b, l + 1);
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
  // ParameterMode ParamDefinitionListNoPin
  public static boolean ParamDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDecl")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PARAM_DECL, "<param decl>");
    r = ParameterMode(b, l + 1);
    p = r; // pin = 1
    r = r && ParamDefinitionListNoPin(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // identifier
  public static boolean ParamDef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDef")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, PARAM_DEF, r);
    return r;
  }

  /* ********************************************************** */
  // ParamDef &(!(';'|')')) (',' ParamDef)*
  static boolean ParamDefinitionListNoPin(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = ParamDef(b, l + 1);
    p = r; // pin = 1
    r = r && ParamDefinitionListNoPin_1(b, l + 1);
    r = r && ParamDefinitionListNoPin_2(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // &(!(';'|')'))
  private static boolean ParamDefinitionListNoPin_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = ParamDefinitionListNoPin_1_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !(';'|')')
  private static boolean ParamDefinitionListNoPin_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !ParamDefinitionListNoPin_1_0_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ';'|')'
  private static boolean ParamDefinitionListNoPin_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' ParamDef)*
  private static boolean ParamDefinitionListNoPin_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ParamDefinitionListNoPin_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ParamDefinitionListNoPin_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' ParamDef
  private static boolean ParamDefinitionListNoPin_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && ParamDef(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(')'|';')
  static boolean ParamListRec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamListRec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !ParamListRec_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ')'|';'
  private static boolean ParamListRec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamListRec_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RPAREN);
    if (!r) r = consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // alters|updates|clears|restores|preserves|replaces|evaluates|identifier
  public static boolean ParameterMode(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterMode")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAMETER_MODE, "<parameter mode>");
    r = consumeToken(b, ALTERS);
    if (!r) r = consumeToken(b, UPDATES);
    if (!r) r = consumeToken(b, CLEARS);
    if (!r) r = consumeToken(b, RESTORES);
    if (!r) r = consumeToken(b, PRESERVES);
    if (!r) r = consumeToken(b, REPLACES);
    if (!r) r = consumeToken(b, EVALUATES);
    if (!r) r = consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PrecisItem*
  public static boolean PrecisBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisBlock")) return false;
    Marker m = enter_section_(b, l, _NONE_, PRECIS_BLOCK, "<precis block>");
    int c = current_position_(b);
    while (true) {
      if (!PrecisItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PrecisBlock", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, true, false, ItemsRecover_parser_);
    return true;
  }

  /* ********************************************************** */
  // MathStandardDefnDecl
  //           | MathTheoremDecl
  //           | MathRecognitionDecl
  //           | MathCategoricalDefnDecl
  //           | MathInductiveDefnDecl
  static boolean PrecisItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisItem")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathStandardDefnDecl(b, l + 1);
    if (!r) r = MathTheoremDecl(b, l + 1);
    if (!r) r = MathRecognitionDecl(b, l + 1);
    if (!r) r = MathCategoricalDefnDecl(b, l + 1);
    if (!r) r = MathInductiveDefnDecl(b, l + 1);
    exit_section_(b, l, m, r, false, PrecisItemRecover_parser_);
    return r;
  }

  /* ********************************************************** */
  // !('Definition'|'Implicit'|'Theorem'|'Corollary'|'Categorical'|'Inductive'|'Recognition'|'Chainable'|'Coercer'|'Valued'|end)
  static boolean PrecisItemRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisItemRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !PrecisItemRecover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // 'Definition'|'Implicit'|'Theorem'|'Corollary'|'Categorical'|'Inductive'|'Recognition'|'Chainable'|'Coercer'|'Valued'|end
  private static boolean PrecisItemRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisItemRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DEFINITION);
    if (!r) r = consumeToken(b, IMPLICIT);
    if (!r) r = consumeToken(b, THEOREM);
    if (!r) r = consumeToken(b, COROLLARY);
    if (!r) r = consumeToken(b, CATEGORICAL);
    if (!r) r = consumeToken(b, INDUCTIVE);
    if (!r) r = consumeToken(b, RECOGNITION);
    if (!r) r = consumeToken(b, CHAINABLE);
    if (!r) r = consumeToken(b, COERCER);
    if (!r) r = consumeToken(b, VALUED);
    if (!r) r = consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Precis' identifier (extends ModuleIdentifierSpec)? ';'
  // UsesList?
  // PrecisBlock
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
    r = p && report_error_(b, PrecisBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeTokens(b, -1, END, IDENTIFIER, SEMICOLON)) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (extends ModuleIdentifierSpec)?
  private static boolean PrecisModuleDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl_2")) return false;
    PrecisModuleDecl_2_0(b, l + 1);
    return true;
  }

  // extends ModuleIdentifierSpec
  private static boolean PrecisModuleDecl_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTENDS);
    r = r && ModuleIdentifierSpec(b, l + 1);
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
  // RealizItem*
  public static boolean RealizBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizBlock")) return false;
    Marker m = enter_section_(b, l, _NONE_, REALIZ_BLOCK, "<realiz block>");
    int c = current_position_(b);
    while (true) {
      if (!RealizItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "RealizBlock", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, true, false, ItemsRecover_parser_);
    return true;
  }

  /* ********************************************************** */
  // MathStandardDefnDecl
  static boolean RealizItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizItem")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathStandardDefnDecl(b, l + 1);
    exit_section_(b, l, m, r, false, ImplItemRecover_parser_);
    return r;
  }

  /* ********************************************************** */
  // '(' ImplModuleParamList ')'
  public static boolean RealizModuleParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizModuleParameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, REALIZ_MODULE_PARAMETERS, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, ImplModuleParamList(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'Realization' identifier RealizModuleParameters? for ModuleIdentifierSpec (of ModuleIdentifierSpec)? ';'
  // UsesList?
  // RequiresClause?
  // RealizBlock
  // end identifier ';'
  public static boolean RealizationModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizationModuleDecl")) return false;
    if (!nextTokenIs(b, REALIZATION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, REALIZATION_MODULE_DECL, null);
    r = consumeTokens(b, 2, REALIZATION, IDENTIFIER);
    p = r; // pin = 2
    r = r && report_error_(b, RealizationModuleDecl_2(b, l + 1));
    r = p && report_error_(b, consumeToken(b, FOR)) && r;
    r = p && report_error_(b, ModuleIdentifierSpec(b, l + 1)) && r;
    r = p && report_error_(b, RealizationModuleDecl_5(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, RealizationModuleDecl_7(b, l + 1)) && r;
    r = p && report_error_(b, RealizationModuleDecl_8(b, l + 1)) && r;
    r = p && report_error_(b, RealizBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeTokens(b, -1, END, IDENTIFIER, SEMICOLON)) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // RealizModuleParameters?
  private static boolean RealizationModuleDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizationModuleDecl_2")) return false;
    RealizModuleParameters(b, l + 1);
    return true;
  }

  // (of ModuleIdentifierSpec)?
  private static boolean RealizationModuleDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizationModuleDecl_5")) return false;
    RealizationModuleDecl_5_0(b, l + 1);
    return true;
  }

  // of ModuleIdentifierSpec
  private static boolean RealizationModuleDecl_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizationModuleDecl_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OF);
    r = r && ModuleIdentifierSpec(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // UsesList?
  private static boolean RealizationModuleDecl_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizationModuleDecl_7")) return false;
    UsesList(b, l + 1);
    return true;
  }

  // RequiresClause?
  private static boolean RealizationModuleDecl_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RealizationModuleDecl_8")) return false;
    RequiresClause(b, l + 1);
    return true;
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
  // requires MathAssertionExp (EntailsClause)? ';'
  public static boolean RequiresClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RequiresClause")) return false;
    if (!nextTokenIs(b, REQUIRES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, REQUIRES_CLAUSE, null);
    r = consumeToken(b, REQUIRES);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && report_error_(b, RequiresClause_2(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (EntailsClause)?
  private static boolean RequiresClause_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RequiresClause_2")) return false;
    RequiresClause_2_0(b, l + 1);
    return true;
  }

  // (EntailsClause)
  private static boolean RequiresClause_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RequiresClause_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EntailsClause(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathVarDeclGroup ';'
  static boolean ResMathCartVarGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ResMathCartVarGroup")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = MathVarDeclGroup(b, l + 1);
    p = r; // pin = 1
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'type' identifier
  public static boolean TypeParamDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeParamDecl")) return false;
    if (!nextTokenIs(b, TYPE_PARAM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 2, TYPE_PARAM, IDENTIFIER);
    exit_section_(b, m, TYPE_PARAM_DECL, r);
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

  /* ********************************************************** */
  // Expression root: MathExp
  // Operator priority table:
  // 0: BINARY(MathInfixApplyExp)
  // 1: POSTFIX(MathPrefixApplyExp)
  // 2: POSTFIX(MathMixfixApplyExp)
  // 3: ATOM(MathNestedExp)
  // 4: ATOM(MathIncomingExp) ATOM(MathSymbolExp) BINARY(MathSelectorExp) ATOM(MathLambdaExp)
  //    ATOM(MathAlternativeExp) BINARY(MathClssftnAssrtExp) ATOM(MathOutfixApplyExp) ATOM(MathCartProdExp)
  //    PREFIX(MathBigUnionExp)
  public static boolean MathExp(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "MathExp")) return false;
    addVariant(b, "<math exp>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<math exp>");
    r = MathNestedExp(b, l + 1);
    if (!r) r = MathIncomingExp(b, l + 1);
    if (!r) r = MathSymbolExp(b, l + 1);
    if (!r) r = MathLambdaExp(b, l + 1);
    if (!r) r = MathAlternativeExp(b, l + 1);
    if (!r) r = MathOutfixApplyExp(b, l + 1);
    if (!r) r = MathCartProdExp(b, l + 1);
    if (!r) r = MathBigUnionExp(b, l + 1);
    p = r;
    r = r && MathExp_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean MathExp_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "MathExp_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && MathOpSymbolName(b, l + 1)) {
        r = MathExp(b, l, 0);
        exit_section_(b, l, m, MATH_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 1 && MathFunctionAppList(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, MATH_PREFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 2 && MathMixfixApplyExp_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, MATH_MIXFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 4 && consumeTokenSmart(b, DOT)) {
        r = MathExp(b, l, 4);
        exit_section_(b, l, m, MATH_SELECTOR_EXP, r, true, null);
      }
      else if (g < 4 && MathClssftnAssrtExp_0(b, l + 1)) {
        r = MathExp(b, l, 4);
        exit_section_(b, l, m, MATH_CLSSFTN_ASSRT_EXP, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // MathBracketName MathNonStdAppList MathBracketName
  private static boolean MathMixfixApplyExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathMixfixApplyExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathBracketName(b, l + 1);
    r = r && MathNonStdAppList(b, l + 1);
    r = r && MathBracketName(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' MathAssertionExp ')'
  public static boolean MathNestedExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathNestedExp")) return false;
    if (!nextTokenIsSmart(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_NESTED_EXP, null);
    r = consumeTokenSmart(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // '#' MathSymbolExp
  public static boolean MathIncomingExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathIncomingExp")) return false;
    if (!nextTokenIsSmart(b, POUND)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_INCOMING_EXP, null);
    r = consumeTokenSmart(b, POUND);
    p = r; // pin = 1
    r = r && MathSymbolExp(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // MathReferenceExp MathQualifiedReferenceExp?
  public static boolean MathSymbolExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSymbolExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, MATH_REFERENCE_EXP, "<math symbol exp>");
    r = MathReferenceExp(b, l + 1);
    r = r && MathSymbolExp_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // MathQualifiedReferenceExp?
  private static boolean MathSymbolExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSymbolExp_1")) return false;
    MathQualifiedReferenceExp(b, l + 1);
    return true;
  }

  // 'λ' MathVarDecl ','  MathExp
  public static boolean MathLambdaExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathLambdaExp")) return false;
    if (!nextTokenIsSmart(b, LAMBDA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_LAMBDA_EXP, null);
    r = consumeTokenSmart(b, LAMBDA);
    p = r; // pin = 1
    r = r && report_error_(b, MathVarDecl(b, l + 1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // '{{' MathAlternativesList '}}'
  public static boolean MathAlternativeExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeExp")) return false;
    if (!nextTokenIsSmart(b, DBL_LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_ALTERNATIVE_EXP, null);
    r = consumeTokenSmart(b, DBL_LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, MathAlternativesList(b, l + 1));
    r = p && consumeToken(b, DBL_RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ':'|'ː'
  private static boolean MathClssftnAssrtExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathClssftnAssrtExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, COLON);
    if (!r) r = consumeTokenSmart(b, TRICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // MathOutfixAppList
  public static boolean MathOutfixApplyExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathOutfixApplyExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_OUTFIX_APPLY_EXP, "<math outfix apply exp>");
    r = MathOutfixAppList(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // 'Cart_Prod' ResMathCartVarGroup+  end
  public static boolean MathCartProdExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathCartProdExp")) return false;
    if (!nextTokenIsSmart(b, CART_PROD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MATH_CART_PROD_EXP, null);
    r = consumeTokenSmart(b, CART_PROD);
    p = r; // pin = 1
    r = r && report_error_(b, MathCartProdExp_1(b, l + 1));
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ResMathCartVarGroup+
  private static boolean MathCartProdExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathCartProdExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ResMathCartVarGroup(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!ResMathCartVarGroup(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathCartProdExp_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean MathBigUnionExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathBigUnionExp")) return false;
    if (!nextTokenIsSmart(b, BIGOPERATOR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathBigUnionExp_0(b, l + 1);
    p = r;
    r = p && MathExp(b, l, -1);
    exit_section_(b, l, m, MATH_BIG_UNION_EXP, r, p, null);
    return r || p;
  }

  // bigoperator MathVarDecl ','
  private static boolean MathBigUnionExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathBigUnionExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, BIGOPERATOR);
    r = r && MathVarDecl(b, l + 1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  final static Parser CategoricalSigListRec_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return CategoricalSigListRec(b, l + 1);
    }
  };
  final static Parser ImplItemRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ImplItemRecover(b, l + 1);
    }
  };
  final static Parser ItemsRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ItemsRecover(b, l + 1);
    }
  };
  final static Parser MathPrefixListRec_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return MathPrefixListRec(b, l + 1);
    }
  };
  final static Parser ParamListRec_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ParamListRec(b, l + 1);
    }
  };
  final static Parser PrecisItemRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return PrecisItemRecover(b, l + 1);
    }
  };
}
