package com.jetbrains.resolve.template;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolveLiveTemplatesProvider implements DefaultLiveTemplatesProvider {

  @NotNull
  @Override
  public String[] getDefaultLiveTemplateFiles() {
    return new String[]{"/liveTemplates/Resolve"};
  }

  @Nullable
  @Override
  public String[] getHiddenLiveTemplateFiles() {
    //return new String[]{"/liveTemplates/ResolveHidden"};
    return null;
  }
}
