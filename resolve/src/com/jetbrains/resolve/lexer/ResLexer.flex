package com.jetbrains.resolve.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.jetbrains.resolve.ResTypes;
import java.util.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.jetbrains.resolve.ResolveParserDefinition.*;

%%

%{
  public _ResLexer() {
    this((java.io.Reader)null);
  }
%}

%class _ResLexer
%implements FlexLexer, ResTypes
%unicode

%function advance
%type IElementType

%eof{
  return;
%eof}

NL = \R             //newline
WS = [ \t\f]        //whitespaces

LINE_COMMENT      = "//" [^\r\n]*
MULTILINE_COMMENT = "/*" ( ([^"*"]|[\r\n])* ("*"+ [^"*""/"] )? )* ("*" | "*"+"/")?

LETTER  = [:jletter:] | "_"
DIGIT   =  [:digit:]

INT_DIGIT     = [0-9]
NUM_INT       = "0" | ([1-9] {INT_DIGIT}*)

MATH_NON_IDENTIFIER_SYM    = ({U_ARROW} | {U_LETTER} | {U_OPERATOR} | {U_RELATION} | {U_GREEK})
IDENT   = {LETTER} ({LETTER} | {DIGIT} )*

U_ARROW       = ("⟵"|"⟸"|"⟶"|"⟹"|"⟷"|"⟺"|"↩"|"↪"|"↽"|
                 "⇁"|"↼"|"⇀"|"⇃"|"⇂"|"↿"|"↾"|"↑"|"⇑"|"↓"|"⇓"|"↕"|"⇕")

U_LETTER      = ("ℕ"|"ℤ"|"ℂ"|"𝔹"|"ℚ"|"ℝ"|"𝒫"|"℘")

U_OPERATOR    = ("¬"|"∩"|"∪"|"⊎"|"⊕"|"⊗"|"⊙"|"∅"|"⊖"|"∝"|"×"|
                 "⋆"|"∙"|"∘"|"∼"|"⋈"|"⋉"|"⋊"|"∸")

//U_BIGOPERATOR = ("⋀"|"⋁"|"⋂"|"⋃"|"⨄"|"⨁"|"⨂"|"⨀"|"∑"|"∏")

U_RELATION    = ("≤"|"≥"|"≪"|"≫"|"≲"|"≳"|"∈"|"∉"|"⊂"|"⊃"|"⊆"|
                 "⊇"|"≐"|"≃"|"≈"|"≡"|"≼"|"≽"|"⊲"|"⊳"|"⊴"|"⊵")
U_GREEK       = ("α"|"β"|"γ"|"δ"|"ε"|"ζ"|"η"|"θ"|"ι"|"κ"|"μ"|"ν"|"ξ"|
                 "ο"|"π"|"ρ"|"ς"|"σ"|"τ"|"υ"|"φ"|"χ"|"ψ"|"ω"|"Γ"|"Δ"|"Θ"|"Λ"|
                 "Ξ"|"Σ"|"Φ"|"Ψ"|"Ω")

