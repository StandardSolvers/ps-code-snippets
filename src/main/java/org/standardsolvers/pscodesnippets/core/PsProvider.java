package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Ps;
import org.standardsolvers.pscodesnippets.solution.SolutionStatement;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface PsProvider {
    Package psPack = Ps.class.getPackage();
    Package statementPack = SolutionStatement.class.getPackage();

    PsProvider initPsClassMap();
    PsProvider initSolutionStatementClassMap();
    PsProvider cachePsClassMapByList(List<Class<? extends Ps>> psClassList);
    PsProvider tryCacheSolutionStatementClassByList(List<Class<?>> solutionStatementClassList);

    Map<String, List<Ps>> findAll();
    Set<Class<? extends Ps>> findAllPsClass();
    List<Class<?>> findAllSolutionStatementClass();
    Optional<Ps> constructPs(Class<? extends Ps> psClass);
}
