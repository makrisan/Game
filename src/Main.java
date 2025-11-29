import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 0},
        };

        AStar aStar = new AStar(grid);
        List<AStar.Node> path = aStar.findPath(1, 1, 4, 4);
        if (path != null) {
            for (AStar.Node node : path) {
                System.out.printf("%s:%s%n", node.x, node.y);
            }
        } else {
            System.out.println("No path found.");
        }
    }
}

