package com.jetbrains.resolve.template;

import com.intellij.codeInsight.template.EverywhereContextType;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.jetbrains.resolve.ResTypes;
import org.jetbrains.annotations.NotNull;

public class ResolveEverywhereContextType extends ResolveLiveTemplateContextType {

    protected ResolveEverywhereContextType() {
        super("RESOLVE", "RESOLVE", EverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
        return !(element instanceof PsiComment ||
                 element instanceof LeafPsiElement &&
                 ((LeafPsiElement) element).getElementType() == ResTypes.STRING);
    }
}