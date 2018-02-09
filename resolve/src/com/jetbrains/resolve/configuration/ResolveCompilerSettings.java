package com.jetbrains.resolve.configuration;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
  name = "RESOLVE",
  storages = @Storage(file = StoragePathMacros.APP_CONFIG + "/editor.compilersettings.xml")
)
public class ResolveCompilerSettings implements PersistentStateComponent<ResolveCompilerSettings> {

  private boolean autoImportStandardUses = true;
  private boolean showCompilerEnvVarsOnRun = false;

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

  public boolean isAutoImportStandardUses() {
    return autoImportStandardUses;
  }

  public void setAutoImportStandardUses(boolean autoImportStandardUses) {
    this.autoImportStandardUses = autoImportStandardUses;
  }

  public boolean isShowCompilerEnvVarsOnRun() {
    return showCompilerEnvVarsOnRun;
  }

  public void setShowCompilerEnvVarsOnRun(boolean showCompilerEnvVarsOnRun) {
    this.showCompilerEnvVarsOnRun = showCompilerEnvVarsOnRun;
  }
}
