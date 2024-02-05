package org.standardsolvers.pscodesnippets.core;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.standardsolvers.pscodesnippets.solution.Algorithm;
import org.standardsolvers.pscodesnippets.solution.SolutionStatement;
import org.standardsolvers.pscodesnippets.solution.SimpleAlgorithmImplement;

import java.net.URL;
import java.util.*;

public class AlgorithmProviderImplement implements AlgorithmProvider{
    Map<String, Class<? extends Algorithm>> algorithmClassMap;
    Map<String, Class<?>> solutionStatementClassMap;

    public AlgorithmProviderImplement(){
        initAlgorithmClassMap();
        initSolutionStatementClassMap();
    }

    @Override
    public AlgorithmProviderImplement initAlgorithmClassMap(){
        Set<Class<? extends Algorithm>> list = findAllAlgorithmClass();
        algorithmClassMap = new HashMap<>();
        for (Class<? extends Algorithm> algorithmClass : list) {
            algorithmClassMap.put(algorithmClass.getName(), algorithmClass);
        }
        return this;
    }

    @Override
    public AlgorithmProviderImplement initSolutionStatementClassMap(){
        solutionStatementClassMap = new HashMap<>();
        if(algorithmClassMap.isEmpty()){
            initAlgorithmClassMap();
        }
        List<Class<?>> solutionStatementClassList = findAllSolutionStatementClass();

        for (Class<? extends Algorithm> algorithmClass : algorithmClassMap.values()) {
            Class<?> solutionStatementClass = solutionStatementClassList.stream()
                    .filter(clazz->clazz.getAnnotation(SolutionStatement.class).algorithm().equals(algorithmClass))
                    .findFirst().orElse( null);
            if(solutionStatementClass == null) continue;
            solutionStatementClassMap.put(algorithmClass.getName(), solutionStatementClass);
        }
        return this;
    }

    @Override
    public List<String> findAlgorithmFullClassName(String algorithmName){
        return algorithmClassMap.keySet().stream().filter(s->s.contains(algorithmName)).toList();
    }

    @Override
    public List<Algorithm> find(String algorithmName) {
        List<Algorithm> result = new ArrayList<>();

        List<String> algorithmFullClassNameList = findAlgorithmFullClassName(algorithmName);

        for (String fullClassName : algorithmFullClassNameList) {
            try {
                Class<? extends Algorithm> algorithmClass = algorithmClassMap.get(fullClassName);

                Algorithm algorithm = constructAlgorithm(algorithmClass).orElseThrow(ClassNotFoundException::new);
                result.add(algorithm);

            } catch (ClassNotFoundException ignored) {
            }
        }
        return result;
    }

    @Override
    public Map<String, List<Algorithm>> findAll() {
        Map<String,  List<Algorithm>> result = new HashMap<>();

        List<String> algorithmFullClassNameList =  algorithmClassMap.keySet().stream().toList();

        for (String fullClassName : algorithmFullClassNameList) {
            try {
                Class<? extends Algorithm> algorithmClass = algorithmClassMap.get(fullClassName);

                Algorithm algorithm = constructAlgorithm(algorithmClass).orElseThrow(ClassNotFoundException::new);
                result.put(fullClassName, List.of(algorithm));

            } catch (ClassNotFoundException ignored) {
            }
        }
        return result;
    }

    @Override
    public Optional<Algorithm> constructAlgorithm(Class<? extends Algorithm> algorithmClass){

        try{
            Class<?> statementClass = solutionStatementClassMap.get(algorithmClass.getName());
            Algorithm<?> algorithm = SimpleAlgorithmImplement.createInstance(algorithmClass.getName(), statementClass);
            return Optional.of(algorithm);

        } catch (Exception exception){
            return Optional.empty();

        }
    }

    @Override
    public Set<Class<? extends Algorithm>> findAllAlgorithmClass(){
        Collection<URL> urls = ClasspathHelper.forPackage(algorithmPack.getName());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(new SubTypesScanner(false)));

        return reflections.getSubTypesOf(Algorithm.class);
    }

    @Override
    public List<Class<?>> findAllSolutionStatementClass(){
        Collection<URL> urls = ClasspathHelper.forPackage(statementPack.getName());
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner()));

        Set<Class<?>> subTypes = reflections.getSubTypesOf(Object.class);
        return subTypes.stream().filter(e->e.getAnnotation(SolutionStatement.class)!=null).toList();
    }
}