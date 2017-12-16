package com.jetbrains.resolve.sdk;

import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.projectRoots.impl.SdkListCellRenderer;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ResolveSdkListCellRenderer extends ColoredListCellRenderer<Object> {

  @Override
  protected void customizeCellRenderer(@NotNull JList<?> list, Object value, int index, boolean selected, boolean hasFocus) {
    if (value instanceof Sdk) {
      Sdk valueAsSdk = (Sdk) value;
      appendName(valueAsSdk);
      if (valueAsSdk.getSdkType() instanceof SdkType) {
        setIcon(((SdkType)valueAsSdk.getSdkType()).getIcon());
      }
    }
    else {
      append("<No SDK>");
    }
  }

  private void appendName(@NotNull Sdk sdk) {
    ResolveSdkType t = ResolveSdkType.getInstance();
    String homePath = sdk.getHomePath();
    if (homePath != null) {
      String name = t.suggestSdkName(null, homePath);
      append(name);
    }
    else {
      append(sdk.getName());
    }
    String relHomePath = homePath != null ? FileUtil.getLocationRelativeToUserHome(homePath) : null;
    if (relHomePath != null) {
      append(" (" + relHomePath + ")", SimpleTextAttributes.GRAYED_ATTRIBUTES);
    }
  }

}
