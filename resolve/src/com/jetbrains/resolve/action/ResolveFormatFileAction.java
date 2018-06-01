package com.jetbrains.resolve.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import edu.clemson.resolve.Resolve;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class ResolveFormatFileAction extends ResolveFormatAction {

  @Override
  public void update(@NotNull AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || file == null || !file.isInLocalFileSystem() || !isAvailableOnFile(file)) {
      e.getPresentation().setEnabled(false);
      return;
    }
    e.getPresentation().setEnabled(true);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    String title = StringUtil.notNullize(e.getPresentation().getText());
    Project project = e.getProject();
    VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || !isAvailableOnFile(file)) return;

    commitDoc(project, file);
    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
    if (editor == null) return;

    Map<String, String> argMap = getArgMap(e);
    CompilerIssueListener issueListener = new CompilerIssueListener();

    Resolve compiler = ResolveValidateAction.setupAndRunCompiler(project, editor, file, argMap, issueListener);
    if (compiler.targetModule.hasParseErrors) return;
    ResolveValidateAction.annotateIssues(editor, file, compiler, issueListener);

    ProgressManager.getInstance().run(new Task.Backgroundable(project, "RESOLVE Sugar", true) {

      @Override
      public void run(@NotNull ProgressIndicator indicator) {
        compiler.processCommandLineTarget();
        return compiler.errMgr.getErrorCount() == 0;
      }
    });


    try {
      doSomething(file, module, project, title);
    }
    catch (ExecutionException ex) {
      error(title, project, ex);
      LOGGER.error(ex);
    }
  }

}
