package org.standardsolvers.pscodesnippets.solution;

public interface Algorithm {
    // 설정 인자
    <T> Algorithm setConfig(String string, T argument);

    // 컨텍스트 인자
    <T> Algorithm setProperty(String string, T argument);

    // 알고리즘 바디
    String getContext();


}
