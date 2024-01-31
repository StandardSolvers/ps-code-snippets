package org.standardsolvers.pscodesnippets.solution.bfs;

import org.standardsolvers.pscodesnippets.solution.Algorithm;

public class Bfs implements Algorithm {
    @Override
    public <T> Algorithm set(String key, T value) {
        return this;
    }

    @Override
    public String getContext() {
        return "bfs";
    }
}
