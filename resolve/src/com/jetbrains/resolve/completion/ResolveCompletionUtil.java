package com.jetbrains.resolve.completion;

import com.intellij.codeInsight.completion.PrefixMatcher;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiDirectory;
import com.intellij.util.PlatformIcons;
import com.jetbrains.resolve.psi.ResFile;
import org.jetbrains.annotations.NotNull;

public class ResolveCompletionUtil {

  public static final int VAR_PRIORITY = 30;
  public static final int FUNCTION_PRIORITY = 15;
  public static final int FACILITY_PRIORITY = 5;
  public static final int DEFINITION_PRIORITY = 10;
  public static final int TYPE_PRIORITY = 20;
  public static final int KEYWORD_PRIORITY = 1;

  private static class Lazy {
    private static final QualifierInsertHandler FACILITY_OR_MODULE_INSERT_HANDLER =
      new QualifierInsertHandler("::", true);
  }

  @NotNull
  public static LookupElementBuilder createDirectoryLookupElement(@NotNull PsiDirectory directory) {
    return LookupElementBuilder.createWithSmartPointer(directory.getName(), directory)
      .withIcon(PlatformIcons.DIRECTORY_CLOSED_ICON);
  }

  @NotNull
  public static LookupElementBuilder createResolveFileLookupElement(@NotNull ResFile resolveFile) {
    return createResolveFileLookupElement(resolveFile, false);
  }

  @NotNull
  static PrefixMatcher createPrefixMatcher(@NotNull PrefixMatcher original) {
    return new CamelHumpMatcher(original.getPrefix(), true);
  }

  @NotNull
  public static LookupElementBuilder createResolveFileLookupElement(@NotNull ResFile resolveFile, boolean forTypes) {
    String name = resolveFile.getVirtualFile().getNameWithoutExtension();
    LookupElementBuilder result = LookupElementBuilder.create(name).withIcon(resolveFile.getIcon(0));
    if (forTypes) {
      result = result.withInsertHandler(Lazy.FACILITY_OR_MODULE_INSERT_HANDLER);
    }
    return result;
  }
}
