package com.jetbrains.resolve.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/** Represents the root of all module like declarations in RESOLVE. */
public interface ResModuleDecl extends ResNamedElement {

  boolean shouldAutoImportUses();

  @NotNull
  ResBlock getBlock();

  @NotNull
  List<ResModuleIdentifierSpec> getModuleIdentifierSpecs();

  /**
   * For implementations primary, returns a list of {@link ResReferenceExp}s corresponding to the one or more modules
   * we're extending, implementing, or enhancing.
   * <p>
   * For example, say we're implementing Stack_Template using an array based implementation:
   * {@code Implementation Array_Impl for Stack_Template; ... } here Stack_Template is a named reference to super
   * module and as such (for now at least) must be explicitly named in the {@code uses} list (as it could theoretically
   * be found in a different project via a from clause).
   * </p>
   * @return a list of all super module referenc exps in this module's header.
   */
  @NotNull
  List<ResReferenceExp> getModuleHeaderReferences();

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
