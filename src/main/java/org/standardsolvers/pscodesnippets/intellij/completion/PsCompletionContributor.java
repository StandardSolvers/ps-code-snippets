package org.standardsolvers.pscodesnippets.intellij.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import org.standardsolvers.pscodesnippets.core.PsManager;
import org.standardsolvers.pscodesnippets.core.PsManagerImplement;

public class PsCompletionContributor extends CompletionContributor {

    public PsCompletionContributor(){

        PsManager psManager = PsManagerImplement.getInstance();
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new PsCompletionProvider<>(psManager));
    }
}
