package com.jetbrains.resolve;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
  name = "RESOLVE",
  storages = @Storage(file = StoragePathMacros.APP_CONFIG + "/editor.compilersettings.xml")
)
public class ResolveCompilerSettings implements PersistentStateComponent<ResolveCompilerSettings> {

  private boolean noAutoStandardUses = false;
  private boolean showCompilerEnvVarsOnRun = false;
  //private boolean useMathAsciiAbbreviations = false;
  //private boolean useMathUnicodeSymbols = true;

  public static ResolveCompilerSettings getInstance() {
    return ServiceManager.getService(ResolveCompilerSettings.class);
  }

  @Nullable
  @Override
  public ResolveCompilerSettings getState() {
    return this;
  }

  @Override
  public void loadState(ResolveCompilerSettings state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  public boolean isNoAutoStandardUses() {
    return noAutoStandardUses;
  }

  public void setNoAutoStandardUses(boolean noAutoStandardUses) {
    this.noAutoStandardUses = noAutoStandardUses;
  }

  public boolean isShowCompilerEnvVarsOnRun() {
    return showCompilerEnvVarsOnRun;
  }

  public void setShowCompilerEnvVarsOnRun(boolean showCompilerEnvVarsOnRun) {
    this.showCompilerEnvVarsOnRun = showCompilerEnvVarsOnRun;
  }

  //formatting getters and setters
  /*public boolean isUseMathAsciiAbbreviations() {
    return useMathAsciiAbbreviations;
  }

  public void setUseMathAsciiAbbreviations(boolean useMathAsciiAbbrevs) {
    this.useMathAsciiAbbreviations = useMathAsciiAbbrevs;
  }

  public boolean isUseMathUnicodeSymbols() {
    return useMathUnicodeSymbols;
  }

  public void setUseMathUnicodeSymbols(boolean useUnicode) {
    this.useMathUnicodeSymbols = useUnicode;
  }*/
}
