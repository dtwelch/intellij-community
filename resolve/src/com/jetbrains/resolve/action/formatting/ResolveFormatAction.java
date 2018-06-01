package com.jetbrains.resolve.action.formatting;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.jetbrains.resolve.ResolveFileType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ResolveFormatAction extends DumbAwareAction {

  protected boolean isAvailableOnFile(VirtualFile file) {
    return file.getFileType() == ResolveFileType.INSTANCE;
  }

  @NotNull
  public abstract List<String> getArguments(@NotNull String fileName);

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

  @NotNull
  public abstract String getTitle();
}
