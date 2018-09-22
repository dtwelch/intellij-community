package com.jetbrains.resolve.action;

import edu.clemson.resolve.core.ResolveCompilerListener;
import edu.clemson.resolve.core.ResolveMessage;
import org.jetbrains.annotations.NotNull;

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

  @Override
  public void reportStatus(@NotNull String s) {

  }

  @Override
  public void resetStatus() {

  }

  @Override
  public void reportError(@NotNull ResolveMessage message) {

  }

  @Override
  public void reportWarning(@NotNull ResolveMessage message) {

  }
  /*private final Resolve compiler;
  private final ConsoleView console;
  public boolean hasOutput = false;

  public RunResolveListener(@NotNull Resolve compiler,
                            @NotNull ConsoleView console) {
    this.compiler = compiler;
    this.console = console;
  }

  @Override
  public void info(String msg) {
    if (compiler.errMgr.formatWantsSingleLineMessage()) {
      msg = msg.replace('\n', ' ');
    }
    console.print(msg + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
    hasOutput = true;
  }

  @Override
  public void error(ResolveMessage msg) {
    track(msg, ConsoleViewContentType.ERROR_OUTPUT);
  }

  @Override
  public void warning(ResolveMessage msg) {
    track(msg, ConsoleViewContentType.NORMAL_OUTPUT);
  }

  private void track(ResolveMessage msg, ConsoleViewContentType errType) {
    ST msgST = compiler.errMgr.getMessageTemplate(msg);
    String outputMsg = msgST.render();
    if (compiler.errMgr.formatWantsSingleLineMessage()) {
      outputMsg = outputMsg.replace('\n', ' ');
    }
    console.print(outputMsg + "\n", errType);
    hasOutput = true;
  }*/
}
