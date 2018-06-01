package com.jetbrains.resolve.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ResolveSugarFileAction extends ResolveFormatFileAction {

  @NotNull
  @Override
  public List<String> getArguments(@NotNull AnActionEvent e) {
    VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
    List<String> result = new ArrayList<>();
    result.add("-sugar");
    result.add(file.getCanonicalPath());
    return result;
  }

  @NotNull
  @Override
  public String getTitle() {
    return "Sugaring Code";
  }
}
