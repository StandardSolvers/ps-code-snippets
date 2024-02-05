package org.standardsolvers.pscodesnippets.intellij.completion.contributor;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.standardsolvers.pscodesnippets.core.AlgorithmManager;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.util.List;

public class AlgorithmCompletionProvider<V extends CompletionParameters> extends CompletionProvider<V>{

    final AlgorithmManager algorithmManager;

    public AlgorithmCompletionProvider(AlgorithmManager algorithmManager) {
        this.algorithmManager = algorithmManager;
    }

    @Override
    protected void addCompletions(@NotNull V parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet resultSet) {
        String codeBeforeCaret = codeBeforeCaret(parameters.getEditor());

        if(algorithmManager.isExists(codeBeforeCaret) || algorithmManager.existsCachedLike(codeBeforeCaret)){
          
            List<Algorithm> algorithmList = algorithmManager.find(codeBeforeCaret);

            algorithmList.forEach(algorithm -> {
                String replacement = algorithm.getContext();
                LookupElement lookupElement = LookupElementBuilder.create(codeBeforeCaret +": "+ replacement)
                        .withInsertHandler((context1, item) -> {
                            Document document = context1.getDocument();
                            document.deleteString(context1.getStartOffset(), context1.getTailOffset());
                            document.insertString(context1.getStartOffset(), replacement);
                        });
                resultSet.addElement(lookupElement);
            });
        }
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
