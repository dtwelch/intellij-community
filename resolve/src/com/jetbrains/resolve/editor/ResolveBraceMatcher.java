package com.jetbrains.resolve.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.ResolveParserDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolveBraceMatcher implements PairedBraceMatcher {

  private static final BracePair[] PAIRS = new BracePair[]{
    new BracePair(ResTypes.LPAREN, ResTypes.RPAREN, false),
    new BracePair(ResTypes.LBRACE, ResTypes.RBRACE, false),
  };

  @Override
  public BracePair[] getPairs() {
    return PAIRS;
  }

  @Override
  public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType type) {
    return ResolveParserDefinition.COMMENTS.contains(type)
           || ResolveParserDefinition.WHITESPACES.contains(type)
           || type == ResTypes.SEMICOLON
           || type == ResTypes.COMMA
           || type == ResTypes.RPAREN
           || type == ResTypes.RBRACE
           || null == type;
  }

  @Override
  public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
    return openingBraceOffset;
  }
}
