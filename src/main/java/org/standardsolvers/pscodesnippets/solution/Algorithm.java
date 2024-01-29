package org.standardsolvers.pscodesnippets.solution;

public interface Algorithm {

    <T> Algorithm setProperty(String key, T value);

    String getContext();
}
