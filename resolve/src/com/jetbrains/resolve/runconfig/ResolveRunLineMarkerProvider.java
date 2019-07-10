package com.jetbrains.resolve.runconfig;

import com.intellij.execution.lineMarker.ExecutorAction;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.Function;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.psi.ResOperationLikeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolveRunLineMarkerProvider extends RunLineMarkerContributor {

    private static final Function<PsiElement, String> TOOLTIP_PROVIDER = new Function<PsiElement, String>() {
        @Override
        public String fun(PsiElement element) {
            return "Run Program";
        }
    };

    @Nullable
    @Override
    public Info getInfo(@NotNull PsiElement e) {
        if (e.getNode().getElementType() == ResTypes.IDENTIFIER) {
            PsiElement parent = e.getParent();
            PsiFile file = e.getContainingFile();
            if (ResolveRunUtil.isMainRESOLVEFile(file) && parent instanceof ResOperationLikeNode) {
                String name = ((ResOperationLikeNode)parent).getName();
                if (name!= null && name.equalsIgnoreCase("main")) {
                    return new Info(AllIcons.RunConfigurations.TestState.Run, TOOLTIP_PROVIDER,
                                    ExecutorAction.getActions(0)[0]);
                }
            }
        }
        return null;
    }
}
