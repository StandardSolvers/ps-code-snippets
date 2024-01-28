package org.standardsolvers.pscodesnippets.solution;

public interface Algorithm {

    // 컨텍스트 인자
    <T> Algorithm setProperty(String key, T value);

    // 알고리즘 바디
    String getContext();
}
