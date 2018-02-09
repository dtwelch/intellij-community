package com.jetbrains.resolve.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.PsiElementPattern.Capture;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.psi.*;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ResolveKeywordCompletionContributor extends CompletionContributor implements DumbAware {
  public ResolveKeywordCompletionContributor() {

    extend(CompletionType.BASIC, usesPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "uses"));

    extend(CompletionType.BASIC, modulePattern(ResPrecisModuleDecl.class, ResPrecisBlock.class),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY,
                                                "Implicit", "Definition", "Def", "Theorem", "Corollary", "Inductive",
                                                "Recognition", "Categorical"));
  }

  private static Capture<PsiElement> usesPattern() {
    return onKeywordStartWithParent(psiElement(ResBlock.class)
                                      .withParent(ResModuleDecl.class)
                                      .andOr(psiElement().isFirstAcceptedChild(psiElement()),
                                             psiElement().afterSibling(psiElement(ResModuleIdentifier.class))));
  }

  private static Capture<PsiElement> onKeywordStartWithParent(Class<? extends PsiElement> parentClass) {
    return onKeywordStartWithParent(psiElement(parentClass));
  }

  private static Capture<PsiElement> onKeywordStartWithParent(Capture<? extends PsiElement> parentPattern) {
    return psiElement(ResTypes.IDENTIFIER).withParent(psiElement(PsiErrorElement.class).withParent(parentPattern));
  }

  private static Capture<PsiElement> modulePattern(Class<? extends ResModuleDecl> moduleType,
                                                   Class<? extends ResBlock> blockType) {
    return onKeywordStartWithParent(psiElement(blockType).withParent(moduleType));
  }
}
