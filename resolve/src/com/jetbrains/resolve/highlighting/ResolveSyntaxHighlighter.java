package com.jetbrains.resolve.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.ResolveParserDefinition;
import com.jetbrains.resolve.lexer.ResolveLexer;
import org.jetbrains.annotations.NotNull;

import static com.jetbrains.resolve.highlighting.ResolveSyntaxHighlightingColors.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A highlighter is really just a mapping from token type to some text attributes using
 * {@link #getTokenHighlights(IElementType)}. The reason that it returns an array, TextAttributesKey[], is that you
 * might want to mix the attributes of a few known highlighters. A {@link TextAttributesKey} is just a name for that a
 * theme or IDE skin can set. For example, {@link com.intellij.openapi.editor.DefaultLanguageHighlighterColors#KEYWORD}
 * is the key that maps to what identifiers look like in the editor. To change it, see dialog:
 * Editor > Colors & Fonts > Language Defaults.
 * <p>
 * From <a href="http://www.jetbrains.org/intellij/sdk/docs/reference_guide/custom_language_support/syntax_highlighting_and_error_highlighting.html">doc</a>:
 * "The mapping of the TextAttributesKey to specific attributes used in an editor is defined by the EditorColorsScheme
 * class, and can be configured by the user if the jetbrains provides an appropriate configuration interface.
 * ...
 * The syntax highlighter returns the {@link TextAttributesKey} instances for each token type which needs special
 * highlighting. For highlighting lexer errors, the standard TextAttributesKey for bad characters
 * {@code HighlighterColors.BAD_CHARACTER} can be used."
 *
 * @since 0.0.1
 */
public class ResolveSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<>();

    static {
        fillMap(ATTRIBUTES, LINE_COMMENT, ResolveParserDefinition.LINE_COMMENT);
        fillMap(ATTRIBUTES, BLOCK_COMMENT, ResolveParserDefinition.MULTILINE_COMMENT);
        fillMap(ATTRIBUTES, PARENTHESES, ResTypes.LPAREN, ResTypes.RPAREN);
        fillMap(ATTRIBUTES, BRACES, ResTypes.LBRACE, ResTypes.RBRACE, ResTypes.DBL_LBRACE, ResTypes.DBL_RBRACE);
        fillMap(ATTRIBUTES, BAD_CHARACTER, TokenType.BAD_CHARACTER);
        fillMap(ATTRIBUTES, IDENTIFIER, ResTypes.IDENTIFIER);
        fillMap(ATTRIBUTES, DOT, ResTypes.DOT);
        fillMap(ATTRIBUTES, SEMICOLON, ResTypes.SEMICOLON);
        fillMap(ATTRIBUTES, COMMA, ResTypes.COMMA);
        //fillMap(ATTRIBUTES, ResolveParserDefinition.PARAMETER_MODES, PARAMETER_MODE);
        //fillMap(ATTRIBUTES, ResolveParserDefinition.OPERATORS, OPERATOR);
        fillMap(ATTRIBUTES, ResolveParserDefinition.KEYWORDS, KEYWORD);
        fillMap(ATTRIBUTES, ResolveParserDefinition.NUMBERS, NUMBER);
        fillMap(ATTRIBUTES, ResolveParserDefinition.STRING_LITERALS, STRING);
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new ResolveLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(ATTRIBUTES.get(tokenType));
    }
}