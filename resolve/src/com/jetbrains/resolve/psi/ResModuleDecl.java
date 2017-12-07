package com.jetbrains.resolve.psi;

import org.jetbrains.annotations.NotNull;

/** Represents the root of all module like declarations in RESOLVE. */
public interface ResModuleDecl extends ResNamedElement {

  /*@NotNull
  ResBlock getBlock();

      @NotNull
      public List<ResModuleIdentifierSpec> getModuleIdentifierSpecs();

      @NotNull
      public Map<String, ResModuleIdentifierSpec> getModuleIdentifierSpecMap();

      @NotNull
      public List<ResReferenceExp> getModuleHeaderReferences();

      @NotNull
      public List<ResMathDefnDecl> getMathDefinitionDecls();

      @NotNull
      public List<ResMathDefnSig> getMathDefnSigs();

      @NotNull
      public List<ResTypeLikeNodeDecl> getTypes();

      @NotNull
      public List<ResFacilityDecl> getFacilities();

      @Nullable
      public ResModuleParameters getModuleParameters();

      @NotNull
      public List<ResTypeParamDecl> getGenericTypeParams();

      @NotNull
      public List<ResParamDef> getConstantParamDefs();

      @NotNull
      public List<ResMathDefnSig> getDefinitionParamSigs();

      @NotNull
      public List<ResOperationLikeNode> getOperationLikeThings();
  */
}
