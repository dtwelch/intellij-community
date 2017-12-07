package com.jetbrains.resolve.psi;

/**
 * Anything that (implicitly) 'owns' some {@link ResMathExp} that happens to function as a type
 * descriptor -- meaning something on the right hand side of a math colon ':' .
 */
public interface ResMathMetaTypeExpOwner extends ResCompositeElement {}
