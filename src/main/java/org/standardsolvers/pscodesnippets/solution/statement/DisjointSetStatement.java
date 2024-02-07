package org.standardsolvers.pscodesnippets.solution.statement;

import org.standardsolvers.pscodesnippets.solution.SolutionStatement;
import org.standardsolvers.pscodesnippets.solution.ps.DisjointSetAlgorithm;

@SolutionStatement(ps = DisjointSetAlgorithm.class)
public class DisjointSetStatement {
    void union(int a, int b) {
        a = find(a);
        b = find(b);
    }

    int parent[] = new int[101];

    int find(int num) {
        if (num == parent[num]) {
            return num;
        }
        return parent[num] = find(parent[num]);
    }
}
