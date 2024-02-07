package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Ps;

import java.util.List;

public interface PsManager {
    void initAll();
    List<Ps> find(String psName);
    List<Ps> findAll();
    List<Ps> getCached(String psName);
    List<Ps> getCachedLike(String psNameLike);
    boolean isCached(String psClass);
    boolean isExists(String psName);
    boolean existsCachedLike(String psNameLike);
    void putCache(String psName, List<Ps> psList);
    void clearCache();
}