STR     = "\""
STRING = {STR} ( [^\"\\\n\r] | "\\" ("\\" | {STR} | {ESCAPES} | [0-8xuU] ) )* {STR}?

ASCII = ("+" | "-" | "*" | "<" | ">" | "!" | "=" | "/" | "~")

//we can add more chars here relatively easily later.
SUBSCRIPT = ("₀" | "₁" | "₂" | "₊" | "₋")
MATH_IDENT = ({IDENT} | {MATH_NON_IDENTIFIER_SYM} | {ASCII})+ ({SUBSCRIPT})* ("'"|"`")*

ESCAPES = [abfnrtv]

%%
<YYINITIAL> {

{WS}                                    { return WS; }
{NL}+                                   { return NLS; }
{LINE_COMMENT}                          { return LINE_COMMENT; }
{MULTILINE_COMMENT}                     { return MULTILINE_COMMENT; }
{STRING}                                { return STRING; }

"'\\'"                                  { return BAD_CHARACTER; }
//"'" [^\\] "'"                           { return CHAR; }
//"'" \n "'"                              { return CHAR; }
//"'\\" [abfnrtv\\\'] "'"                 { return CHAR; }

// Punctuation
"#"                                     { return POUND; }
"."                                     { return DOT; }
"("                                     { return LPAREN; }
")"                                     { return RPAREN; }

":"                                     { return COLON; }
"::"                                    { return COLON_COLON; }

";"                                     { return SEMICOLON; }
","                                     { return COMMA; }
"(i.)"                                  { return IND_BASE; }
"(ii.)"                                 { return IND_HYPO; }

// Brackets
"∥"                                     { return DBL_BAR; }

"⟨"                                     { return LANGLE; }
"⟩"                                     { return RANGLE; }

"⌈"                                     { return LCEIL; }
"⌉"                                     { return RCEIL; }

"["                                     { return LBRACK; }
"]"                                     { return RBRACK; }

"|{"                                    { return PIECEWISE; }

"{"                                     { return LBRACE; }
"}"                                     { return RBRACE; }

"|"                                     { return BAR; }

// Builtin
"∃"                                     { return EXISTS; }
"∀"                                     { return FORALL; }
"λ"                                     { return LAMBDA; }
"≜"                                    { return TRIANGLEQ; }
"="                                     { return EQUALS; }
"≠"                                     { return NEQUALS; }  //non-ascii variant
"∧"                                     { return AND;}
"∨"                                     { return OR;}

":="                                    { return COLON_EQUALS; }
":=:"                                   { return COLON_EQUALS_COLON; }

// Keywords
"by"                                    { return BY; }

"Cart_Prod"                             { return CART_PROD; }
"Categorical"                           { return CATEGORICAL; }
"changing"                              { return CHANGING; }
"Concept"                               { return CONCEPT; }
"constraints"                           { return CONSTRAINTS; }
"conventions"                           { return CONVENTIONS; }
"Corollary"                             { return COROLLARY; }
"Coercer"                               { return COERCER; }
"correspondence"                        { return CORRESPONDENCE; }

"do"                                    { return DO; }
"decreasing"                            { return DECREASING; }
"Definition"                            { return DEFINITION; }
"Def"                                   { return DEFINITION; }

"else"                                  { return ELSE; }
"Enhancement"                           { return ENHANCEMENT; }
"enhanced"                              { return ENHANCED; }
"end"                                   { return END;  }
"ensures"                               { return ENSURES; }
"exemplar"                              { return EXEMPLAR; }
"externally"                            { return EXTERNALLY; }
"extends"                               { return EXTENDS; }

"Facility"                              { return FACILITY;  }
"false"                                 { return FALSE; }
"family"                                { return FAMILY; }
"for"                                   { return FOR; }
"from"                                  { return FROM; }

"if"                                    { return IF_MATH; }
"If"                                    { return IF_PROG; }
"Implicit"                              { return IMPLICIT; }
"initialization"                        { return INITIALIZATION; }
"Inductive"                             { return INDUCTIVE; }
"is"                                    { return IS; }

"Literal"                               { return LITERAL; }
"realized"                              { return REALIZED; }
"Realization"                           { return REALIZATION; }

"maintaining"                           { return MAINTAINING; }
"modeled"                               { return MODELED; }

"Operation"                             { return OPERATION; }
"otherwise"                             { return OTHERWISE; }
"of"                                    { return OF; }

"Procedure"                             { return PROCEDURE; }
"Precis"                                { return PRECIS; }

"Recursive"                             { return RECURSIVE; }
"Recognition"                           { return RECOGNITION; }
"Record"                                { return RECORD; }
"requires"                              { return REQUIRES; }

"then"                                  { return THEN; }
"true"                                  { return TRUE; }
"Theorem"                               { return THEOREM; }
"Type"                                  { return TYPE_FAMILY; }
"type"                                  { return TYPE_PARAM; }

"uses"                                  { return USES; }
"Valued"                                { return VALUED; }
"Var"                                   { return VAR; }
"While"                                 { return WHILE; }
"which_entails"                         { return WHICH_ENTAILS; }
"with"                                  { return WITH; }

// Parameter modes

"alters"                                { return ALTERS; }
"updates"                               { return UPDATES; }
"clears"                                { return CLEARS; }
"restores"                              { return RESTORES; }
"preserves"                             { return PRESERVES; }
"replaces"                              { return REPLACES; }
"evaluates"                             { return EVALUATES; }

{IDENT}                                 { return IDENTIFIER; }
{MATH_IDENT}                            { return MATHIDENTIFIER; }
{NUM_INT}                               { return INT; }
.                                       { return BAD_CHARACTER; }
}