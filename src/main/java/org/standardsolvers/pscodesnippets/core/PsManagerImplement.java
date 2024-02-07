package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Ps;

import java.util.*;

public class PsManagerImplement implements PsManager {

    static PsManager psManager = new PsManagerImplement();
    PsProvider psProvider = new PsProviderImplement();
    Map<String, List<Ps>> psMap = new HashMap<>();

    private PsManagerImplement(){
        initAll();
    }
    public static PsManager getInstance() {
        return psManager;
    }

    @Override
    public boolean isExists(String psName){
        return isCached(psName);
    }

    @Override
    public List<Ps> findAll() {
        List<Ps> result = new ArrayList<>();
        psMap.values().forEach(result::addAll);
        return result;
    }

    @Override
    public void initAll() {
        psMap = psProvider.findAll();
    }


    @Override
    public List<Ps> find(String psName) {

        if(isCached(psName)){
            return getCached(psName);
        }

        // if not returned
        return getCachedLike(psName);
    }


    @Override
    public boolean existsCachedLike(String psNameLike){
        Set<String> psNames = psMap.keySet();
        for (String name : psNames) {
            if (name.contains(psNameLike)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public List<Ps> getCachedLike(String psNameLike){
        List<Ps> result = new ArrayList<>();
        Set<String> psNames = psMap.keySet();
        for (String name : psNames) {
            if (name.contains(psNameLike)) {
                result.addAll(psMap.get(name));
            }
        }
        return result;
    }

    @Override
    public void putCache(String psName, List<Ps> psList){
        psMap.put(psName, psList);
    }

    @Override
    public boolean isCached(String psName){
        return psMap.containsKey(psName);
    }

    @Override
    public List<Ps> getCached(String psName){
        return psMap.get(psName);
    }

    @Override
    public void clearCache(){
        psMap.clear();
    }
}
