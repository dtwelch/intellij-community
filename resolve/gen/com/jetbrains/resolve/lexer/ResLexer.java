// Copyright 2000-2017 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jetbrains.resolve.lexer;

import com.intellij.lexer.FlexAdapter;

public class ResLexer extends FlexAdapter {
  public ResLexer() {
    super(new _ResLexer());
  }
}
