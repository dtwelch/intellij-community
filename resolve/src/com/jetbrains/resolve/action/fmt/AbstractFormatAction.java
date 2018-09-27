package com.jetbrains.resolve.action.fmt;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.jetbrains.resolve.ResolveFileType;
import com.jetbrains.resolve.ResolveStudioController;
import com.jetbrains.resolve.action.ResolveAction;
import com.jetbrains.resolve.action.ResolveValidateAction;
import com.jetbrains.resolve.action.RunResolveListener;
import edu.clemson.resolve.core.control.AbstractUserInterfaceControl;
import edu.clemson.resolve.semantics.SyntaxAwareMathFormatter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFormatAction extends ResolveAction {

  protected boolean isAvailableOnFile(VirtualFile file) {
    return file.getFileType() == ResolveFileType.INSTANCE;
  }

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

  public void doFormat(@Nullable Editor editor, @NotNull Project project,
                       @NotNull VirtualFile fileOrDir,
                       @NotNull AbstractUserInterfaceControl control) {
    ConsoleView console = ResolveStudioController.getInstance(project).getConsole();
    console.clear();
    String timeStamp = getTimeStamp();

    console.print(timeStamp + ": resolve " + "-format " + fileOrDir.getPath() + "\n",
                  ConsoleViewContentType.SYSTEM_OUTPUT);
    RunResolveListener defaultListener = new RunResolveListener(control, console);
    control.addAdditionalCompilerListener(defaultListener);

    SyntaxAwareMathFormatter formatter = new SyntaxAwareMathFormatter(control);
    formatter.formatFileOrDirectory(fileOrDir.getPath());
    if (editor != null) {
      ResolveValidateAction.annotateIssues(editor, fileOrDir, control, null);
    }

    VfsUtil.markDirtyAndRefresh(true, true, true, fileOrDir);
  }

  @NotNull
  public abstract String getTitle();
}
