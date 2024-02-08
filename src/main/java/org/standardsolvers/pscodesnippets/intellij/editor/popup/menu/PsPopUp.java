package org.standardsolvers.pscodesnippets.intellij.editor.popup.menu;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.project.Project;
import org.standardsolvers.pscodesnippets.intellij.dialog.wrapper.PsDialogWrapper;

public class PsPopUp extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        PsDialogWrapper dialog = new PsDialogWrapper();
        dialog.show();
        String context = dialog.getContext();
        Project project = e.getProject();
        Editor editor = (Editor) e.getDataContext().getData("editor");
        int offset = editor.getCaretModel().getOffset();
        WriteCommandAction.runWriteCommandAction(project, () -> {
            editor.getDocument().insertString(offset, context);
        });
    }
}
