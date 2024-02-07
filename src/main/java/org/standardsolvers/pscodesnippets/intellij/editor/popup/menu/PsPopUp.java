package org.standardsolvers.pscodesnippets.intellij.editor.popup.menu;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.standardsolvers.pscodesnippets.intellij.dialog.wrapper.PsDialogWrapper;

public class PsPopUp extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        PsDialogWrapper dialog = new PsDialogWrapper();
        dialog.show();
    }
}
