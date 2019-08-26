package com.jetbrains.resolve.template;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.impl.source.tree.TreeUtil;
import com.intellij.psi.tree.TokenSet;
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

  public static class ResolvePrecisModuleContextType extends ResolveLiveTemplateContextType {
    protected ResolvePrecisModuleContextType() {
      super("RESOLVE_PRECIS_MODULE", "RESOLVE Precis module", ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return element.getParent().getParent() instanceof ResPrecisModuleDecl;
    }
  }

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
      super("RESOLVE_CONCEPT_MODULE", "RESOLVE Concept module", ResolveEverywhereContextType.class);
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
      return element.getParent().getParent() instanceof ResConceptEnhancementModuleDecl;
    }
  }

  public static class ResolveRealizationModuleContextType extends ResolveLiveTemplateContextType {
    protected ResolveRealizationModuleContextType() {
      super("RESOLVE_REALIZ_MODULE", "RESOLVE implementation module", ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return TreeUtil.findParent(element.getNode(), ResTypes.REALIZ_MODULE_PARAMETERS) == null &&
             element.getParent().getParent() instanceof ResRealizationModuleDecl;
    }
  }

  public static class ResolveRealizationParameterList extends ResolveLiveTemplateContextType {
    protected ResolveRealizationParameterList() {
      super("RESOLVE_REALIZ_PARAM_LIST", "RESOLVE realization parameters", ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return TreeUtil.findParent(element.getNode(), ResTypes.REALIZ_MODULE_PARAMETERS) != null &&
             element.getParent().getParent() instanceof ResRealizationModuleDecl;
    }
  }

  public static class ResolveCodeBlockContextType extends ResolveLiveTemplateContextType {
    protected ResolveCodeBlockContextType() {
      super("RESOLVE_CODE", "RESOLVE executable code block",
            ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return TreeUtil.findParent(element.getNode(),
                                 TokenSet.create(ResTypes.PROCEDURE_DECL,
                                                 ResTypes.OPERATION_PROCEDURE_DECL),
                                 TokenSet.create(ResTypes.REALIZ_BLOCK)) != null;
    }
  }

  public static class ResolveReprContextType extends ResolveLiveTemplateContextType {
    protected ResolveReprContextType() {
      super("RESOLVE_REPR", "RESOLVE representation type",
            ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return TreeUtil.findParent(element.getNode(),
                                 TokenSet.create(ResTypes.TYPE_REPR_DECL)) != null;
    }
  }

  public static class ResolveTypeModelContextType extends ResolveLiveTemplateContextType {
    protected ResolveTypeModelContextType() {
      super("RESOLVE_MODEL", "RESOLVE model type",
            ResolveEverywhereContextType.class);
    }

    @Override
    protected boolean isInContext(@NotNull PsiElement element) {
      return TreeUtil.findParent(element.getNode(),
                                 TokenSet.create(ResTypes.TYPE_MODEL_DECL)) != null;
    }
  }

}
