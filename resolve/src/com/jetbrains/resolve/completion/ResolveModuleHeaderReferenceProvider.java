package com.jetbrains.resolve.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.options.ex.Settings;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ResolveModuleHeaderReferenceProvider extends CompletionProvider<CompletionParameters> {

  //Take a look at GoImportPathsCompletionProvider and GoAutoImportCompletionContributor for tips on using the global
  //scope searching stuff... though somehow
  //I still would like to be able to reference things and complete moduleIdentifierSpecs listed in the uses clause...
  @Override
  protected void addCompletions(@NotNull CompletionParameters parameters,
                                ProcessingContext context,
                                @NotNull CompletionResultSet result) {
    int i;
    i=0;

  }
}
