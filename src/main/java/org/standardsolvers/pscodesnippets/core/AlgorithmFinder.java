package org.standardsolvers.pscodesnippets.core;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AlgorithmFinder {
    final Package p = Algorithm.class.getPackage();
    public List<String> findFullClassName(String className) throws IOException {
        Collection<URL> urls = ClasspathHelper.forPackage(p.getName());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(urls)
                .setScanners(new SubTypesScanner(false)));

        Set<Class<? extends Algorithm>> allClasses = reflections.getSubTypesOf(Algorithm.class);
        List<String> result = allClasses.stream().map(Class::getName)
                .filter(s -> s.endsWith("Algorithm") && s.contains(className))
                .toList();

//        System.out.println("Total classes found: " + allClasses.size());
//        System.out.println(String.join(",", result));

        return result;
    }
}