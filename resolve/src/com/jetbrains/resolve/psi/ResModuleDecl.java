package com.jetbrains.resolve.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/** Represents the root of all module like declarations in RESOLVE. */
public interface ResModuleDecl extends ResNamedElement {

  boolean shouldAutoSearchUses();

  @NotNull
  ResBlock getBlock();

  @NotNull
  List<ResModuleIdentifierSpec> getModuleIdentifierSpecs();

  /**
   * Returns a list of module (header) identifiers.
   * <p>
   * For example, in the case of {@code T extends U}, this method would return a list containing an
   * {@link ResModuleIdentifierSpec} for {@code U}.
   *
   * @return a list of all super module references in the module's header.
   */
  @NotNull
  List<ResModuleIdentifierSpec> getModuleHeaderIdentifierSpecs();

  @NotNull
  List<ResModuleIdentifierSpec> getStandardModulesToSearch();

  @NotNull
  List<ResMathDefnDecl> getMathDefinitionDecls();

  @NotNull
  List<ResMathDefnSig> getMathDefnSigs();

  /*@NotNull
  ResBlock getBlock();

      @NotNull
      public List<ResModuleIdentifierSpec> getModuleIdentifierSpecs();

      @NotNull
      public Map<String, ResModuleIdentifierSpec> getModuleIdentifierSpecMap();

      @NotNull
      public List<ResReferenceExp> getModuleHeaderIdentifierSpecs();

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
