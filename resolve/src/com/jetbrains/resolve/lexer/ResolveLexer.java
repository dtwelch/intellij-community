package com.jetbrains.resolve.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;
import com.jetbrains.resolve.ResolveParserDefinition;

public class ResolveLexer extends MergingLexerAdapter {
    public ResolveLexer() {
        super(new FlexAdapter(new _ResLexer()), TokenSet.orSet(ResolveParserDefinition.COMMENTS, ResolveParserDefinition.WHITESPACES));
    }
}
