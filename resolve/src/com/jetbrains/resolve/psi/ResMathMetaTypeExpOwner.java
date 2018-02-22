package com.jetbrains.resolve.psi;

import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.Nullable;

/**
 * Anything that (implicitly) 'owns' some {@link ResMathExp} that happens to function as a type descriptor -- meaning
 * something on the right hand side of a math colon (or tricolon).
 */
public interface ResMathMetaTypeExpOwner extends ResCompositeElement {

  @Nullable
  ResMathExp getResMathMetaTypeExp(@Nullable ResolveState context);
}
