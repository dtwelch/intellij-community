package com.jetbrains.resolve.psi.impl;

import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.OrderedSet;
import com.jetbrains.resolve.psi.ResFile;
import com.jetbrains.resolve.psi.ResMathReferenceExp;
import com.jetbrains.resolve.psi.ResNamedElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResMathVarLikeReference extends PsiPolyVariantReferenceBase<ResMathReferenceExp> {

  private static final Key<SmartPsiElementPointer<ResMathReferenceExp>> CONTEXT = Key.create("CONTEXT");

  ResMathVarLikeReference(@NotNull ResMathReferenceExp o) {
    super(o, TextRange.from(o.getIdentifier().getStartOffsetInParent(),
                            o.getIdentifier().getTextLength()));
  }

  private static final ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase> MY_RESOLVER =
    new ResolveCache.PolyVariantResolver<PsiPolyVariantReferenceBase>() {
      @NotNull
      @Override
      public ResolveResult[] resolve(@NotNull PsiPolyVariantReferenceBase t,
                                     boolean incompleteCode) {
        return ((ResMathVarLikeReference)t).resolveInner();
      }
    };

  @NotNull
  private ResolveResult[] resolveInner() {
    Collection<ResolveResult> result = new OrderedSet<>();
    processResolveVariants(ResReference.createResolveProcessor(result, myElement));
    return result.toArray(new ResolveResult[result.size()]);
  }

  @NotNull
  private PsiElement getIdentifier() {
    return myElement.getIdentifier();
  }

  @NotNull
  @Override
  public ResolveResult[] multiResolve(boolean b) {
    return myElement.isValid()
           ? ResolveCache.getInstance(myElement.getProject()).resolveWithCaching(this, MY_RESOLVER, false, false)
           : ResolveResult.EMPTY_ARRAY;
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
    ResMathReferenceExp qualifier = myElement.getQualifier();
    if (qualifier != null) {
      //return ResReference.processQualifierExpression(((ResFile)file), qualifier, processor, state);
    }
    return processUnqualifiedResolve(((ResFile)file), processor, state, true);
  }

  private boolean processUnqualifiedResolve(@NotNull ResFile file,
                                            @NotNull ResScopeProcessor processor,
                                            @NotNull ResolveState state,
                                            boolean localResolve) {
    PsiElement parent = myElement.getParent();
   /* if (parent instanceof ResMathSelectorExp) {
      boolean result = processMathSelector((ResMathSelectorExp) parent, processor, state, myElement);
      if (processor.isCompletion()) return result;
      if (!result || ResPsiImplUtil.prevDot(myElement)) return false;
    }
    PsiElement grandPa = parent.getParent();
    if (grandPa instanceof ResMathSelectorExp && !processMathSelector((ResMathSelectorExp) grandPa, processor, state, parent)) return false;
    if (ResPsiImplUtil.prevDot(parent)) return false;
    */
    //if (!processConceptualAccessor(processor, state, true)) return false;

    if (!processBlock(processor, state, true)) return false;
    if (!ResReference.processModuleLevelEntities(file, processor, state, localResolve)) return false;
    if (!ResReference.processModuleHeaderAndExplicitUsesImports(file, processor, state)) return false;
    //if (!processBuiltin(processor, state, myElement)) return false;
    return true;
  }

  //TODO: Keep eye on ResScopeProcessorBase!! #execute method added ResTypeReprDecl...
  private boolean processBlock(@NotNull ResScopeProcessor processor,
                               @NotNull ResolveState state,
                               boolean localResolve) {
    ResScopeProcessorBase delegate = createDelegate(processor);
    ResolveUtil.treeWalkUp(myElement, delegate);
    //process local parameters if we're in a local definition or an operation like thing (doesn't include module
    //params; that's in processModuleLevelEntities)
    //ResReference.processBlockParameters(myElement, delegate);
    return processNamedElements(processor, state, delegate.getVariants(), localResolve);
  }

  private boolean processNamedElements(@NotNull PsiScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       @NotNull Collection<? extends ResNamedElement> elements,
                                       boolean localResolve) {
    for (ResNamedElement e : elements) {
      if ((e.isUsesClauseVisible() || localResolve) && !processor.execute(e, state)) {
        return false;
      }
    }
    return true;
  }

  @NotNull
  private ResMathVarLikeProcessor createDelegate(@NotNull ResScopeProcessor processor) {
    return new ResMathVarLikeProcessor(myElement, processor.isCompletion());
  }

  private static class ResMathVarLikeProcessor extends ResScopeProcessorBase {
    public Map<String, String> implicitlyBoundTypeParameters = new HashMap<>();

    ResMathVarLikeProcessor(@NotNull ResMathReferenceExp origin, boolean completion) {
      super(origin.getIdentifier(), origin, completion);
    }

    @Override
    protected boolean crossOff(@NotNull PsiElement e) {
      return false;
    }
  }
}
