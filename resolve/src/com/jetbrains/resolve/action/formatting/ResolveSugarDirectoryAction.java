package com.jetbrains.resolve.action.formatting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ResolveSugarDirectoryAction extends ResolveFormatDirectoryAction {

  @NotNull
  @Override
  public List<String> getArguments(@NotNull String fileName) {
    List<String> result = new ArrayList<>();
    result.add("-sugar");
    result.add(fileName);
    return result;
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Sugaring code";
  }
}
