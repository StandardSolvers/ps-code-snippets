package org.standardsolvers.pscodesnippets.core;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

public class AlgorithmHelper {
    final String SOLUTION_PACKAGE = "org.standardsolvers.pscodesnippets.solution";

    static AlgorithmHelper algorithmHelper = new AlgorithmHelper();
    Map<Class<? extends Algorithm>, Algorithm> algorithmMap = new HashMap<>();
    private AlgorithmHelper(){}

    public static AlgorithmHelper getInstance() {
        return algorithmHelper;
    }

    public boolean isExists(String algorithmName){
        try {
            List<String> algorithmNameList = findFullClassName(algorithmName);
            // 찾기   - with find(Class<T> algorithmClass)
            return !algorithmNameList.isEmpty();
        } catch (IOException e) {
//            log.warn("Algorithm not found: {}", algorithmName, exception);
            return false;
        }
    }

    public <T  extends Algorithm> boolean isCached(Class<T> algorithmClass){
        return algorithmMap.containsKey(algorithmClass);
    }

    public void clearCache(){
        algorithmMap.clear();
    }

    public <T  extends Algorithm> List<Algorithm> find(String algorithmName) {
        List<Algorithm> result = new ArrayList<>();
        try{
            List<String> algorithmNameList = findFullClassName(algorithmName);

            for (String fullClassName : algorithmNameList) {
                try {
                    Class<T> algorithmClass = (Class<T>) Class.forName(fullClassName);
                    Algorithm algorithm = find(algorithmClass).orElseThrow(ClassNotFoundException::new);
                    result.add(algorithm);
                } catch (ClassNotFoundException ignored) {
                }
            }
            return result;
        } catch (IOException exception) {
//            log.warn("Algorithm not found: {}", algorithmName, exception);
            return List.of();
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
            return Optional.of(algorithm);

        } catch (NoSuchMethodException exception){
//            log.warn("No such method found: {}", algorithmClass, exception);
            return Optional.empty();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception){
//            log.warn("Error instantiating algorithm: {}", algorithmClass, exception);
            return Optional.empty();

        }
    }

    public List<String> findFullClassName(String className) throws IOException {
        String path = SOLUTION_PACKAGE.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL uri = classLoader.getResource(path);
        File rootDir = new File(uri.getFile());

        return findFullClassNameInDirectory(rootDir, SOLUTION_PACKAGE, className);
    }

    public List<String> findFullClassNameInDirectory(File directory, String packageName, String className) {
        List<String> result = new ArrayList<>();

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                // Recursive call
                result.addAll(findFullClassNameInDirectory(file, packageName + "." + file.getName(), className));
            } else if (file.getName().endsWith("Algorithm.class") && file.getName().contains(className)) {
                String fullClassName = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                result.add(fullClassName);
            }
        }
        return result;
    }

}
