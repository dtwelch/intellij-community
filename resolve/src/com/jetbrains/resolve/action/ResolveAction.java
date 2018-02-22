package com.jetbrains.resolve.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.util.ArrayUtil;
import com.jetbrains.resolve.ResolveFileType;
import edu.clemson.resolve.Resolve;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

abstract class ResolveAction extends AnAction implements DumbAware {

    @Override
    public void update(AnActionEvent e) {
        setPresentationVisible(e);
    }

    private void setPresentationVisible(@NotNull AnActionEvent e) {
        VirtualFile vfile = getRESOLVEFileFromEvent(e);
        if (vfile == null || e.getProject() == null) {
            e.getPresentation().setEnabled(false);
            return;
        }
        e.getPresentation().setEnabled(true);
        e.getPresentation().setVisible(true);
    }

    @Nullable
    protected VirtualFile getRESOLVEFileFromEvent(@NotNull AnActionEvent e) {
        VirtualFile[] files = LangDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
        if (files == null || files.length == 0) return null;
        VirtualFile vfile = files[0];
        if (vfile != null && vfile.getName().endsWith("." + ResolveFileType.INSTANCE.getDefaultExtension())) {
            return vfile;
        }
        return null;
    }

    @NotNull
    public static Resolve getDefaultCompiler(@NotNull Map<String, String> argMap) {
        return getDefaultCompiler(getArgMapAsList(argMap));
    }

    @NotNull
    public static Resolve getDefaultCompiler(@NotNull List<String> args) {
        return new Resolve(ArrayUtil.toStringArray(args));
    }

    @NotNull
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    @NotNull
    public static VirtualFile getContentRoot(@NotNull Project project, @NotNull VirtualFile vfile) {
        VirtualFile root = ProjectRootManager.getInstance(project).getFileIndex().getContentRootForFile(vfile);
        if (root != null) return root;
        return vfile.getParent();
    }

    @NotNull
    public static List<String> getArgMapAsList(@NotNull Map<String, String> argMap) {
        List<String> result = new ArrayList<>();
        for (String option : argMap.keySet()) {
            if (option.length() != 0) result.add(option);
            String value = argMap.get(option);
            if (value.length() != 0) result.add(value);
        }
        return result;
    }

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
