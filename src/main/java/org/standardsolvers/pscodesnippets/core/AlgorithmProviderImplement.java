package org.standardsolvers.pscodesnippets.core;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.net.URL;
import java.util.*;

public class AlgorithmProviderImplement implements AlgorithmProvider{
    final Package pack = Algorithm.class.getPackage();

    @Override
    public List<String> findFullClassName(String className) {
        Collection<URL> urls = ClasspathHelper.forPackage(pack.getName());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(new SubTypesScanner(false)));

        Set<Class<? extends Algorithm>> allClasses = reflections.getSubTypesOf(Algorithm.class);
        List<String> result = allClasses.stream().map(Class::getName)
                .filter(s -> s.endsWith(className))
                .toList();

        return result;
    }
}