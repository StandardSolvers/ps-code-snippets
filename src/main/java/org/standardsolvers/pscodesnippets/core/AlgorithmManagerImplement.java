package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.util.*;

public class AlgorithmManagerImplement implements AlgorithmManager {

    static AlgorithmManager algorithmManager = new AlgorithmManagerImplement();
    AlgorithmProvider algorithmProvider = new AlgorithmProviderImplement();
    Map<String, List<Algorithm>> algorithmMap = new HashMap<>();

    private AlgorithmManagerImplement(){
        initAll();
    }
    public static AlgorithmManager getInstance() {
        return algorithmManager;
    }

    @Override
    public boolean isExists(String algorithmName){
        if(isCached(algorithmName)){
            return true;

        } else {
            String algorithmClassName = algorithmName + "Algorithm";
            List<String> algorithmNameList = algorithmProvider.findAlgorithmFullClassName(algorithmClassName);
            return !algorithmNameList.isEmpty();

        }
    }

    @Override
    public List<Algorithm> findAll() {
        List<Algorithm> result = new ArrayList<>();
        algorithmMap.values().forEach(result::addAll);
        return result;
    }

    @Override
    public void initAll() {
        Map<String, List<Algorithm>> foundedMap = algorithmProvider.findAll();
        algorithmMap = foundedMap;
    }


    @Override
    public <T  extends Algorithm> List<Algorithm> find(String algorithmName) {
        String algorithmClassName = algorithmName + "Algorithm";

        if(isCached(algorithmName)){
            return getCached(algorithmName);

        } else {
            List<Algorithm> result = algorithmProvider.find(algorithmClassName);
            if(!result.isEmpty()){
                putCache(algorithmName, result);
                return result;
            }
        }

        // if not returned
        return getCachedLike(algorithmName);
    }


    @Override
    public boolean existsCachedLike(String algorithmNameLike){
        Set<String> algorithmNames = algorithmMap.keySet();
        for (String name : algorithmNames) {
            if (name.contains(algorithmNameLike)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public List<Algorithm> getCachedLike(String algorithmNameLike){
        List<Algorithm> result = new ArrayList<>();
        Set<String> algorithmNames = algorithmMap.keySet();
        for (String name : algorithmNames) {
            if (name.contains(algorithmNameLike)) {
                result.addAll(algorithmMap.get(name));
            }
        }
        return result;
    }

    @Override
    public void putCache(String algorithmName, List<Algorithm> algorithms){
        algorithmMap.put(algorithmName, algorithms);
    }

    @Override
    public <T  extends Algorithm> boolean isCached(String algorithmName){
        return algorithmMap.containsKey(algorithmName);
    }

    @Override
    public List<Algorithm> getCached(String algorithmName){
        return algorithmMap.get(algorithmName);
    }

    @Override
    public void clearCache(){
        algorithmMap.clear();
    }
}
