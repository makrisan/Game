import java.util.*;

public class AStar {

    // map size
    private final int maxX;
    private final int maxY;

    // tile grid (1 = wall, 0 = walkable)
    private final int[][] grid;

    // 4-directional movement (left, up, right, down)
    private final int[][] neighbours = {
            {-1, 0},   // left
            {0, -1},   // up
            {1, 0},    // right
            {0, 1}     // down
    };

    public AStar(int[][] grid) {
        this.grid = grid;
        this.maxX = grid[0].length;
        this.maxY = grid.length;
    }

    // ------------------------------
    // Node class
    // ------------------------------
public class Node {

    int x;
    int y;
    int gScore;
    int hScore;
    Node parent;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gScore = 0;
        this.hScore = 0;
        this.parent = null;
    }

    void updateHScore(int dstX, int dstY) {
        this.hScore = Math.abs(x - dstX) + Math.abs(y - dstY);
    }

    int getFScore() {
        return this.gScore + this.hScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return x == node.x &&
                y == node.y;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x + (y * maxY));
    }
}

    // ------------------------------
    // A* algorithm
    // ------------------------------
    public List<Node> findPath(int srcX, int srcY, int dstX, int dstY) {
        List<Node> path = new ArrayList<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(AStar.Node::getFScore));
        openSet.add(new Node(srcX, srcY));
        Set<Node> closedSet = new HashSet<>();
        while (!openSet.isEmpty()) {
            AStar.Node current = openSet.poll();

            if (current.x == dstX && current.y == dstY) {
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                return path;
            }

            closedSet.add(current);

            for (int[] neighbour : neighbours) {
                int x = current.x + neighbour[0];
                int y = current.y + neighbour[1];

                // check bounds and walkability
                if (x < 0 || x >= maxX || y < 0 || y >= maxY || grid[y][x] == 1) {
                    continue;
                }
                AStar.Node neighbor = new AStar.Node(x, y);
                int newGScore = current.gScore + 1;
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                if (!openSet.contains(neighbor) || newGScore < neighbor.gScore) {
                    neighbor.parent = current;
                    neighbor.gScore = newGScore;
                    neighbor.updateHScore(dstX, dstY);
                    openSet.add(neighbor);
                }
            }
        }
        return null;
    }
}

