package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.HashSet;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.configuration.ResolveCompilerSettings;
import com.jetbrains.resolve.psi.*;
import com.jetbrains.resolve.sdk.ResolveSdkUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ResAbstractModuleImpl extends ResNamedElementImpl implements ResModuleDecl {

  ResAbstractModuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public boolean shouldAutoSearchUses() {
    Project project = getProject();
    Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
    VirtualFile vFile = getContainingFile().getVirtualFile();
    boolean result = vFile != null && sdk != null;
    if (result) {
      String homePath = sdk.getHomePath();
      boolean isCoreLibraryModule = homePath != null && vFile.getPath().contains(homePath);
      boolean noAutoStandardUseChecked = ResolveCompilerSettings.getInstance().isNoAutoStandardUses();

      //if we don't represent a core library module, and the "no-std-uses-flag" setting isn't checked,
      //then we should automatically include standard imports.
      result = !isCoreLibraryModule && !noAutoStandardUseChecked;
    }
    return result;
  }

  //TODO: Store a list of the standard modules somewhere in RESOLVEROOT so we can avoid hardcoding them
  // (and their names here)
  @NotNull
  @Override
  public List<ResModuleIdentifierSpec> getStandardModulesToSearch() {
    return CachedValuesManager.getCachedValue(this, () -> {
      List<String> toConvert = ContainerUtil.newArrayList();
      toConvert.add("Class_Theory");
      toConvert.add("Boolean_Theory");
      return CachedValueProvider.Result.create(
        ResElementFactory.createUsesSpecList(getProject(), toConvert), this);
    });
  }

  /*
  @NotNull
  public List<GoConstDefinition> getConstants() {
    return CachedValuesManager.getCachedValue(this, () -> {
      StubElement<GoFile> stub = getStub();
      List<GoConstDefinition> result;
      if (stub != null) {
        result = ContainerUtil.newArrayList();
        List<GoConstSpec> constSpecs = getChildrenByType(stub, GoTypes.CONST_SPEC, GoConstSpecStubElementType.ARRAY_FACTORY);
        for (GoConstSpec spec : constSpecs) {
          GoConstSpecStub specStub = spec.getStub();
          if (specStub == null) continue;
          result.addAll(getChildrenByType(specStub, GoTypes.CONST_DEFINITION, GoConstDefinitionStubElementType.ARRAY_FACTORY));
        }
      }
      else {
        result = calcConsts();
      }
      return CachedValueProvider.Result.create(result, this);
    });
  }*/

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
  public List<ResModuleIdentifierSpec> getModuleHeaderIdentifierSpecs() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResModuleIdentifierSpec.class);
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
