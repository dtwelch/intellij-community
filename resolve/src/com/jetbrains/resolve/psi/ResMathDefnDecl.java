package com.jetbrains.resolve.psi;

import java.util.List;

/**
 * Represents any sort of mathematical definition in RESOLVE, including: categorical, inductive, and standard
 * style defs.
 * <p>
 * Notice too that this doesn't extend {@link ResNamedElement}; this has to do with the fact that the name portion of
 * a definition isn't really part of the declaration itself, rather its bound up in an instance of
 * {@link ResMathDefnSig}.</p>
 */
public interface ResMathDefnDecl extends ResCompositeElement {

  /**
   * Returns a list of {@link ResMathDefnSig}s introduced by this declaration. Note that this is going to be a
   * singleton except in the case of {@link ResMathCategoricalDefnDecl}s, in which there could be an arbitrary number
   * of signatures introduced.
   */
  List<ResMathDefnSig> getSignatures();
}