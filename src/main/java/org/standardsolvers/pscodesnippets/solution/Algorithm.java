package org.standardsolvers.pscodesnippets.solution;

public interface Algorithm {
    <T> Algorithm set(String key, T value);

    String getContext();
}
