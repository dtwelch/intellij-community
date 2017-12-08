package com.jetbrains.resolve.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.resolve.ResolveFileType;
import com.jetbrains.resolve.ResolveIcons;
import com.jetbrains.resolve.ResolveLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ResFile extends PsiFileBase {

    public ResFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, ResolveLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ResolveFileType.INSTANCE;
    }

    @NotNull
    @Override
    public PsiElement[] getChildren() {
        return super.getChildren();
    }

    @Nullable
    public ResModuleDecl getEnclosedModule() {
        return PsiTreeUtil.findChildOfType(this, ResModuleDecl.class);
    }

    @Override
    public Icon getIcon(int s) {
        if (getEnclosedModule() == null) {
            return ResolveIcons.RESOLVE_FILE;
        }
        else {
            return getEnclosedModule().getIcon(0);
        }
    }

}
