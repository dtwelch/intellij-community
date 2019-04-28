package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
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

import java.util.*;
import java.util.stream.Collectors;

public abstract class ResAbstractModuleImpl extends ResNamedElementImpl implements ResModuleDecl {

  ResAbstractModuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Nullable
  public ResRequiresClause getRequiresClause() {
    return findChildByClass(ResRequiresClause.class);
  }

  @NotNull
  public ResBlock getBlock() {
    return findNotNullChildByClass(ResBlock.class);
  }

  //really this should just lookup a map from the std directory
  @Override
  public boolean shouldAutoSearchUses() {
    Project project = getProject();
    Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
    PsiFile psiFile = getContainingFile();
    if (sdk == null || psiFile == null) return false;

    PsiDirectory dir = psiFile.getOriginalFile().getContainingDirectory();
    if (dir == null) return false;
    String homePath = sdk.getHomePath();
    String dirPath = dir.getVirtualFile().getPath();
    boolean isCoreLibraryModule = homePath != null && dir.getVirtualFile().getPath().contains(homePath);
    boolean noAutoStandardUseChecked = ResolveCompilerSettings.getInstance().isNoAutoStandardUses();

    //if we don't represent a core library module, and the "no-std-uses-flag" setting isn't checked,
    //then we should automatically include standard imports.
    return !isCoreLibraryModule && !noAutoStandardUseChecked;
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

  @NotNull
  public List<ResModuleIdentifierSpec> getImports() {
    return CachedValuesManager.getCachedValue(this, new CachedValueProvider<List<ResModuleIdentifierSpec>>() {
      @Override
      public Result<List<ResModuleIdentifierSpec>> compute() {
        List<ResModuleIdentifierSpec> imports = calcImports();
        return Result.create(imports, ResAbstractModuleImpl.this);
      }
    });
  }

  @NotNull
  public List<ResReferenceExp> getModuleHeaderReferences() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, ResReferenceExp.class);
  }

  /**
   * @return map like { module name, maybe alias -> module ident spec } for module
   */
    /*public Map<String, ResModuleIdentifierSpec> getUsesMap() {
        return CachedValuesManager.getCachedValue(this, new CachedValueProvider<Map<String, ResModuleIdentifierSpec>>() {
            @Nullable
            @Override
            public Result<Map<String, ResModuleIdentifierSpec>> compute() {
                Map<String, ResModuleIdentifierSpec> map = new LinkedHashMap<>();
                List<Object> dependencies = ContainerUtil.newArrayList(GoFile.this);
                Module module = ModuleUtilCore.findModuleForPsiElement(GoFile.this);
                for (ResModuleIdentifierSpec spec : getModuleIdentifierSpecs()) {
                    if (spec.getAlias() != )
                    String alias = spec.getAlias();
                    ..
                }
                return Result.create(map, ArrayUtil.toObjectArray(dependencies));
            }
        });
    }*/
  @NotNull
  public Map<String, ResModuleIdentifierSpec> getModuleIdentifierSpecMap() {
    return CachedValuesManager.getCachedValue(this, new CachedValueProvider<Map<String, ResModuleIdentifierSpec>>() {
      @Nullable
      @Override
      public Result<Map<String, ResModuleIdentifierSpec>> compute() {
        Map<String, ResModuleIdentifierSpec> result = new HashMap<>();
        for (ResModuleIdentifierSpec spec : getImports()) {
          result.put(spec.getName(), spec);
          //if (spec.getAlias() != null) {
          //    result.put(spec.getAlias().getText(), spec);
          //}
        }
        return Result.create(result, ResAbstractModuleImpl.this);
      }
    });
  }


  @Nullable
  @Override
  public PsiElement getIdentifier() {
    return findChildByType(ResTypes.IDENTIFIER);
  }

   /* @NotNull
    public List<ResModuleSpec> getSuperModuleSpecList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, ResModuleSpec.class);
    }*/

  @Nullable
  public ResModuleParameters getModuleParameters() {
    return PsiTreeUtil.findChildOfType(this, ResModuleParameters.class);
  }

  @NotNull
  @Override
  @Deprecated
  public List<ResModuleIdentifierSpec> getModuleIdentifierSpecs() {
    return getUsesList() != null ? getUsesList().getModuleIdentifierSpecList() :
           ContainerUtil.<ResModuleIdentifierSpec>newArrayList();
  }

  @Nullable
  public ResUsesList getUsesList() {
    return PsiTreeUtil.findChildOfType(this, ResUsesList.class);
  }

  @NotNull
  @Override
  public List<ResTypeParamDecl> getGenericTypeParams() {
    List<ResTypeParamDecl> genericTypes = new ArrayList<>();
    ResModuleParameters params = getModuleParameters();
    if (params instanceof ResSpecModuleParameters) {
      genericTypes.addAll(((ResSpecModuleParameters)params).getTypeParamDeclList());
    }
    return genericTypes;
  }

  @NotNull
  @Override
  public List<ResParamDef> getConstantParamDefs() {
    List<ResParamDef> result = new ArrayList<>();
    ResModuleParameters params = getModuleParameters();
    if (params instanceof ResSpecModuleParameters) {
      for (ResParamDecl paramGroup : (((ResSpecModuleParameters)params).getParamDeclList())) {
        result.addAll(paramGroup.getParamDefList());
      }
    }
    return result;
  }

  @NotNull
  @Override
  public List<ResMathDefnSig> getDefinitionParamSigs() {
    List<ResMathDefnSig> result = new ArrayList<>();
    ResModuleParameters params = getModuleParameters();
    if (params instanceof ResSpecModuleParameters) {
      for (ResMathStandardDefnDecl paramGroup : (((ResSpecModuleParameters)params).getMathParameterDefnDeclList())) {
        result.addAll(paramGroup.getSignatures());
      }
    }
    return result;
  }

  @NotNull
  @Override
  public List<ResMathDefnDecl> getMathDefinitionDecls() {
    return CachedValuesManager.getCachedValue(this,
                                              new CachedValueProvider<List<ResMathDefnDecl>>() {
                                                @Override
                                                public Result<List<ResMathDefnDecl>> compute() {
                                                  return Result.create(calc(ResMathDefnDecl.class),
                                                                       ResAbstractModuleImpl.this);
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
  public List<ResTypeLikeNodeDecl> getTypes() {
    return CachedValuesManager.getCachedValue(this,
                                              new CachedValueProvider<List<ResTypeLikeNodeDecl>>() {
                                                @Override
                                                public Result<List<ResTypeLikeNodeDecl>> compute() {
                                                  return Result.create(calc(ResTypeLikeNodeDecl.class),
                                                                       ResAbstractModuleImpl.this);
                                                }
                                              });
  }

  @NotNull
  public List<ResFacilityDecl> getFacilities() {
    return CachedValuesManager.getCachedValue(this,
                                              new CachedValueProvider<List<ResFacilityDecl>>() {
                                                @Override
                                                public Result<List<ResFacilityDecl>> compute() {
                                                  return Result.create(calc(ResFacilityDecl.class),
                                                                       ResAbstractModuleImpl.this);
                                                }
                                              });
  }

  @NotNull
  public List<ResOperationLikeNode> getOperationLikeThings() {
    return CachedValuesManager.getCachedValue(this,
                                              new CachedValueProvider<List<ResOperationLikeNode>>() {
                                                @Override
                                                public Result<List<ResOperationLikeNode>> compute() {
                                                  return Result.create(calc(ResOperationLikeNode.class),
                                                                       ResAbstractModuleImpl.this);
                                                }
                                              });
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

  @NotNull
  private List<ResModuleIdentifierSpec> calcImports() {
    List<ResModuleIdentifierSpec> result = ContainerUtil.newArrayList();
    ResUsesList list = getUsesList();
    if (list == null) return ContainerUtil.emptyList();
    for (ResModuleIdentifierSpec identifier : list.getModuleIdentifierSpecList()) {
      result.add(identifier);
    }
    return result;
  }
}
