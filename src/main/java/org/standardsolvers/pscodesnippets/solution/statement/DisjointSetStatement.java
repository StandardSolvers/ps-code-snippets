package org.standardsolvers.pscodesnippets.solution.statement;

import org.standardsolvers.pscodesnippets.solution.SolutionStatement;
import org.standardsolvers.pscodesnippets.solution.ps.DisjointSet;

@SolutionStatement(ps = DisjointSet.class)
public class DisjointSetStatement {
    static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a < b) parent[b] = a;
        else parent[a] = b;
    }

    static int parent[] = new int[101];

    static int find(int num) {
        if (num == parent[num]) {
            return num;
        }
        return parent[num] = find(parent[num]);
    }

}
