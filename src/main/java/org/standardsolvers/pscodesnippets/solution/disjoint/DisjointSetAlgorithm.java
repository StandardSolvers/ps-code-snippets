package org.standardsolvers.pscodesnippets.solution.disjoint;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

public class DisjointSetAlgorithm implements Algorithm {
    @Override
    public <T> Algorithm set(String key, T value) {
        return this;
    }

    @Override
    public String getContext() {
        return "disjointSet";
    }
}
