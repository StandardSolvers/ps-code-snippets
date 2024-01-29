package org.standardsolvers.pscodesnippets.core;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
public class AlgorithmHelper {
    private static final Logger log = LoggerFactory.getLogger(AlgorithmHelper.class);
    static AlgorithmHelper algorithmHelper = new AlgorithmHelper();
    Map<Class<? extends Algorithm>, Algorithm> algorithmMap = new HashMap<>();
    private AlgorithmHelper(){}


    public static AlgorithmHelper getInstance() {
        return algorithmHelper;
    }

    public <T  extends Algorithm> boolean isExists(Class<T> algorithmClass){
        return algorithmMap.containsKey(algorithmClass);
    }

    public void clearMap(){
        algorithmMap.clear();
    }

    public <T  extends Algorithm> Optional<Algorithm> find(String algorithmName) {
        try {
            // 찾기   - with find(Class<T> algorithmClass)
            Class<T> algorithmClass = (Class<T>) Class.forName(algorithmName);
            return find(algorithmClass);
        }catch (ClassNotFoundException exception){
//            log.warn("Algorithm not found: {}", algorithmName, exception);
            return Optional.empty();
        }
    }

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
            return Optional.ofNullable(algorithm);

        } catch (NoSuchMethodException exception){
//            log.warn("No such method found: {}", algorithmClass, exception);
            return Optional.empty();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception){
//            log.warn("Error instantiating algorithm: {}", algorithmClass, exception);
            return Optional.empty();

        }
    }


}
