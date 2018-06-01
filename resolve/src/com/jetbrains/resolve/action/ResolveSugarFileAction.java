package com.jetbrains.resolve.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ResolveSugarFileAction extends ResolveFormatFileAction {

  @Override
  public Map<String, String> getArgMap(@NotNull AnActionEvent e) {
    VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);

    Map<String, String> args = new HashMap<>();
    args.put("-sugar", file.getCanonicalPath());
    return args;
  }
}
