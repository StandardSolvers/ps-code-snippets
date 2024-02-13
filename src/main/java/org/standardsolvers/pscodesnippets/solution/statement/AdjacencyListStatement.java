package org.standardsolvers.pscodesnippets.solution.statement;

import org.standardsolvers.pscodesnippets.solution.SolutionStatement;
import org.standardsolvers.pscodesnippets.solution.ps.AdjacencyList;

import java.util.ArrayList;
import java.util.Scanner;


@SolutionStatement(ps = AdjacencyList.class)
public class AdjacencyListStatement {
    public void solution() {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i=0; i<=100; i++){
            graph.add(new ArrayList<>());
        }
        Scanner sc = new Scanner(System.in);
        for (int i = 1; i < 100; i++) {
            String[] tmp = sc.next().split(" ");
            int a = Integer.parseInt(tmp[0]);
            int b = Integer.parseInt(tmp[1]);
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
    }
}
