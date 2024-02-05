package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.util.List;

public interface AlgorithmManager {
    boolean isExists(String algorithmName);
    List<Algorithm> findAll();

    void initAll();

    <T  extends Algorithm> List<Algorithm> find(String algorithmName);

    boolean existsCachedLike(String algorithmNameLike);

    List<Algorithm> getCachedLike(String algorithmNameLike);

    void putCache(String algorithmName, List<Algorithm> algorithms);
    <T  extends Algorithm> boolean isCached(String algorithmClass);
    List<Algorithm> getCached(String algorithmName);
    void clearCache();
}
