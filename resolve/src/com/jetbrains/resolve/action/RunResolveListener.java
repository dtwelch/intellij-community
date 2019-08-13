package com.jetbrains.resolve.action;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import edu.clemson.resolve.core.ResolveCompilerListener;
import edu.clemson.resolve.core.ResolveMessage;
import edu.clemson.resolve.core.control.UserInterfaceControl;
import org.jetbrains.annotations.NotNull;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.List;

/**
 * Listens for information coming back from the resolve compiler. Captures warnings, errors, and general informational
 * printouts emitted by the compiler. This class, like {@link RunRESOLVEOnLanguageFile} follows very closely the tool
 * listener written for this purpose in the ANTLRv4 plugin written by Terence Parr and others, found here:
 * <p>
 * <a href="https://github.com/antlr/intellij-plugin-v4">https://github.com/antlr/intellij-plugin-v4/a>
 */
public class RunResolveListener implements ResolveCompilerListener {

  public final List<String> all = new ArrayList<String>();

  private final UserInterfaceControl compiler;
  private final ConsoleView console;
  public boolean hasOutput = false;

  public RunResolveListener(@NotNull UserInterfaceControl compiler,
                            @NotNull ConsoleView console) {
    this.compiler = compiler;
    this.console = console;
  }

  @Override
  public void reportStatus(@NotNull String s) {
    if (compiler.getErrorManager().formatWantsSingleLineMessage()) {
      s = s.replace('\n', ' ');
    }
    console.print(s + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
    hasOutput = true;
  }

  @Override
  public void resetStatus() {
  }

  @Override
  public void reportError(@NotNull ResolveMessage message) {
    track(message, ConsoleViewContentType.ERROR_OUTPUT);
  }

  @Override
  public void reportWarning(@NotNull ResolveMessage message) {
    track(message, ConsoleViewContentType.NORMAL_OUTPUT);
  }

  private void track(ResolveMessage msg, ConsoleViewContentType errType) {
    ST msgST = compiler.getErrorManager().getMessageTemplate(msg);
    String outputMsg = msgST.render();
    if (compiler.getErrorManager().formatWantsSingleLineMessage()) {
      outputMsg = outputMsg.replace('\n', ' ');
    }
    console.print(outputMsg + "\n", errType);
    hasOutput = true;
  }
}
