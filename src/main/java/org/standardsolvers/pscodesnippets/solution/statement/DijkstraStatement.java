package org.standardsolvers.pscodesnippets.solution.statement;

import org.jetbrains.annotations.NotNull;
import org.standardsolvers.pscodesnippets.solution.SolutionStatement;
import org.standardsolvers.pscodesnippets.solution.ps.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

@SolutionStatement(ps = Dijkstra.class)
public class DijkstraStatement {

    public void solution() {
        class Node implements Comparable<Node> {
            private int index;
            private int distance;

            public Node(int index, int distance) {
                this.index = index;
                this.distance = distance;
            }

            public int getIndex() {
                return this.index;
            }

            public int getDistance() {
                return this.distance;
            }

            @Override
            public int compareTo(@NotNull Node o) {
                return 0;
            }
        }

        int n=0,m,start =0;
        int []d = new int[10001];
        int INF = (int) 1e9;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();

        pq.offer(new Node(start,0));
        d[start] = 0;
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int dist = node.getDistance();
            int now = node.getIndex();

            if (d[now] < dist) continue;;

            for (int i=0; i<graph.get(now).size(); i++){
                int cost = d[now]+ graph.get(now).get(i).getDistance();
                if (cost < d[graph.get(now).get(i).getIndex()]) {
                    d[graph.get(now).get(i).getIndex()] = cost;
                    pq.offer(new Node(graph.get(now).get(i).getIndex() , cost));
                }
            }
        }
        Arrays.fill(d,INF);

    }
}
