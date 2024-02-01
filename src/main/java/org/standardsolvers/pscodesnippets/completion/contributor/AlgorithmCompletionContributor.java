package org.standardsolvers.pscodesnippets.completion.contributor;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import org.standardsolvers.pscodesnippets.core.AlgorithmManager;
import org.standardsolvers.pscodesnippets.core.SimpleAlgorithmManagerImplement;

public class AlgorithmCompletionContributor extends CompletionContributor {
    AlgorithmManager algorithmHelper;

    public AlgorithmCompletionContributor(){
        algorithmHelper = SimpleAlgorithmManagerImplement.getInstance();
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new AlgorithmCompletionProvider<>(algorithmHelper));
    }


}
