package org.standardsolvers.pscodesnippets.solution.disjoint;

import org.standardsolvers.pscodesnippets.solution.SolutionStatement;

public class DisjointSetStatement implements SolutionStatement {


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
    @Override
    public void solution() {

    }
}
