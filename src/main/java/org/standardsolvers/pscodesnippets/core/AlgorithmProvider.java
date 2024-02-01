package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AlgorithmProvider {
    default <T  extends Algorithm> List<Algorithm> find(String algorithmName) throws IOException {
        List<Algorithm> result = new ArrayList<>();

        List<String> algorithmFullClassNameList = findFullClassName(algorithmName);

        for (String fullClassName : algorithmFullClassNameList) {
            try {
                Class<T> algorithmClass = (Class<T>) Class.forName(fullClassName);
                Algorithm algorithm = initAlgorithm(algorithmClass).orElseThrow(ClassNotFoundException::new);
                result.add(algorithm);

            } catch (ClassNotFoundException ignored) {
            }
        }
        return result;
    }

    default <T extends Algorithm> Optional<Algorithm> initAlgorithm(Class<T> algorithmClass){

        try{
            Constructor<T> constructor = algorithmClass.getConstructor();
            T algorithm = constructor.newInstance();
            return Optional.of(algorithm);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException exception){
            return Optional.empty();

        }
    }

    List<String> findFullClassName(String className) throws IOException;
}
