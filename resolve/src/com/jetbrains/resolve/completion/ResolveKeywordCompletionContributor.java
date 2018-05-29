package com.jetbrains.resolve.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiElementPattern.Capture;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.util.ProcessingContext;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.psi.*;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

//For nice (fairly comprehensible) examples of PSI pattern methods being used, look at: PyKeywordCompletionContributor
public class ResolveKeywordCompletionContributor extends CompletionContributor implements DumbAware {
  public ResolveKeywordCompletionContributor() {
    extend(CompletionType.BASIC, usesPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "uses"));

    extend(CompletionType.BASIC, modulePattern(ResPrecisModuleDecl.class, ResPrecisBlock.class),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY,
                                                "Implicit", "Definition", "Def", "Theorem", "Corollary",
                                                "Inductive", "Recognition", "Categorical"));

    extend(CompletionType.BASIC, precisHeaderExtendsPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "extends"));

    extend(CompletionType.BASIC, categoricalForPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "for"));

    extend(CompletionType.BASIC, modulePattern(ResRealizationModuleDecl.class, ResRealizBlock.class),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "Definition"));

    extend(CompletionType.BASIC, modulePattern(ResConceptModuleDecl.class, ResConceptBlock.class),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY,
                                                "Definition", "Def", "Implicit", "constraints"));

    extend(CompletionType.BASIC, modulePattern(ResConceptEnhancementModuleDecl.class, ResConceptBlock.class),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "Definition", "Def", "Implicit"));

    extend(CompletionType.BASIC, parameterModePattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY,
                                                "evaluates", "updates", "alters", "clears", "preserves", "restores", "replaces"));

    extend(CompletionType.BASIC, recordTypePattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "Record"));

    extend(CompletionType.BASIC, typeParamPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "type"));

    extend(CompletionType.BASIC, statementPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "While", "If"));

    extend(CompletionType.BASIC, mathCartProdPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "Cart_Prod"));

    extend(CompletionType.BASIC, variablePattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "Var"));

    extend(CompletionType.BASIC, statementPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "While", "If"));

    extend(CompletionType.BASIC, moduleRequiresPattern(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "requires"));

    extend(CompletionType.BASIC, modelInitialization(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "initialization"));
    extend(CompletionType.BASIC, initializationEnsures(),
           new ResolveKeywordCompletionProvider(ResolveCompletionUtil.KEYWORD_PRIORITY, "ensures"));

  }

  //TODO: Redo this eventually, it doesn't work quite right yet.
  private static Capture<PsiElement> usesPattern() {
    return onKeywordStartWithParent(psiElement(ResBlock.class)
                                      .withParent(ResModuleDecl.class)
                                      .andOr(psiElement().withFirstNonWhitespaceChild(psiElement()),
                                             psiElement().afterSibling(psiElement(ResModuleIdentifier.class))));
  }

  private static Capture<PsiElement> recordTypePattern() {
    return psiElement(ResTypes.IDENTIFIER).withParent(psiElement(ResTypes.TYPE_REFERENCE_EXP)
                                                        .withParent(psiElement().withParent(ResTypeReprDecl.class)));
  }

  private static Capture<PsiElement> categoricalForPattern() {
    return psiElement(ResTypes.IDENTIFIER)
      .withParent(psiElement(ResTypes.MATH_SYMBOL_NAME)
                    .withParent(psiElement(ResTypes.MATH_PREFIX_DEFN_SIG)
                                  .withParent(psiElement(ResTypes.MATH_CATEGORICAL_DEFN_DECL))
                                  .afterSiblingSkipping(psiElement().whitespace(), psiElement(PsiErrorElement.class))));
  }

  private static Capture<? extends PsiElement> precisHeaderExtendsPattern() {
    return onKeywordStartWithParent(psiElement(ResBlock.class).with(
      new PatternCondition<PsiElement>("extendsKeyword") {
        @Override
        public boolean accepts(@NotNull PsiElement element, ProcessingContext context) {
          PsiElement x = element.getPrevSibling();
          if (x instanceof PsiWhiteSpace) {
            if (x.getPrevSibling() instanceof PsiErrorElement) {
              PsiErrorElement v = (PsiErrorElement)x.getPrevSibling();
              return v.getErrorDescription().contains("';' or extends expected");
            }
          }
          return false;
        }
      }));
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

  private static Capture<PsiElement> mathCartProdPattern() {
    return psiElement(ResTypes.IDENTIFIER)
      .withParent(psiElement(ResTypes.MATH_SYMBOL_NAME)
                    .withParent(psiElement(ResTypes.MATH_REFERENCE_EXP)));
  }

  private static Capture<PsiElement> moduleRequiresPattern() {
    return psiElement(ResTypes.IDENTIFIER)
      .withParent(psiElement(PsiErrorElement.class)
                    .withParent(ResBlock.class)
                    .atStartOf(psiElement(ResBlock.class)));
  }

  private static Capture<PsiElement> typeParamPattern() {
    return psiElement(ResTypes.IDENTIFIER).withParent(ResParameterMode.class).inside(ResSpecModuleParameters.class);
  }

  private static Capture<PsiElement> initializationEnsures() {
    return psiElement(ResTypes.IDENTIFIER)
      .withParent(psiElement(PsiErrorElement.class).afterLeaf(psiElement(ResTypes.INITIALIZATION)));
  }

  private static Capture<PsiElement> modelInitialization() {
    return psiElement(ResTypes.IDENTIFIER)
      .withParent(psiElement(PsiErrorElement.class)
                    .afterSibling(psiElement(ResTypeModelDecl.class)
                                    .withLastChild(psiElement().andOr(psiElement(ResExemplarDecl.class),
                                                                      psiElement(ResConstraintsClause.class)))));
  }

  private static Capture<PsiElement> statementPattern() {
    //IntellijIdeaRulezzz is what the psiElement(ResTypes.IDENTIFIER) bit with previous sibling = "::"
    return psiElement(ResTypes.IDENTIFIER).andNot(psiElement().afterLeaf(psiElement(ResTypes.COLON_COLON)))
      .withParent(psiElement(ResTypes.REFERENCE_EXP)
                    .withParent(psiElement(ResTypes.SIMPLE_STATEMENT)));
  }

  private static Capture<PsiElement> parameterModePattern() {
    return psiElement(ResTypes.IDENTIFIER).withParent(ResParameterMode.class);
  }

  private static Capture<PsiElement> variablePattern() {
    return psiElement(ResTypes.IDENTIFIER).withParent(psiElement(ResTypes.REFERENCE_EXP)
                                                        .isFirstAcceptedChild(psiElement()));
  }
}
