package com.jetbrains.resolve.psi;

import com.intellij.psi.tree.IElementType;
import com.jetbrains.resolve.ResolveLanguage;
import org.jetbrains.annotations.NotNull;

/** A Jetbrains specific token type for any {@link ResCompositeElement}. */
public class ResCompositeElementType extends IElementType {

  public ResCompositeElementType(@NotNull String debug) {
    super(debug, ResolveLanguage.INSTANCE);
  }
}
