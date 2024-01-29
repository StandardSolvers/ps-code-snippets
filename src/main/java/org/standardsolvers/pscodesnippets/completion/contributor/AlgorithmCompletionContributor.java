package org.standardsolvers.pscodesnippets.completion.contributor;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class AlgorithmCompletionContributor extends CompletionContributor {
    public AlgorithmCompletionContributor(){
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        CaretModel caretModel = parameters.getEditor().getCaretModel();
                        int a = caretModel.getCaretCount();
                        int b = caretModel.getOffset();
                        String codeBeforeCaret = parameters.getEditor().getDocument().getText(new TextRange(caretModel.getStartOffset() - caretModel.getCaretCount(), caretModel.getOffset()));
                        System.out.println("aaa");
                        System.out.println(parameters);
                        System.out.println(context);
                        System.out.println(resultSet);

                        if (codeBeforeCaret.endsWith("Hello")) {
                            resultSet.addElement(LookupElementBuilder.create("Hello World"));
                        }

                    }
                }
        );
    }
}
