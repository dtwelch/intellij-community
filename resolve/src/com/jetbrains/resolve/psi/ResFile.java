package com.jetbrains.resolve.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.resolve.ResolveFileType;
import com.jetbrains.resolve.ResolveIcons;
import com.jetbrains.resolve.ResolveLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ResFile extends PsiFileBase {

  public ResFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, ResolveLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return ResolveFileType.INSTANCE;
  }

  @NotNull
  @Override
  public PsiElement[] getChildren() {
    return super.getChildren();
  }

  @Nullable
  public ResModuleDecl getEnclosedModule() {
    return PsiTreeUtil.findChildOfType(this, ResModuleDecl.class);
  }

  @NotNull
  public List<ResMathDefnSig> getMathDefnSignatures() {
    ResModuleDecl enclosedModule = getEnclosedModule();
    return enclosedModule != null ? enclosedModule.getMathDefnSigs() : new ArrayList<ResMathDefnSig>();
  }

  @NotNull
  public List<ResModuleIdentifierSpec> getModuleIdentifierSpecs() {
    ResModuleDecl enclosedModule = getEnclosedModule();
    return enclosedModule != null ? enclosedModule.getUsesModuleIdentifierSpecs() :
           new ArrayList<ResModuleIdentifierSpec>();
  }

  @NotNull
  public List<ResModuleIdentifierSpec> getStandardFacilities() {
    return CachedValuesManager.getCachedValue(this, () -> {
      ResModuleDecl enclosedModule = getEnclosedModule();
      List<ResModuleIdentifierSpec> result = new ArrayList<>();
      if (enclosedModule != null) {
        for (ResModuleIdentifierSpec m : enclosedModule.getStandardModulesToSearch()) {
          PsiElement resolved = m.getModuleIdentifier().resolve();
          if (resolved != null && resolved instanceof ResFile &&
              ((ResFile)resolved).getEnclosedModule() != null &&
              ((ResFile)resolved).getEnclosedModule() instanceof ResFacilityModuleDecl) {
            result.add(m);
          }
        }
      }
      return CachedValueProvider.Result.create(result, this);
    });
  }

   /* @NotNull
    public List<ResModuleIdentifier> getSuperModuleModuleIdentifierList() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getSuperModuleSpecList() :
                new ArrayList<ResModuleIdentifier>();
    }*/

  @NotNull
  public List<ResTypeParamDecl> getGenericTypeParams() {
    ResModuleDecl enclosedModule = getEnclosedModule();
    return (enclosedModule != null) ? enclosedModule.getGenericTypeParams() : new ArrayList<ResTypeParamDecl>();
  }

  @NotNull
  public List<ResParamDecl> getConstantModuleParams() {
    ResModuleDecl enclosedModule = getEnclosedModule();
    List<ResParamDecl> params = new ArrayList<>();
    if (enclosedModule == null) return params;
    ResModuleParameters moduleParams = enclosedModule.getModuleParameters();
    if (moduleParams instanceof ResSpecModuleParameters) {
      params.addAll(((ResSpecModuleParameters) params).getParamDeclList());
    }
    return params;
  }

  @NotNull
  public List<ResTypeLikeNodeDecl> getTypes() {
    ResModuleDecl enclosedModule = getEnclosedModule();
    return enclosedModule != null ? enclosedModule.getTypes() : new ArrayList<ResTypeLikeNodeDecl>();
  }

  @NotNull
  public List<ResFacilityDecl> getFacilities() {
    ResModuleDecl enclosedModule = getEnclosedModule();
    return enclosedModule != null ? enclosedModule.getFacilities() : new ArrayList<ResFacilityDecl>();
  }

  @NotNull
  public List<ResOperationLikeNode> getOperationLikeThings() {
    ResModuleDecl enclosedModule = getEnclosedModule();
    return enclosedModule != null ? enclosedModule.getOperationLikeThings() : new ArrayList<ResOperationLikeNode>();
  }

    /*@NotNull public List<ResAnnotatableOperationLikeNode> getOperationImpls() {
        ResModuleDecl enclosedModule = getEnclosedModule();
        return enclosedModule != null ? enclosedModule.getOperationsWithImpls() :
                new ArrayList<ResAnnotatableOperationLikeNode>();
    }*/

  public boolean hasMainOperationWithBody() { // todo create a map for faster search
    List<ResOperationLikeNode> operations = getOperationLikeThings();
    if (!(getEnclosedModule() instanceof ResFacilityModuleDecl)) return false;
    for (ResOperationLikeNode o : operations) {
      if (o instanceof ResOperationProcedureDecl &&
          o.getName() != null && o.getName().equalsIgnoreCase("main")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Icon getIcon(int s) {
    if (getEnclosedModule() == null) {
      return ResolveIcons.RESOLVE_FILE;
    }
    else {
      return getEnclosedModule().getIcon(0);
    }
  }

}
