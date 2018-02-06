package com.jetbrains.resolve.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.ElementBase;
import com.intellij.ui.RowIcon;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.PlatformIcons;
import com.jetbrains.resolve.ResolveIcons;
import com.jetbrains.resolve.psi.ResCompositeElement;
import com.jetbrains.resolve.psi.ResNamedElement;
import com.jetbrains.resolve.psi.ResPrecisModuleDecl;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public abstract class ResNamedElementImpl extends ResCompositeElementImpl
    implements ResCompositeElement, ResNamedElement {

  public ResNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public PsiElement setName(@NonNls @NotNull String newName) throws IncorrectOperationException {
    return this;
  }

  @Nullable
  @Override
  public Icon getIcon(int flags) {
    Icon icon = null;
    if (this instanceof ResPrecisModuleDecl) icon = ResolveIcons.PRECIS;
    /*else if (this instanceof ResPrecisExtensionModuleDecl) icon = RESOLVEIcons.PRECIS_EXT;
    else if (this instanceof ResConceptModuleDecl) icon = RESOLVEIcons.CONCEPT;
    else if (this instanceof ResConceptExtensionModuleDecl) icon = RESOLVEIcons.CONCEPT_EXT;
    else if (this instanceof ResImplModuleDecl) icon = RESOLVEIcons.IMPL;*/
    //else if (this instanceof ResFacilityModuleDecl) icon = ResolveIcons.FACILITY;
    /*else if (this instanceof ResTypeModelDecl) icon = RESOLVEIcons.TYPE_MODEL;
    else if (this instanceof ResTypeReprDecl) icon = RESOLVEIcons.TYPE_REPR;
    else if (this instanceof ResFacilityDecl) icon = RESOLVEIcons.FACILITY;
    else if (this instanceof ResTypeParamDecl) icon = RESOLVEIcons.GENERIC_TYPE;
    else if (this instanceof ResMathVarDef) icon = RESOLVEIcons.VARIABLE;
    else if (this instanceof ResOperationDecl) icon = RESOLVEIcons.FUNCTION_DECL;
    else if (this instanceof ResOperationProcedureDecl) icon = RESOLVEIcons.FUNCTION_IMPL;
    else if (this instanceof ResProcedureDecl) icon = RESOLVEIcons.FUNCTION_IMPL;
    else if (this instanceof ResParamDef) icon = RESOLVEIcons.PARAMETER;*/
    //TODO: complete the icon list here as you go along

    if (icon != null) {
      /*if ((flags & Iconable.ICON_FLAG_VISIBILITY) != 0) {
        RowIcon rowIcon = ElementBase.createLayeredIcon(this, icon, flags);
        rowIcon.setIcon(isUsesClauseVisible() ? PlatformIcons.PUBLIC_ICON : PlatformIcons.PRIVATE_ICON, 1);
        return rowIcon;
      }*/
      return icon;
    }
    return super.getIcon(flags);
  }
}
