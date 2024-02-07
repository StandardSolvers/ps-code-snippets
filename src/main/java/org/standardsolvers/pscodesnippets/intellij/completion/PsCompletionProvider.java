package org.standardsolvers.pscodesnippets.intellij.completion;

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
import org.standardsolvers.pscodesnippets.core.PsManager;
import org.standardsolvers.pscodesnippets.solution.Ps;

import java.util.List;

public class PsCompletionProvider<V extends CompletionParameters> extends CompletionProvider<V>{

    final PsManager psManager;

    public PsCompletionProvider(PsManager psManager) {
        this.psManager = psManager;
    }

    @Override
    protected void addCompletions(@NotNull V parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet resultSet) {
        String codeBeforeCaret = codeBeforeCaret(parameters.getEditor());

        if(psManager.isExists(codeBeforeCaret) || psManager.existsCachedLike(codeBeforeCaret)){
          
            List<Ps> psList = psManager.find(codeBeforeCaret);

            psList.forEach(ps -> {
                String replacement = ps.getContext();
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
