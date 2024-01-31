package org.standardsolvers.pscodesnippets.solution;

public class SampleAlgorithm implements Algorithm{
    @Override
    public <T> Algorithm set(String key, T value) {
        return this;
    }

    @Override
    public String getContext() {
        return "SampleAlgorithm This is sample!";
    }
}
