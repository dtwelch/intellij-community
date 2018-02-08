package com.jetbrains.resolve.completion;

import com.intellij.codeInsight.completion.BasicInsertHandler;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.jetbrains.resolve.psi.ResMathDefnSig;
import com.jetbrains.resolve.psi.ResMathPrefixDefnSig;

public class MathSymbolInsertHandler extends BasicInsertHandler<LookupElement> {

    @Override
    public void handleInsert(InsertionContext context, LookupElement item) {
        PsiElement element = item.getPsiElement();
        if (!(element instanceof ResMathDefnSig)) return;
        ResMathDefnSig signature = (ResMathDefnSig) element;
        int paramsCount = signature.getParameters().size();

        InsertHandler<LookupElement> handler = new BasicInsertHandler<>();
        if (signature instanceof ResMathPrefixDefnSig && paramsCount != 0) {
            handler = ParenthesesInsertHandler.WITH_PARAMETERS;
        }
        handler.handleInsert(context, item);
    }
}
