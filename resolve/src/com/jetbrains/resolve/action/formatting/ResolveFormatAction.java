package com.jetbrains.resolve.action.formatting;

import com.intellij.execution.ExecutionException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.util.Consumer;
import edu.clemson.resolve.Resolve;
import edu.clemson.resolve.ResolveMessage;
import com.jetbrains.resolve.ResolveFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ResolveFormatAction extends DumbAwareAction {

  protected boolean isAvailableOnFile(VirtualFile file) {
    return file.getFileType() == ResolveFileType.INSTANCE;
  }
/*
  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    String title = StringUtil.notNullize(e.getPresentation().getText());
    Project project = e.getProject();
    VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
    if (project == null || !isAvailableOnFile(file)) return;

    commitDoc(project, file);
    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
    if (editor == null) return;

    Map<String, String> argMap = new LinkedHashMap<>();
    argMap.put("", file.getCanonicalPath());
    CompilerIssueListener issueListener = new CompilerIssueListener();
/*
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
  }*/

  @NotNull
  public abstract List<String> getArguments(@NotNull AnActionEvent e);

  /**
   * Somewhat longhanded way of saving the document (see go-plugin impl of gofmt)
   */
  void commitDoc(Project project, VirtualFile file) {
    PsiDocumentManager psiMgr = PsiDocumentManager.getInstance(project);
    FileDocumentManager docMgr = FileDocumentManager.getInstance();
    Document doc = docMgr.getDocument(file);
    if (doc == null) return;

    boolean unsaved = !psiMgr.isCommitted(doc) || docMgr.isDocumentUnsaved(doc);
    if (unsaved) {
      psiMgr.commitDocument(doc);
      docMgr.saveDocument(doc);
    }
  }

}
