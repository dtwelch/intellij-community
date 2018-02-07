package com.jetbrains.resolve.psi.impl;

import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.OrderedSet;
import com.jetbrains.resolve.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class ResReference extends PsiPolyVariantReferenceBase<ResReferenceExpBase> {

  private static final Key<SmartPsiElementPointer<ResReferenceExpBase>> CONTEXT = Key.create("CONTEXT");
  private static final Key<String> ACTUAL_NAME = Key.create("ACTUAL_NAME");

  private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
    (t, incompleteCode) -> ((ResReference)t).resolveInner();

  ResReference(@NotNull ResReferenceExpBase o) {
    super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                            o.getIdentifier().getTextLength()));
  }

  @NotNull
  private PsiElement getIdentifier() {
    return myElement.getIdentifier();
  }

  @NotNull
  private ResolveResult[] resolveInner() {
    if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
    Collection<ResolveResult> result = new OrderedSet<>();
    processResolveVariants(createResolveProcessor(result, myElement));
    return result.toArray(new ResolveResult[result.size()]);
  }

  @NotNull
  static ResScopeProcessor createResolveProcessor(@NotNull final Collection<ResolveResult> result,
                                                  @NotNull final ResReferenceExpBase o) {
    return new ResScopeProcessor() {
      @Override
      public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
        if (element.equals(o)) {
          return !result.add(new PsiElementResolveResult(element));
        }
        //TODO: Added 3/23/17 to keep program contexts from ref'ing math defns.
        //if (o instanceof ResReferenceExp && element instanceof ResMathDefnSig) {
        //    return true;
        //}
        String name = ObjectUtils.chooseNotNull(state.get(ACTUAL_NAME), element instanceof PsiNamedElement ?
                                                                        ((PsiNamedElement)element).getName() : null);
        if (name != null && o.getIdentifier().textMatches(name)) {
          result.add(new PsiElementResolveResult(element));
          return false;
        }
        return true;
      }
    };
  }

  @NotNull
  @Override
  public ResolveResult[] multiResolve(boolean incompleteCode) {
    if (!myElement.isValid()) return ResolveResult.EMPTY_ARRAY;
    return ResolveCache
      .getInstance(myElement.getProject())
      .resolveWithCaching(this, MY_RESOLVER, false, false);
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return ArrayUtil.EMPTY_OBJECT_ARRAY;
  }

  public boolean processResolveVariants(@NotNull ResScopeProcessor processor) {
    PsiFile file = myElement.getContainingFile();
    if (!(file instanceof ResFile)) return false;
    ResolveState state = ResolveState.initial();
    ResReferenceExpBase qualifier = myElement.getQualifier();
    /*return qualifier != null ?
           processQualifierExpression(((ResFile) file), qualifier, processor, state) :
           processUnqualifiedResolve(((ResFile) file), processor, state, true);*/
    return processUnqualifiedResolve(((ResFile)file), processor, state, true);
  }

  private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                            @NotNull ResScopeProcessor processor,
                                            @NotNull ResolveState state,
                                            boolean localResolve) {

    PsiElement parent = myElement.getParent();
    /*if (parent instanceof ResSelectorExp) {
      boolean result = processSelector((ResSelectorExp) parent, processor, state, myElement);
      if (processor.isCompletion()) return result;
      if (!result || ResPsiImplUtil.prevDot(myElement)) return false;
    }
    PsiElement grandPa = parent.getParent();
    if (grandPa instanceof ResSelectorExp && !processSelector((ResSelectorExp) grandPa, processor, state, parent)) return false;
    if (ResPsiImplUtil.prevDot(parent)) return false;
    if (!processBlock(processor, state, true)) return false;
    if (!processModuleLevelEntities(file, processor, state, true)) return false;*/

    if (!processUsesImports(file, processor, state)) return false;
    //if (!processFacilityImports(file, processor, state)) return false;

    return true;
  }

  public static boolean processUsesImports(@NotNull ResFile file,
                                           @NotNull ResScopeProcessor processor,
                                           @NotNull ResolveState state) {
    if (file.getEnclosedModule() == null) return true;
    return processUsesImports(file.getEnclosedModule(), processor, state);
  }

  //OK: So it looks like this is the method that's going to have to initiate the search into the super modules...
  //Update: Ok so at least this method is doing what I *think* it needs to be doing right now. Don't get me wrong its a godawful
  //mess, but at least its working as I expect for the moment. TODO: Clean it up, improve names etc.
  private static boolean processUsesImports(@NotNull ResModuleDecl moduleDecl,
                                            @NotNull ResScopeProcessor processor,
                                            @NotNull ResolveState state) {
    List<ResModuleIdentifierSpec> usesItems = moduleDecl.getModuleIdentifierSpecs();

    List<ResReferenceExp> headerModules = moduleDecl.getModuleHeaderReferences();
    for (ResModuleIdentifierSpec o : usesItems) {
      //if (o.getAlias() != null) {
      //    if (!processor.execute(o, state.put(ACTUAL_NAME, o.getAlias().getText()))) return false;
      //}
      //else {
      PsiElement resolve = o.getModuleIdentifier().resolve();
      if (resolve != null && resolve instanceof ResFile) {
        for (ResReferenceExp e : headerModules) {
          //process the super module's uses clauses
          List<ResModuleIdentifierSpec> superModuleUses = ((ResFile)resolve).getModuleIdentifierSpecs();

          //this shouldn't be necessary if we add implicit module specs... right?
          /*for (ResModuleIdentifierSpec e1 : superModuleUses) {
            PsiElement eRes = e1.getModuleIdentifier().resolve();
            if (eRes != null) {
              if (!processModuleLevelEntities((ResFile) eRes, processor, state, false)) return false;
            }
          }*/
        }
        /*processor.execute(resolve, state.put(ACTUAL_NAME, o.getModuleIdentifier().getText()));
        boolean forSuperModule = forSuperModule(moduleDecl, o.getName());
        if (!processModuleLevelEntities((ResFile) resolve, processor, state, forSuperModule)) return false;*/
      }
      //}
    }
    return true;
  }
}
