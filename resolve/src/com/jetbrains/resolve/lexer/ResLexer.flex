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

LETTER  = [:letter:] | "_"
DIGIT   =  [:digit:]

INT_DIGIT     = [0-9]
NUM_INT       = "0" | ([1-9] {INT_DIGIT}*)

IDENT   = {LETTER} ({LETTER} | {DIGIT} )*
MSYM    = ({U_ARROW} | {U_LETTER} | {U_OPERATOR}  | {U_RELATION} | {U_GREEK})

U_ARROW       = ("⟵"|"⟸"|"⟶"|"⟹"|"⟷"|"⟺"|"↩"|"↪"|"↽"|
                 "⇁"|"↼"|"⇀"|"⇃"|"⇂"|"↿"|"↾"|"↑"|"⇑"|"↓"|"⇓"|"↕"|"⇕")

U_LETTER      = ("ℕ"|"ℤ"|"ℂ"|"𝔹"|"ℚ"|"ℝ"|"𝓟"|"℘")

U_BIGOPERATOR = ("⋀"|"⋁"|"⋂"|"⋃"|"⨄"|"⨁"|"⨂"|"⨀"|"∑"|"∏")

U_OPERATOR    = ("∧"|"∨"|"¬"|"∩"|"∪"|"⊎"|"⊕"|"⊗"|"⊙"|"⊖"|"∝"|"×"|
                 "⋆"|"∙"|"∘"|"∼"|"⋈"|"⋉"|"⋊"|"∸")

U_RELATION    = ("≤"|"≥"|"≠"|"≪"|"≫"|"≲"|"≳"|"∈"|"∉"|"⊂"|"⊃"|"⊆"|
                 "⊇"|"≐"|"≃"|"≈"|"≡"|"≼"|"≽"|"⊲"|"⊳"|"⊴"|"⊵")

U_GREEK       = [\u0370-\u03FF]

%%
<YYINITIAL> {

{WS}                                    { return WS; }
{NL}+                                   { return NLS; }
{LINE_COMMENT}                          { return LINE_COMMENT; }
{MULTILINE_COMMENT}                     { return MULTILINE_COMMENT; }

"'\\'"                                  { return BAD_CHARACTER; }
"'" [^\\] "'"                           { return CHAR; }
"'" \n "'"                              { return CHAR; }
"'\\" [abfnrtv\\\'] "'"                 { return CHAR; }

// Punctuation
"#"                                     { return POUND; }
"."                                     { return DOT; }
"("                                     { return LPAREN; }
")"                                     { return RPAREN; }
"′"                                      { return PRIME; }

":"                                     { return COLON; }
"ː"                                     { return TRICOLON; }
"::"                                    { return COLON_COLON; }
";"                                     { return SEMICOLON; }
","                                     { return COMMA; }

// Brackets
"∥"                                     { return DBL_BAR; }

"⟨"                                     { return LANGLE; }
"⟩"                                     { return RANGLE; }

"⌈"                                     { return LCEIL; }
"⌉"                                     { return RCEIL; }

"["                                     { return LBRACK; }
"]"                                     { return RBRACK; }

"{{"                                    { return DBL_LBRACE; }
"}}"                                    { return DBL_RBRACE; }

"{"                                     { return LBRACE; }
"}"                                     { return RBRACE; }

"|"                                     { return BAR; }

// Builtin
"∃"                                     { return EXISTS; }
"∀"                                     { return FORALL; }
"λ"                                     { return LAMBDA; }
"≜"                                     { return TRI_EQUALS; }
":="                                    { return COLON_EQUALS; }
":=:"                                   { return COLON_EQUALS_COLON; }

// Keywords
"by"                                    { return BY; }

"Cart_Prod"                             { return CART_PROD; }
"Categorical"                           { return CATEGORICAL; }
"changing"                              { return CHANGING; }
"Chainable"                             { return CHAINABLE; }
"Concept"                               { return CONCEPT; }
"constraints"                           { return CONSTRAINTS; }
"conventions"                           { return CONVENTIONS; }
"Valued"                                { return VALUED; }
"Corollary"                             { return COROLLARY; }
"correspondence"                        { return CORRESPONDENCE; }

"do"                                    { return DO; }
"decreasing"                            { return DECREASING; }
"Definition"                            { return DEFINITION; }
"Def"                                   { return DEFINITION; }
"Defines"                               { return DEFINES; }

"else"                                  { return ELSE; }
"Extension"                             { return EXTENSION; }
"Enhancement"                           { return ENHANCEMENT; }
"enhanced"                              { return ENHANCED; }
"end"                                   { return END;  }
"ensures"                               { return ENSURES; }
"exemplar"                              { return EXEMPLAR; }
"externally"                            { return EXTERNALLY; }

"Facility"                              { return FACILITY;  }
"false"                                 { return FALSE; }
"family"                                { return FAMILY; }
"for"                                   { return FOR; }
"from"                                  { return FROM; }

"if"                                    { return IF; }
"If"                                    { return PROG_IF; }
"Implicit"                              { return IMPLICIT; }
"initialization"                        { return INITIALIZATION; }
"Inductive"                             { return INDUCTIVE; }
"is"                                    { return IS; }

"realized"                              { return REALIZED; }
"Realization"                           { return REALIZATION; }

"maintaining"                           { return MAINTAINING; }
"modeled"                               { return MODELED; }

"Operation"                             { return OPERATION; }
"otherwise"                             { return OTHERWISE; }
"of"                                    { return OF; }

{IDENT}                                 { return IDENTIFIER; }
{NUM_INT}                               { return INT; }
.                                       { return BAD_CHARACTER; }
}