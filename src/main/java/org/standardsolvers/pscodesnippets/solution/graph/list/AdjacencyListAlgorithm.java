package org.standardsolvers.pscodesnippets.solution.graph.list;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

public class AdjacencyListAlgorithm implements Algorithm {
    @Override
    public <T> Algorithm set(String key, T value) {
        return this;
    }

    @Override
    public String getContext() {
        return "adjacency matrix";
    }
}
