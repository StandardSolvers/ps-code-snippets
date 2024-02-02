package org.standardsolvers.pscodesnippets.solution;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SimpleAlgorithmImplement<T> implements Algorithm<T>{
    final Class<T> statementClass;
    final String statementString;

    public SimpleAlgorithmImplement(Class<T> statementClass){
        this.statementClass = statementClass;
        this.statementString = findBody(statementClass);
    }

    @Override
    public String getContext(){
        return this.statementString;
    }

    public String findBody(Class<T> statementClass) {
        String statementClassFullName = statementClass.getName();
        String resourcePath = "/" + statementClassFullName.replace('.', '/') + ".java";
        InputStream input = statementClass.getResourceAsStream(resourcePath);

        if (input == null) {
            return "";
        } try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException ignored) {
            return "";
        }
    }
}
