package org.standardsolvers.pscodesnippets.solution.dijkstra;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

public class DijkstraAlgorithm implements Algorithm {
    @Override
    public <T> Algorithm set(String key, T value) {
        return this;
    }

    @Override
    public String getContext() {
        return "wow!";
    }

}
