package com.jetbrains.resolve.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.psi.ResMathSymbolName;
import com.jetbrains.resolve.psi.ResReferenceExpBase;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ResolveCompletionContributor extends CompletionContributor {

  public ResolveCompletionContributor() {
    extend(CompletionType.BASIC, referenceExp(), new ResolveReferenceCompletionProvider());
    extend(CompletionType.BASIC, mathReferenceExp(), new ResolveReferenceCompletionProvider());
  }

  private static PsiElementPattern.Capture<PsiElement> referenceExp() {
    return psiElement().withParent(ResReferenceExpBase.class);
  }

  private static PsiElementPattern.Capture<PsiElement> mathReferenceExp() {
    return psiElement().withParent(psiElement(ResMathSymbolName.class).withParent(ResReferenceExpBase.class));
  }

  public boolean invokeAutoPopup(@NotNull PsiElement position, char typeChar) {
    return typeChar == ':' && position.getNode().getElementType() == ResTypes.COLON;
  }
}
