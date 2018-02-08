package com.jetbrains.resolve.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.util.PlatformIcons;
import com.jetbrains.resolve.ResolveIcons;
import com.jetbrains.resolve.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ResolveCompletionUtil {

  public static final int VAR_PRIORITY = 30;
  public static final int FUNCTION_PRIORITY = 15;
  public static final int FACILITY_PRIORITY = 5;
  public static final int DEFINITION_PRIORITY = 10;
  public static final int TYPE_PRIORITY = 20;
  public static final int KEYWORD_PRIORITY = 1;

  private static class Lazy {
    private static final QualifierInsertHandler FACILITY_OR_MODULE_INSERT_HANDLER =
      new QualifierInsertHandler("::", true);
  }

  private static final LookupElementRenderer<LookupElement> VARIABLE_RENDERER =
    new LookupElementRenderer<LookupElement>() {
      @Override
      public void renderElement(LookupElement element, LookupElementPresentation p) {
        PsiElement o = element.getPsiElement();
        if (!(o instanceof ResNamedElement)) return;
        ResNamedElement v = (ResNamedElement)o;
        /*ResType type = v.getResType(null);
        String text = ResPsiImplUtil.getText(type);*/
        Icon icon =
          v instanceof ResMathVarDef ? ResolveIcons.VARIABLE :
         /* v instanceof ResVarDef ? RESOLVEIcons.VARIABLE :
          v instanceof ResExemplarDecl ? RESOLVEIcons.EXEMPLAR :
          v instanceof ResParamDef
          ? RESOLVEIcons.PARAMETER
          : v instanceof ResTypeParamDecl
            ? RESOLVEIcons.GENERIC_TYPE
            : v instanceof ResFieldDef ? RESOLVEIcons.RECORD_FIELD :*/ null;
        /*if (v instanceof ResMathVarDef) {
          //Todo: Need to write a getResTypeInner method and put it into the psi util class;
          //should be called from ResMathVarDefImpl...
          //typeText += v.get
        }*/
        p.setIcon(icon);
        //p.setTailText(calcTailTextForFields(v), true);
        //p.setTypeText(text);
        p.setTypeGrayed(true);
        p.setItemText(element.getLookupString());
      }
    };

  private static final LookupElementRenderer<LookupElement> DEFINITION_RENDERER =
    new LookupElementRenderer<LookupElement>() {
      @Override
      public void renderElement(LookupElement element, LookupElementPresentation p) {
        PsiElement o = element.getPsiElement();
        if (!(o instanceof ResMathDefnSig)) return;
        String rangeTypeText = "";
        ResMathDefnSig signature = (ResMathDefnSig)o;
        StringBuilder typeText = new StringBuilder();

        //Todo, move the following printing business logic into a method somewhere in ResPsiImplUtil
        ResCompositeElement mathType = signature.getMathTypeExp();
        boolean first = true;
        for (ResMathVarDeclGroup grp : signature.getParameters()) {
          if (grp.getMathExp() != null) {
            for (PsiElement e : grp.getMathVarDefList()) {
              if (first) {
                first = false;
                typeText.append(grp.getMathExp().getText());
              }
              else {
                typeText.append(" × ").append(grp.getMathExp().getText());
              }
            }
          }
        }
        if (mathType != null) rangeTypeText = mathType.getText();
        if (!typeText.toString().isEmpty()) typeText.append(" ⟶ ");
        typeText.append(rangeTypeText);
        p.setIcon(ResolveIcons.DEF);
        p.setTypeText(rangeTypeText);
        p.setTypeGrayed(true);
        // p.setTailText(calcTailText(f), true);
        String name =
          /*signature instanceof ResMathOutfixDefnSig
          || signature instanceof ResMathPostfixDefnSig
          ? signature.getCanonicalName()
          :*/ element.getLookupString();
        p.setItemText(name + " : " + typeText);
      }
    };

  @NotNull
  static LookupElement createMathDefinitionLookupElement(@NotNull ResMathDefnSig signature,
                                                         @NotNull String lookupString,
                                                         double priority) {
    return PrioritizedLookupElement.withPriority(
      LookupElementBuilder.createWithSmartPointer(lookupString, signature)
        .withRenderer(DEFINITION_RENDERER)
        .withInsertHandler(new MathSymbolInsertHandler()),
      priority);
  }

  @NotNull
  static LookupElement createMathVarLookupElement(@NotNull ResMathVarDef v) {
    return PrioritizedLookupElement.withPriority(
      LookupElementBuilder.createWithSmartPointer(v.getMathSymbolName().getText(), v)
        .withRenderer(VARIABLE_RENDERER)
        .withInsertHandler(new MathSymbolInsertHandler()), VAR_PRIORITY);
  }

  @NotNull
  static LookupElement createResModuleLookupElement(@NotNull ResModuleDecl module) {
    return createResModuleLookupElement(module, module.getName());
  }

  @Nullable
  static LookupElement createVariableLikeLookupElement(@NotNull ResNamedElement v) {
    String name = v.getName();
    if (StringUtil.isEmpty(name)) return null;
    return createVariableLikeLookupElement(v, name, null, VAR_PRIORITY);
  }

  @NotNull
  static LookupElement createVariableLikeLookupElement(@NotNull ResNamedElement v,
                                                       @NotNull String lookupString,
                                                       @Nullable InsertHandler<LookupElement> insertHandler,
                                                       double priority) {
    return PrioritizedLookupElement.withPriority(
      LookupElementBuilder.createWithSmartPointer(lookupString, v)
        .withRenderer(VARIABLE_RENDERER)
        .withInsertHandler(insertHandler), priority);
  }

  @NotNull
  private static LookupElement createResModuleLookupElement(@NotNull ResModuleDecl module, String name) {
    return PrioritizedLookupElement.withPriority(
      LookupElementBuilder.create(name)
        .withInsertHandler(Lazy.FACILITY_OR_MODULE_INSERT_HANDLER)
        .withIcon(module.getIcon(0)), FACILITY_PRIORITY);
  }

  @NotNull
  public static LookupElementBuilder createDirectoryLookupElement(@NotNull PsiDirectory directory) {
    return LookupElementBuilder.createWithSmartPointer(directory.getName(), directory)
      .withIcon(PlatformIcons.DIRECTORY_CLOSED_ICON);
  }

  @NotNull
  public static LookupElementBuilder createResolveFileLookupElement(@NotNull ResFile resolveFile) {
    return createResolveFileLookupElement(resolveFile, false);
  }

  @NotNull
  static PrefixMatcher createPrefixMatcher(@NotNull PrefixMatcher original) {
    return new CamelHumpMatcher(original.getPrefix(), true);
  }

  @NotNull
  public static LookupElementBuilder createResolveFileLookupElement(@NotNull ResFile resolveFile, boolean forTypes) {
    String name = resolveFile.getVirtualFile().getNameWithoutExtension();
    LookupElementBuilder result = LookupElementBuilder.create(name).withIcon(resolveFile.getIcon(0));
    if (forTypes) {
      result = result.withInsertHandler(Lazy.FACILITY_OR_MODULE_INSERT_HANDLER);
    }
    return result;
  }
}
