package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class ResAbstractModuleImpl extends ResNamedElementImpl implements ResModuleDecl {

  ResAbstractModuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  @NotNull
  public ResBlock getBlock() {
    return findNotNullChildByClass(ResBlock.class);
  }

  @Nullable
  @Override
  public PsiElement getIdentifier() {
    return findChildByType(ResTypes.IDENTIFIER);
  }

  @NotNull
  @Override
  public List<ResModuleIdentifierSpec> getModuleIdentifierSpecs() {
    return getUsesList() != null ? getUsesList().getModuleIdentifierSpecList() : ContainerUtil.newArrayList();
  }

  @NotNull
  public List<ResReferenceExp> getModuleHeaderReferences() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResReferenceExp.class);
  }

  @Nullable
  public ResUsesList getUsesList() {
    return PsiTreeUtil.findChildOfType(this, ResUsesList.class);
  }

  @NotNull
  @Override
  public List<ResMathDefnDecl> getMathDefinitionDecls() {
    return CachedValuesManager.getCachedValue(
      this,
      new CachedValueProvider<List<ResMathDefnDecl>>() {
        @Override
        public Result<List<ResMathDefnDecl>> compute() {
          return Result.create(calc(ResMathDefnDecl.class), ResAbstractModuleImpl.this);
        }
      });
  }

  @NotNull
  @Override
  public List<ResMathDefnSig> getMathDefnSigs() {
    List<ResMathDefnSig> signatures = new ArrayList<>();
    for (ResMathDefnDecl def : getMathDefinitionDecls()) {
      List<ResMathDefnSig> sigs = def.getSignatures();
      signatures.addAll(sigs);
    }
    return signatures;
  }

  @NotNull
  private <T extends ResCompositeElement> List<T> calc(final Class<? extends T> type) {
    final List<T> result = ContainerUtil.newArrayList();
    processChildrenDummyAware(this.getBlock(), new Processor<PsiElement>() {
      @Override
      public boolean process(PsiElement e) {
        if (type.isInstance(e)) result.add(type.cast(e));
        return true;
      }
    });
    return result;
  }

  private static boolean processChildrenDummyAware(@NotNull ResBlock module,
                                                   @NotNull final Processor<PsiElement> processor) {
    return new Processor<PsiElement>() {
      @Override
      public boolean process(@NotNull PsiElement psiElement) {
        for (PsiElement child = psiElement.getFirstChild();
             child != null; child = child.getNextSibling()) {
          if (child instanceof GeneratedParserUtilBase.DummyBlock) {
            if (!process(child)) return false;
          }
          else if (!processor.process(child)) return false;
        }
        return true;
      }
    }.process(module);
  }
}
