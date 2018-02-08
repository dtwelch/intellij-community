package com.jetbrains.resolve;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.jetbrains.resolve.lexer.ResolveLexer;
import com.jetbrains.resolve.parser.ResParser;
import com.jetbrains.resolve.psi.ResFile;
import com.jetbrains.resolve.psi.ResTokenType;
import org.jetbrains.annotations.NotNull;

import static com.jetbrains.resolve.ResTypes.*;

/**
 * @author dtwelch
 */
public class ResolveParserDefinition implements ParserDefinition {

  public static final IFileElementType FILE = new IFileElementType(ResolveLanguage.INSTANCE);

  public static final IElementType LINE_COMMENT = new ResTokenType("RESOLVE_LINE_COMMENT");
  public static final IElementType MULTILINE_COMMENT = new ResTokenType("RESOLVE_MULTILINE_COMMENT");

  public static final IElementType WS = new ResTokenType("RESOLVE_WHITESPACE");
  public static final IElementType NLS = new ResTokenType("RESOLVE_WS_NEW_LINES");

  public static final TokenSet WHITESPACES = TokenSet.create(WS, NLS);
  public static final TokenSet COMMENTS = TokenSet.create(LINE_COMMENT, MULTILINE_COMMENT);
  public static final TokenSet STRING_LITERALS = TokenSet.create(STRING, CHAR);
  public static final TokenSet NUMBERS = TokenSet.create(INT);

  public static final TokenSet KEYWORDS = TokenSet.create(PRECIS, END, USES, EXTENDS, FROM, DEFINITION,
                                                          CART_PROD, OTHERWISE, IMPLICIT, IF, CHAINABLE);

  public static final TokenSet OPERATORS = TokenSet.create(POUND, SYMBOL, MATHSYMBOL, PRIME, COLON_EQUALS,
                                                           LBRACK, RBRACK, LANGLE, RANGLE, LCEIL, RCEIL, DBL_BAR,
                                                           BAR, COLON_EQUALS_COLON,
                                                           TRUE, FALSE);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new ResolveLexer();
  }

  @Override
  public PsiParser createParser(Project project) {
    return new ResParser();
  }

  /**
   * What is the IFileElementType of the root parse tree node? It is called from
   * {@link #createFile(FileViewProvider)} at least.
   */
  @NotNull
  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  /**
   * "Tokens of those types are automatically skipped by PsiBuilder." This apparently applies to this method,
   * {@link #getCommentTokens()}, and {@link #getStringLiteralElements()}.
   */
  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return WHITESPACES;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return COMMENTS;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return STRING_LITERALS;
  }

  /**
   * Convert from <em>internal</em> parse node (AST they call it) to final PSI node. This converts only internal rule
   * nodes apparently, not leaf nodes. Leaves are just tokens I guess.
   * <p>
   * If you don't care to distinguish PSI nodes by type, it is sufficient to create a {@link ASTWrapperPsiElement}
   * around the parse tree node ({@link ASTNode} in Jetbrains speak).
   */
  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    return Factory.createElement(node);
  }

  /**
   * Create the root of your PSI tree (a {@link PsiFile}).
   * <p>
   * From IntelliJ IDEA Architectural Overview: "A PSI (Program Structure Interface) file is the root of a structure
   * representing the contents of a file as a hierarchy of elements in a particular programming language."
   * <p>
   * Psi based File is to be distinguished from a {@link com.intellij.lang.FileASTNode}, which is a parse
   * tree node that eventually becomes a {@link PsiFile}. From this, we can get it back via: {@link PsiFile#getNode}.
   */
  @Override
  public PsiFile createFile(FileViewProvider viewProvider) {
    return new ResFile(viewProvider);
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return SpaceRequirements.MAY;
  }
}
