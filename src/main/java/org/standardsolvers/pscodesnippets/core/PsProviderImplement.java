package org.standardsolvers.pscodesnippets.core;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.standardsolvers.pscodesnippets.solution.Ps;
import org.standardsolvers.pscodesnippets.solution.SolutionStatement;
import org.standardsolvers.pscodesnippets.solution.SimplePsImplement;

import java.net.URL;
import java.util.*;

public class PsProviderImplement implements PsProvider {
    Map<String, Class<? extends Ps>> psClassMap;
    Map<String, Class<?>> solutionStatementClassMap;

    public PsProviderImplement(){
        initPsClassMap();
        initSolutionStatementClassMap();
    }

    @Override
    public PsProviderImplement initPsClassMap(){
        Set<Class<? extends Ps>> list = findAllPsClass();
        psClassMap = new HashMap<>();
        for (Class<? extends Ps> psClass : list) {
            psClassMap.put(psClass.getName(), psClass);
        }
        return this;
    }

    @Override
    public PsProviderImplement initSolutionStatementClassMap(){
        solutionStatementClassMap = new HashMap<>();
        if(psClassMap.isEmpty()){
            initPsClassMap();
        }
        List<Class<?>> solutionStatementClassList = findAllSolutionStatementClass();

        for (Class<? extends Ps> psClass : psClassMap.values()) {
            Class<?> solutionStatementClass = solutionStatementClassList.stream()
                    .filter(clazz->clazz.getAnnotation(SolutionStatement.class).ps().equals(psClass))
                    .findFirst().orElse( null);
            if(solutionStatementClass == null) continue;
            solutionStatementClassMap.put(psClass.getName(), solutionStatementClass);
        }
        return this;
    }

    @Override
    public List<String> findPsFullClassName(String psName){
        return psClassMap.keySet().stream().filter(s->s.contains(psName)).toList();
    }

    @Override
    public List<Ps> find(String psName) {
        List<Ps> result = new ArrayList<>();

        List<String> psFullClassNameList = findPsFullClassName(psName);

        for (String fullClassName : psFullClassNameList) {
            try {
                Class<? extends Ps> psClass = psClassMap.get(fullClassName);

                Ps ps = constructPs(psClass).orElseThrow(ClassNotFoundException::new);
                result.add(ps);

            } catch (ClassNotFoundException ignored) {
            }
        }
        return result;
    }

    @Override
    public Map<String, List<Ps>> findAll() {
        Map<String,  List<Ps>> result = new HashMap<>();

        List<String> psFullClassNameList =  psClassMap.keySet().stream().toList();

        for (String fullClassName : psFullClassNameList) {
            try {
                Class<? extends Ps> psClass = psClassMap.get(fullClassName);

                Ps ps = constructPs(psClass).orElseThrow(ClassNotFoundException::new);
                result.put(fullClassName, List.of(ps));

            } catch (ClassNotFoundException ignored) {
            }
        }
        return result;
    }

    @Override
    public Optional<Ps> constructPs(Class<? extends Ps> psClass){

        try{
            Class<?> statementClass = solutionStatementClassMap.get(psClass.getName());
            Ps<?> ps = SimplePsImplement.createInstance(psClass.getName(), psClass.getSimpleName(), statementClass);
            return Optional.of(ps);

        } catch (Exception exception){
            return Optional.empty();

        }
    }

    @Override
    public Set<Class<? extends Ps>> findAllPsClass(){
        Collection<URL> urls = ClasspathHelper.forPackage(psPack.getName());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(new SubTypesScanner(false)));

        return reflections.getSubTypesOf(Ps.class);
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