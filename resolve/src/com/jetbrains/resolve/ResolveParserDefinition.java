package com.jetbrains.resolve;

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
import com.jetbrains.resolve.psi.ResTokenType;
import org.jetbrains.annotations.NotNull;


/**
 * @author dtwelch
 */
public class ResolveParserDefinition implements ParserDefinition {

  public static final IElementType LINE_COMMENT = new ResTokenType("RESOLVE_LINE_COMMENT");
  public static final IElementType MULTILINE_COMMENT = new ResTokenType("RESOLVE_MULTILINE_COMMENT");

  public static final IElementType WS = new ResTokenType("RESOLVE_WHITESPACE");
  public static final IElementType NLS = new ResTokenType("RESOLVE_WS_NEW_LINES");

  public static final TokenSet WHITESPACES = TokenSet.create(WS, NLS);
  public static final TokenSet COMMENTS = TokenSet.create(LINE_COMMENT, MULTILINE_COMMENT);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return null;
  }

  @Override
  public PsiParser createParser(Project project) {
    return null;
  }

  @Override
  public IFileElementType getFileNodeType() {
    return null;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return null;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return null;
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    return null;
  }

  @Override
  public PsiFile createFile(FileViewProvider viewProvider) {
    return null;
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return null;
  }
}
