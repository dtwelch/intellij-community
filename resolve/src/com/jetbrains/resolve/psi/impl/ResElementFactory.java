package com.jetbrains.resolve.psi.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.jetbrains.resolve.ResolveLanguage;
import com.jetbrains.resolve.psi.ResFile;
import com.jetbrains.resolve.psi.ResModuleIdentifierSpec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

  @NotNull
  public static List<ResModuleIdentifierSpec> createUsesSpecList(@NotNull Project project, List<String> imports) {
    String joinedList = StringUtil.join(imports, e -> e, ", ");
    ResFile file = createFileFromText(project, "Precis T; uses "  + joinedList + "; end T;");
    return file.getEnclosedModule().getUsesModuleIdentifierSpecs();
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