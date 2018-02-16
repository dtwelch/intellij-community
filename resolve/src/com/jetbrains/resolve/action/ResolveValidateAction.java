package com.jetbrains.resolve.action;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseMotionListener;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import com.jetbrains.resolve.ResolveStudioController;
import edu.clemson.resolve.Resolve;
import edu.clemson.resolve.ResolveMessage;
import edu.clemson.resolve.Utils;
import org.antlr.v4.runtime.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.stringtemplate.v4.ST;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResolveValidateAction extends ResolveAction {
  public static final Key<Issue> ISSUE_ANNOTATION = Key.create("ISSUE_ANNOTATION");

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getData(PlatformDataKeys.PROJECT);
    VirtualFile resolveFile = getRESOLVEFileFromEvent(e);
    if (project == null || resolveFile == null) return;

    commitDoc(project, resolveFile);
    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
    if (editor == null) return;

    Map<String, String> argMap = new LinkedHashMap<>();
    argMap.put("", resolveFile.getCanonicalPath());
    //argMap.put("-lib", getContentRoot(project, resolveFile).getPath());
    CompilerIssueListener issueListener = new CompilerIssueListener();

    Resolve compiler = setupAndRunCompiler(project, editor, resolveFile, argMap, issueListener);
    if (compiler.targetModule.hasParseErrors) return;

    annotateIssues(editor, resolveFile, compiler, issueListener);
  }

  @Nullable
  public static Resolve setupAndRunCompiler(@NotNull Project project,
                                            @NotNull Editor editor,
                                            @NotNull VirtualFile targetFile,
                                            @NotNull Map<String, String> args) {
    return setupAndRunCompiler(project, editor, targetFile, args, null);
  }

  @NotNull
  public static Resolve setupAndRunCompiler(@NotNull Project project,
                                                    @NotNull Editor editor,
                                                    @NotNull VirtualFile targetFile,
                                                    @NotNull Map<String, String> args,
                                                    @Nullable Resolve.ResolveListener customListener) {
    Resolve compiler = getDefaultCompiler(args);
    ConsoleView console = ResolveStudioController.getInstance(project).getConsole();
    console.clear();
    String timeStamp = getTimeStamp();
    console.print(timeStamp + ": resolve " + Utils.join(getArgMapAsList(args), " ") + "\n",
                  ConsoleViewContentType.SYSTEM_OUTPUT);

    RunResolveListener defaultListener = new RunResolveListener(compiler, console);
    compiler.removeListeners();
    compiler.addListener(defaultListener);
    if (customListener != null) compiler.addListener(customListener);
    try {
      ProgressManager.getInstance().run(new Task.WithResult<Boolean, Exception>(
        project, "Analyzing Current Source", false) {
        @Override
        protected Boolean compute(@NotNull ProgressIndicator indicator) throws Exception {
          compiler.processCommandLineTarget();
          return compiler.errMgr.getErrorCount() == 0;
        }
      });
    }
    catch (Throwable e) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      String msg = sw.toString();
      Notification notification =
        new Notification("AnalyzeAction", "failed to execute " + targetFile.getPath(),
                         e.toString(),
                         NotificationType.INFORMATION);
      Notifications.Bus.notify(notification, project);
      console.print(timeStamp + ": resolve " + msg + "\n", ConsoleViewContentType.SYSTEM_OUTPUT);
      defaultListener.hasOutput = true; // show console below
    }
    if (defaultListener.hasOutput) {
      ResolveStudioController.showConsoleWindow(project);
    }
    return compiler;
  }

  public static void annotateIssues(Editor editor,
                                    VirtualFile targetFile,
                                    Resolve compiler,
                                    CompilerIssueListener issueListener) {
    editor.getMarkupModel().removeAllHighlighters();    //first

    EditorMouseMotionListener mouseListener = new EditorMouseMotionListener() {
      @Override
      public void mouseMoved(EditorMouseEvent e) {
        int offset = MyActionUtils.getMouseOffset(e.getMouseEvent(), editor);
        if (offset >= editor.getDocument().getTextLength()) return;
        List<RangeHighlighter> issueHighlightersAtOffset =
          MyActionUtils.getRangeHighlightersAtOffset(editor, HighlighterLayer.ERROR, offset);
        if (issueHighlightersAtOffset.size() == 0) return;
        List<String> msgs = new ArrayList<String>();

        for (RangeHighlighter highlighter : issueHighlightersAtOffset) {
          Issue errorUnderCursor = highlighter.getUserData(ISSUE_ANNOTATION);
          if (errorUnderCursor == null || errorUnderCursor.msg == null) continue;
          String errorMsg = getIssueDisplayString(compiler, errorUnderCursor);
          if (errorMsg != null) msgs.add(errorMsg);
        }
        String combinedErrorMsg = Utils.join(msgs, "\n");
        showErrorToolTip(editor, offset, HintManager.getInstance(), combinedErrorMsg, e);
      }
      @Override
      public void mouseDragged(EditorMouseEvent e) {
      }
    };
    List<RangeHighlighter> issueRelatedHighlighters = new ArrayList<>();
    for (Issue issue : issueListener.issues) {
      annotateIssueInEditor(targetFile, issueRelatedHighlighters, editor, issue);
    }

    editor.addEditorMouseMotionListener(mouseListener);
    editor.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void beforeDocumentChange(DocumentEvent event) {
      }

      @Override
      public void documentChanged(DocumentEvent event) {
        //remove all current issue related highlighters
        MarkupModel markupModel = editor.getMarkupModel();
        for (RangeHighlighter h : issueRelatedHighlighters) {
          int eventOffset = event.getOffset();
          int highlightersOffset = h.getStartOffset();
          if (eventOffset >= h.getStartOffset() && eventOffset < h.getEndOffset()) {
            h.getTextAttributes().setEffectColor(JBColor.ORANGE);
            // editor.getMarkupModel().removeHighlighter(h);
          }
        }
      }
    });

  }

  public static String getIssueDisplayString(Resolve compiler, Issue e) {
    ST st = compiler.errMgr.getMessageTemplate(e.msg);
    return st.render();
  }

  public static void showErrorToolTip(Editor editor,
                                      int offset, HintManager hintMgr,
                                      String msg,
                                      EditorMouseEvent event) {
    int flags = HintManager.HIDE_BY_ANY_KEY | HintManager.HIDE_BY_TEXT_CHANGE | HintManager.HIDE_BY_SCROLLING;
    int timeout = 0; // default?
    hintMgr.showErrorHint(editor, msg, offset, offset + 1, HintManager.ABOVE, flags, timeout);
  }

  public static void annotateIssueInEditor(@NotNull VirtualFile file,
                                           @NotNull List<RangeHighlighter> highlighters,
                                           @NotNull Editor editor,
                                           @NotNull Issue issue) {
    MarkupModel markupModel = editor.getMarkupModel();  //ref to the current editor's markup model...
    final TextAttributes attr = new TextAttributes();

    Token offendingToken = issue.msg.offendingToken;
    int a = offendingToken.getStartIndex();
    int b = offendingToken.getStopIndex() + 1;

    if (issue.msg instanceof ResolveMessage.LanguageSemanticsMessage) {
      if (issue.msg.getErrorType().severity == ResolveMessage.ErrorSeverity.ERROR) {
        attr.setForegroundColor(JBColor.RED);
        attr.setEffectColor(JBColor.RED);
        attr.setEffectType(EffectType.WAVE_UNDERSCORE);
      }
      else {  //warning (should be yellowish or something)
        attr.setBackgroundColor(new JBColor(new Color(246, 235, 188),
                                            new Color(246, 235, 188)));
        attr.setEffectType(EffectType.BOXED);
      }
    }
    String sourceName = offendingToken.getTokenSource().getSourceName();
    String vFilePath = file.getPath();
    if (vFilePath.equals(sourceName)) { //only want highlights in the doc the user is looking at.
      RangeHighlighter highlighter = markupModel.addRangeHighlighter(a,
                                                                     b,
                                                                     HighlighterLayer.ERROR, // layer
                                                                     attr,
                                                                     HighlighterTargetArea.EXACT_RANGE);
      highlighters.add(highlighter);
      highlighter.putUserData(ISSUE_ANNOTATION, issue);
    }
  }

  public static class Issue {
    String annotation;
    ResolveMessage msg;
    public Issue(ResolveMessage msg) { this.msg = msg; }
  }
}
