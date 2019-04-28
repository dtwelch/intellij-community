package com.jetbrains.resolve.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Represents the root of all module like declarations in RESOLVE.
 */
public interface ResModuleDecl extends ResNamedElement {

  boolean shouldAutoSearchUses();

  @Nullable
  public ResRequiresClause getRequiresClause();

  @NotNull
  public List<ResModuleIdentifierSpec> getStandardModulesToSearch();

  @NotNull
  public ResBlock getBlock();

  ResUsesList getUsesList();

  /**
   * Returns all {@link ResModuleIdentifierSpec}s identifying modules that this module happens to extend. For example,
   * if a particular instance of this class represents is an (enhancement) realization module named {@code T_Realiz},
   * i.e.:
   * <pre>
   *   Realization T_Realiz for T_Enhancement of T_Concept from Foo.Y;
   *    ...
   * </pre>
   * then a call to this method will return a list that looks like this {@link [T_Enhancement, T_Concept from Foo.Y]}.
   * Where each element is a well formed module identifier spec.
   *
   * @return The list of module identifiers referenced in the module's header.
   */
  @NotNull
  public List<ResModuleIdentifierSpec> getModuleIdentifierSpecs();

  @NotNull
  public Map<String, ResModuleIdentifierSpec> getModuleIdentifierSpecMap();

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
}
