package com.jetbrains.resolve.action;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.jetbrains.resolve.ResolveIcons;
import org.jetbrains.annotations.NotNull;

public class CreateResolveFileAction extends CreateFileFromTemplateAction implements DumbAware {

  public CreateResolveFileAction() {
    super("RESOLVE File", "Creates a RESOLVE file from the specified template", ResolveIcons.RESOLVE_FILE);
  }

  @Override
  protected void buildDialog(Project project, PsiDirectory directory, @NotNull CreateFileFromTemplateDialog.Builder builder) {
    builder.setTitle("New RESOLVE file").addKind("Empty file", ResolveIcons.RESOLVE_FILE, "RESOLVE File");
  }

  @NotNull
  @Override
  protected String getActionName(PsiDirectory directory, String newName, String templateName) {
    return "New RESOLVE file";
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof CreateResolveFileAction;
  }
}
