// Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jetbrains.resolve.sdk;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.ui.ColoredListCellRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ResolveSdkListCellRenderer extends ColoredListCellRenderer<Object> {

  @Override
  protected void customizeCellRenderer(@NotNull JList<?> list, Object value, int index, boolean selected, boolean hasFocus) {
    if (value instanceof Sdk) {
      Sdk valueAsSdk = (Sdk) value;
      appendName(valueAsSdk);
      setIcon(customizeIcon(valueAsSdk));
    }
    else {

    }
  }

  private void appendName(@NotNull Sdk sdk) {
  }

  @Nullable
  private Icon customizeIcon(@NotNull Sdk sdk) {
    return null;
  }
}
