package org.standardsolvers.pscodesnippets.intellij.completion.contributor;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import org.standardsolvers.pscodesnippets.core.AlgorithmManager;
import org.standardsolvers.pscodesnippets.core.AlgorithmManagerImplement;

public class AlgorithmCompletionContributor extends CompletionContributor {

    public AlgorithmCompletionContributor(){

        


        AlgorithmManager algorithmManager = AlgorithmManagerImplement.getInstance();
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new AlgorithmCompletionProvider<>(algorithmManager));


    }
}
