package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;
import org.standardsolvers.pscodesnippets.solution.dijkstra.DijkstraAlgorithm;
import org.standardsolvers.pscodesnippets.solution.disjoint.DisjointSetAlgorithm;
import org.standardsolvers.pscodesnippets.solution.sample.SampleAlgorithm;

import java.io.IOException;
import java.util.*;

public class AlgorithmManagerImplement implements AlgorithmManager {

    static AlgorithmManager algorithmManager = new AlgorithmManagerImplement();
    AlgorithmProvider algorithmProvider = new AlgorithmProviderImplement();
    Map<String, List<Algorithm>> algorithmMap = new HashMap<>();

    private AlgorithmManagerImplement(){}
    public static AlgorithmManager getInstance() {
        return algorithmManager;
    }

    @Override
    public boolean isExists(String algorithmName){
        if(isCached(algorithmName)){
            return true;

        } else try {
            String algorithmClassName = algorithmName + "Algorithm";
            List<String> algorithmNameList = algorithmProvider.findFullClassName(algorithmClassName);
            return !algorithmNameList.isEmpty();

        } catch (IOException exception) {
            return false;

        }
    }

    @Override
    public List<Algorithm> findAll() {
        // todo
        return List.of(new DijkstraAlgorithm(), new SampleAlgorithm(), new DisjointSetAlgorithm());
    }

    @Override
    public <T  extends Algorithm> List<Algorithm> find(String algorithmName) {
        String algorithmClassName = algorithmName + "Algorithm";

        if(isCached(algorithmName)){
            return getCached(algorithmName);

        } else try {
            List<Algorithm> result = algorithmProvider.find(algorithmClassName);
            putCache(algorithmName, result);
            return result;

        } catch (IOException exception) {
            return List.of();

        }
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
