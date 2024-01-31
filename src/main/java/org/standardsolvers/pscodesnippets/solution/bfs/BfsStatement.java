package org.standardsolvers.pscodesnippets.solution.bfs;

import org.standardsolvers.pscodesnippets.solution.SolutionStatement;

import java.util.LinkedList;
import java.util.Queue;

public class BfsStatement implements SolutionStatement {


    @Override
    public void solution() {
        class Point {
            int x, y;
            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        int n = 0, m = 0;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        boolean [][]visited = new boolean[10001][10001];

        Queue<Point> queue = new LinkedList<>();
        while (!queue.isEmpty()) {
            int len = queue.size();
            for (int k = 0; k < len; k++) {
                Point cur = queue.poll();
                for (int i = 0; i < 4; i++) {
                    int nx = dx[i] + cur.x;
                    int ny = dy[i] + cur.y;
                    if(nx < 0 || nx >= n || ny < 0 || ny >=m ) continue;
                    if (!visited[nx][ny]){
                        visited[nx][ny] = true;
                        queue.offer(new Point(nx,ny));
                    }
                }
            }
        }
    }
}