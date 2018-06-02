package com.jetbrains.resolve.action.formatting;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ResolveDesugarDirectoryAction extends ResolveFormatDirectoryAction {

  @NotNull
  @Override
  public List<String> getArguments(@NotNull String fileName) {
    List<String> result = new ArrayList<>();
    result.add("-desugar");
    result.add(fileName);
    return result;
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Desugaring code";
  }
}
