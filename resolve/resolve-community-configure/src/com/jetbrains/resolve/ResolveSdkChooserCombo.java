package com.jetbrains.resolve;

import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionListener;
import java.util.List;

public class ResolveSdkChooserCombo extends ComboboxWithBrowseButton {
  private final List<ActionListener> myChangedListeners = ContainerUtil.createLockFreeCopyOnWriteList();

  @Nullable private String myNewProjectPath;

}
