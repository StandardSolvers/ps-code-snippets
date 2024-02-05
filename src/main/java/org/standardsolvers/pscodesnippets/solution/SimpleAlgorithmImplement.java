package org.standardsolvers.pscodesnippets.solution;

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
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String content = reader.lines().collect(Collectors.joining("\n"));
            int start = content.indexOf("public class");
            int end = content.lastIndexOf("}");

            // Check if the indexes are valid and the word "class" is present
            if (start >= 0 && end >= 0 && start < end && content.contains("class")) {
                return content.substring(start, end+1).trim();  // Include the end brace
            } else {
                return "";
            }
        } catch (IOException ignored) {
            return "";
        }
    }
}
