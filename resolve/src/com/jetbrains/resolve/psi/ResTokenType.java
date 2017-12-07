package com.jetbrains.resolve.psi;

import com.intellij.psi.tree.IElementType;
import com.jetbrains.resolve.ResolveLanguage;
import org.jetbrains.annotations.NotNull;

public class ResTokenType extends IElementType {

  public ResTokenType(@NotNull String debugName) {
    super(debugName, ResolveLanguage.INSTANCE);
  }
}
