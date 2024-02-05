package org.standardsolvers.pscodesnippets.intellij.panel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class PsPopUp extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        SampleDialogWrapper dialog = new SampleDialogWrapper();
        dialog.show();
    }
}
