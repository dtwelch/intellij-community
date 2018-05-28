package com.jetbrains.resolve.template;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiUtilCore;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.ResolveLanguage;
import com.jetbrains.resolve.highlighting.ResolveSyntaxHighlighter;
import com.jetbrains.resolve.psi.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResolveLiveTemplateContextType extends TemplateContextType {

  protected ResolveLiveTemplateContextType(@NotNull @NonNls String id,
                                           @NotNull String presentableName,
                                           @Nullable Class<? extends TemplateContextType> baseContextType) {
    super(id, presentableName, baseContextType);
  }

  public boolean isInContext(@NotNull PsiFile file, int offset) {
    if (PsiUtilCore.getLanguageAtOffset(file, offset).isKindOf(ResolveLanguage.INSTANCE)) {
      PsiElement element = getFirstCompositeElement(file.findElementAt(offset));
      return element != null && isInContext(element);
    }
    return false;
  }

  @Nullable
  private static PsiElement getFirstCompositeElement(@Nullable PsiElement at) {
    if (at instanceof PsiComment ||
        at instanceof LeafPsiElement && ((LeafPsiElement)at).getElementType() == ResTypes.STRING) {
      return at;
    }
    PsiElement result = at;
    while (result != null && (result instanceof PsiWhiteSpace || result.getChildren().length == 0)) {
      result = result.getParent();
    }
    return result;
  }

  protected abstract boolean isInContext(@NotNull PsiElement element);

  public SyntaxHighlighter createHighlighter() {
    return new ResolveSyntaxHighlighter();
  }

  // File

  public static class ResolveFileContextType extends ResolveLiveTemplateContextType {

    protected ResolveFileContextType() {
      super("RESOLVE_FILE", "RESOLVE file", ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return element.getParent() instanceof ResFile && !(element instanceof ResModuleDecl);
      //if the second condition is true, it means that a module has already
      //been inserted into the document, and we shouldn't suggest another -- say, when the user is typing
      //in the name.
    }
  }

  // Modules

  public static class ResolveFacilityModuleContextType extends ResolveLiveTemplateContextType {
    protected ResolveFacilityModuleContextType() {
      super("RESOLVE_FACILITY_MODULE", "RESOLVE Facility module", ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return element.getParent().getParent() instanceof ResFacilityModuleDecl;
    }
  }

  public static class ResolveConceptModuleContextType extends ResolveLiveTemplateContextType {
    protected ResolveConceptModuleContextType() {
      super("RESOLVE_CONCEPT_MODULE", "RESOLVE Facility module", ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return element.getParent().getParent() instanceof ResConceptModuleDecl;
    }
  }

  public static class ResolveEnhancementModuleContextType extends ResolveLiveTemplateContextType {
    protected ResolveEnhancementModuleContextType() {
      super("RESOLVE_ENHANCEMENT_MODULE", "RESOLVE enhancement module", ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return element.getParent().getParent() instanceof ResConceptExtensionModuleDecl;
    }
  }

  public static class ResolveRealizationModuleContextType extends ResolveLiveTemplateContextType {
    protected ResolveRealizationModuleContextType() {
      super("RESOLVE_REALIZ_MODULE", "RESOLVE implementation module", ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return element.getParent().getParent() instanceof ResConceptExtensionModuleDecl;
    }
  }
}
