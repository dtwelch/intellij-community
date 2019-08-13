package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.jetbrains.resolve.ResTypes;
import com.jetbrains.resolve.psi.ResEnsuresClause;
import com.jetbrains.resolve.psi.ResOperationLikeNode;
import com.jetbrains.resolve.psi.ResProgSymbolName;
import com.jetbrains.resolve.psi.ResRequiresClause;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ResAbstractOperationLikeNode extends ResNamedElementImpl implements ResOperationLikeNode {

    public ResAbstractOperationLikeNode(@NotNull ASTNode node) {
        super(node);
    }

    //TODO: Have a method that gets the alt pretty syntax name, then override getNameIdentifier here and return that
    //instead
    @Nullable
    public ResProgSymbolName getSugaredName() {
        return findChildByType(ResTypes.PROG_SYMBOL_NAME);
    }

    @NotNull
    @Override
    public PsiElement getIdentifier() {
        PsiElement result = getSugaredName();
        if (result == null) {
            result = findNotNullChildByType(ResTypes.IDENTIFIER);
        }
        return result;
    }

    //TODO:
    //yes, these will be null for procedure decls, though technically those guys shouldn't even extend ResAbstractOperationLikeNode
    //or implement (ResOperationLikeNode) for that matter.
    @Nullable
    public ResRequiresClause getRequiresClause() {
        return findChildByClass(ResRequiresClause.class);
    }

    @Nullable
    public ResEnsuresClause getEnsuresClause() {
        return findChildByClass(ResEnsuresClause.class);
    }
}
