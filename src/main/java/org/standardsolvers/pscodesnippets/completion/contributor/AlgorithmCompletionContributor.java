package org.standardsolvers.pscodesnippets.completion.contributor;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.standardsolvers.pscodesnippets.core.AlgorithmHelper;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.util.List;
import java.util.Optional;

public class AlgorithmCompletionContributor extends CompletionContributor {
    AlgorithmHelper algorithmHelper;

    public AlgorithmCompletionContributor(){
        algorithmHelper = AlgorithmHelper.getInstance();

        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        String codeBeforeCaret = codeBeforeCaret(parameters.getEditor());

                        if(algorithmHelper.isExists(codeBeforeCaret)){
                            List<Algorithm> algorithmList = algorithmHelper.find(codeBeforeCaret);

                            algorithmList.forEach(algorithm -> {
                                String replacement = algorithm.getContext();
                                LookupElement lookupElement = LookupElementBuilder.create(codeBeforeCaret +": "+ replacement)
                                        .withInsertHandler((context1, item) -> {
                                            Document document = context1.getDocument();
                                            document.deleteString(context1.getStartOffset(), context1.getTailOffset()+2);
                                            document.insertString(context1.getStartOffset(), replacement);
                                        });
                                resultSet.addElement(lookupElement);
                            });
                        }
                    }
                }
        );
    }

    String codeBeforeCaret(Editor editor){
        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();

        int endOffset = caretModel.getOffset();

        int startOffset = endOffset - 1;
        while (startOffset >= 0 && !Character.isWhitespace(document.getText().charAt(startOffset))) {
            startOffset--;
        }

        return document.getText(new TextRange(startOffset + 1, endOffset));
    }
}
