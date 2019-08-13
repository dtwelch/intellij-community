package com.jetbrains.resolve.runconfig;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.resolve.psi.ResFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolveRunConfigurationProducer
        extends
        RunConfigurationProducer<ResolveRunConfiguration> implements Cloneable {

    protected ResolveRunConfigurationProducer() {
        super(ResolveRunConfigurationType.getInstance());
    }

    @Override
    protected boolean setupConfigurationFromContext(@NotNull ResolveRunConfiguration configuration,
                                                    @NotNull ConfigurationContext context,
                                                    Ref<PsiElement> sourceElement) {
        PsiFile file = getFileFromContext(context);
        if (ResolveRunUtil.isMainRESOLVEFile(file)) {
            configuration.setName(getConfigurationName(file));
            configuration.setFilePath(file.getVirtualFile().getPath());
            return true;
        }
        return false;
    }

    @NotNull
    protected String getConfigurationName(@NotNull PsiFile file) {
        return "RESOLVE " + file.getName();
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull ResolveRunConfiguration configuration,
                                              ConfigurationContext context) {
        ResFile file = getFileFromContext(context);
        return file != null && FileUtil.pathsEqual(configuration.getFilePath(), file.getVirtualFile().getPath());
    }

    @Nullable
    private static ResFile getFileFromContext(@Nullable ConfigurationContext context) {
        PsiElement contextElement = ResolveRunUtil.getContextElement(context);
        PsiFile psiFile = contextElement != null ? contextElement.getContainingFile() : null;
        return psiFile instanceof ResFile ? (ResFile)psiFile : null;
    }
}
