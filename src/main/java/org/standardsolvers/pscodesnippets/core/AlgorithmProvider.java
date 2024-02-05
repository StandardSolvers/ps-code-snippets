package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;
import org.standardsolvers.pscodesnippets.solution.SolutionStatement;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface AlgorithmProvider {
    Package algorithmPack = Algorithm.class.getPackage();
    Package statementPack = SolutionStatement.class.getPackage();

    AlgorithmProviderImplement initAlgorithmClassMap();

    AlgorithmProviderImplement initSolutionStatementClassMap();

    List<String> findAlgorithmFullClassName(String algorithmName);

    List<Algorithm> find(String algorithmName);

    Map<String, List<Algorithm>> findAll();

    Optional<Algorithm> constructAlgorithm(Class<? extends Algorithm> algorithmClass);

    Set<Class<? extends Algorithm>> findAllAlgorithmClass();

    List<Class<?>> findAllSolutionStatementClass();
}
