package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.util.List;

public interface AlgorithmManager {
    void initAll();
    List<Algorithm> find(String algorithmName);
    List<Algorithm> findAll();
    List<Algorithm> getCached(String algorithmName);
    List<Algorithm> getCachedLike(String algorithmNameLike);
    boolean isCached(String algorithmClass);
    boolean isExists(String algorithmName);
    boolean existsCachedLike(String algorithmNameLike);
    void putCache(String algorithmName, List<Algorithm> algorithms);
    void clearCache();
}
