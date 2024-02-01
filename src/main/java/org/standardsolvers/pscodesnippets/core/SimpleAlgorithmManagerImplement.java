package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SimpleAlgorithmManagerImplement implements AlgorithmManager {

    static AlgorithmManager algorithmHelper = new SimpleAlgorithmManagerImplement();
    AlgorithmFinder algorithmIoFinder = new AlgorithmFinder();

    Map<Class<? extends Algorithm>, Algorithm> algorithmMap = new HashMap<>();
    private SimpleAlgorithmManagerImplement(){}
    public static AlgorithmManager getInstance() {
        return algorithmHelper;
    }

    @Override
    public boolean isExists(String algorithmName){
        try {
            List<String> algorithmNameList = algorithmIoFinder.findFullClassName(algorithmName);
            // 찾기   - with find(Class<T> algorithmClass)
            return !algorithmNameList.isEmpty();
        } catch (IOException | NullPointerException exception) {
//            log.warn("Algorithm not found: {}", algorithmName, exception);
            // System.out.println(exception);
            return false;
        }
    }

    @Override
    public <T  extends Algorithm> List<Algorithm> find(String algorithmName) {
        List<Algorithm> result = new ArrayList<>();
        try{
            List<String> algorithmNameList = algorithmIoFinder.findFullClassName(algorithmName);

            for (String fullClassName : algorithmNameList) {
                try {
                    Class<T> algorithmClass = (Class<T>) Class.forName(fullClassName);
                    Algorithm algorithm = find(algorithmClass).orElseThrow(ClassNotFoundException::new);
                    result.add(algorithm);
                } catch (ClassNotFoundException ignored) {
                    // System.out.println(ignored);
                }
            }
            return result;
        } catch (IOException exception) {
            // System.out.println(exception);
//            log.warn("Algorithm not found: {}", algorithmName, exception);
            return List.of();
        }
    }

    @Override
    public <T  extends Algorithm> Optional<Algorithm> find(Class<T> algorithmClass){

        if(algorithmMap.containsKey(algorithmClass)){
//            log.info("From cache: {}", algorithmClass);
            return Optional.ofNullable(algorithmMap.get(algorithmClass));
        }

        try{
            Constructor<T> constructor = algorithmClass.getConstructor();
            T algorithm = constructor.newInstance();
            algorithmMap.put(algorithmClass, algorithm);
//            log.info("Add cache: {}", algorithmClass);
            return Optional.of(algorithm);

        } catch (NoSuchMethodException exception){
//            log.warn("No such method found: {}", algorithmClass, exception);
            // System.out.println(exception);
            return Optional.empty();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception){
//            log.warn("Error instantiating algorithm: {}", algorithmClass, exception);
            // System.out.println(exception);
            return Optional.empty();

        }
    }

    @Override
    public <T  extends Algorithm> boolean isCached(Class<T> algorithmClass){
        return algorithmMap.containsKey(algorithmClass);
    }

    @Override
    public void clearCache(){
        algorithmMap.clear();
    }
}
