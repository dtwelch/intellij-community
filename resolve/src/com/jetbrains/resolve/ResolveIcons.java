package com.jetbrains.resolve;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class ResolveIcons {
  private static Icon load(String path) {
    return IconLoader.getIcon(path, ResolveIcons.class);
  }

  public static final Icon RESOLVE_FILE = load("/icons/com/jetbrains/resolve/resolveFile.png");

  public static final Icon RESOLVE = load("/icons/com/jetbrains/resolve/tool_icon.png");

  public static final Icon PRECIS = load("/icons/com/jetbrains/resolve/precis.png");
  public static final Icon FACILITY = load("/icons/com/jetbrains/resolve/facility.png");

  public static final Icon DEF = load("/icons/com/jetbrains/resolve/def.png");
  public static final Icon VARIABLE = load("/icons/com/jetbrains/resolve/variable.png");

  public static final Icon SYMBOLS = load("/icons/com/jetbrains/resolve/symbols.png");
  public static final Icon VALIDATE = load("/icons/com/jetbrains/resolve/validate.png");

}
