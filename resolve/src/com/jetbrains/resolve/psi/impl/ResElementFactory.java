package com.jetbrains.resolve.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.jetbrains.resolve.ResolveLanguage;
import com.jetbrains.resolve.psi.ResFile;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ConstantConditions")
public class ResElementFactory {

  private ResElementFactory() {
  }

  @NotNull
  private static ResFile createFileFromText(@NotNull Project project, @NotNull String text) {
    return (ResFile)PsiFileFactory.getInstance(project)
      .createFileFromText("a.resolve", ResolveLanguage.INSTANCE, text);
  }

  @NotNull
  public static PsiElement createIdentifierFromText(@NotNull Project project, String text) {
    ResFile file = createFileFromText(project, "Precis " + text + ";end " + text + ";");
    return file.getEnclosedModule().getIdentifier();
  }

  //TODO: I don't want this to be navigatble.. Figure out how to accomplish this. (or at least make the module
  //navigatable, but obfuscate the body)
   /* @NotNull
    public static ResFile getHardCodedMathFile(@NotNull Project project) {
        final String hardcoded =
                "Precis Builtin_Class_Theory;\n " +
                    "Definition Cls : 𝒫(ℳℰ)\n" +
                "end Builtin_Class_Theory;";
        return createFileFromText(project, hardcoded);
    }*/
}