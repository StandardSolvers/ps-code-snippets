package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.util.List;
import java.util.Optional;

public interface AlgorithmManager {
    boolean isExists(String algorithmName);
    <T  extends Algorithm> List<Algorithm> find(String algorithmName);
    <T  extends Algorithm> Optional<Algorithm> find(Class<T> algorithmClass);
    <T  extends Algorithm> boolean isCached(Class<T> algorithmClass);
    void clearCache();
}
