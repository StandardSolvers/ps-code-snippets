package org.standardsolvers.pscodesnippets.solution;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class SimplePsImplement<T> implements Ps<T> {
    final Class<T> statementClass;
    final String name;
    final String simpleName;
    final String context;
    final String body;

    private SimplePsImplement(String name, String simpleName, Class<T> statementClass){
        this.name = name;
        this.simpleName = simpleName;
        this.statementClass = statementClass;
        this.context = findContext(statementClass);
        this.body = findBody(context);
    }

    public static <T> Ps<T> createInstance(String name, String simpleName, Class<T> statementClass){
        return new SimplePsImplement<>(name, simpleName, statementClass);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSimpleName() {
        return this.simpleName;
    }


    @Override
    public String getContext(){
        return this.context;
    }

    @Override
    public String getBody(){
        return this.body;
    }

    public String findContext(Class<T> statementClass) {
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

    public String findBody(String context){
        String body = context.replaceAll("public\\s+class\\s+\\w+\\s*\\{", "").trim();
        body = body.substring(0, body.lastIndexOf("}")).trim();
        return body;
    }
}
