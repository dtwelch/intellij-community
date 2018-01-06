package com.jetbrains.resolve.editor;

import com.intellij.openapi.editor.colors.FontPreferences;
import com.intellij.openapi.options.FontSize;
import org.jetbrains.annotations.NotNull;

public class ResolveFontPreferences extends FontPreferences {

  public final static int DEFAULT_FONT_SIZE = FontSize.MEDIUM.getSize();

  public final static float DEFAULT_LINE_SPACING = .9f;
  public final static String DEFAULT_FONT_FAMILY = "IsabelleText";

  @NotNull
  public String getFontFamily() {
    return DEFAULT_FONT_FAMILY;
  }

  public int getSize(@NotNull String fontFamily) {
    return DEFAULT_FONT_SIZE;
  }

  public boolean useLigatures() {
    return false;
  }

  public float getLineSpacing() {
    return DEFAULT_LINE_SPACING;
  }
}
