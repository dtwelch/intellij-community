package com.jetbrains.resolve.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.psi.ResMathSymbolName;
import com.jetbrains.resolve.psi.ResModuleDecl;
import com.jetbrains.resolve.psi.ResReferenceExpBase;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ResolveCompletionContributor extends CompletionContributor {

  public ResolveCompletionContributor() {
    extend(CompletionType.BASIC, referenceExp(), new ResolveReferenceCompletionProvider());
    //test this out with the other 4 modules to make sure this works correctly: q: how to references for qualifiers work then?
    //a: in the qualifier method that is called from the processResolveVariants method in the referenceExp classes
    //extend(CompletionType.BASIC, moduleHeaderReferenceExp(), new ResolveModuleHeaderReferenceProvider());
    extend(CompletionType.BASIC, mathReferenceExp(), new ResolveReferenceCompletionProvider());
  }

  /*private static PsiElementPattern.Capture<PsiElement> moduleHeaderReferenceExp() {
    return psiElement().withParent(psiElement(ResReferenceExpBase.class)
                                     .andNot(psiElement().afterSibling(psiElement(PsiErrorElement.class))
                                               .withParent(ResModuleDecl.class)));
  }*/

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
