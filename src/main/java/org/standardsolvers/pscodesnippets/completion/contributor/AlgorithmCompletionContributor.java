package org.standardsolvers.pscodesnippets.completion.contributor;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import org.standardsolvers.pscodesnippets.core.AlgorithmManager;
import org.standardsolvers.pscodesnippets.core.SimpleAlgorithmManagerImplement;

public class AlgorithmCompletionContributor extends CompletionContributor {

    public AlgorithmCompletionContributor(){
        AlgorithmManager algorithmManager = SimpleAlgorithmManagerImplement.getInstance();
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new AlgorithmCompletionProvider<>(algorithmManager));
    }
}
