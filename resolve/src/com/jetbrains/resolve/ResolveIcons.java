package com.jetbrains.resolve;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class ResolveIcons {
  private static Icon load(String path) {
    return IconLoader.getIcon(path, ResolveIcons.class);
  }

  public static final Icon ResolveFile = load("/icons/com/jetbrains/resolve/resolveFile.png");

  public static final Icon Resolve = load("/icons/com/jetbrains/resolve/tool_icon.png");
}
