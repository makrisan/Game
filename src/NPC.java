import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NPC {
    private static int currentId = 0;

    private static void incrementNextId() {
        currentId++;
    }

    private final int netId;
    private final float x;
    private final float y;

    public NPC(float x, float y) {
        this.x = x;
        this.y = y;
        this.netId = currentId;
        incrementNextId();
        this.moveThread();
    }

    public void moveThread() {
        AStar aStar = new AStar(game.collisions);
        Runnable botRunnable = () -> {
        if (path == null || path.size() == 0) {
            Random rand = new Random();
            boolean foundGoodLocation = false;

            while (!foundGoodLocation) {
                int xCur = rand.nextInt((int) maxX - (int) minX + 1) + (int) minX;
                int yCur = rand.nextInt((int) maxY - (int) minY + 1) + (int) minY;

                if (game.collisions[yCur][xCur] == 0) {
                    foundGoodLocation = true;
                    path = aStar.findPath((int) yCur, (int) xCur, (int) y, (int) x);
                }
            }

        } else {
            // YOUR ELSE BLOCK GOES HERE ↓
            Network.OnNpcMove movement = new Network.OnNpcMove();
            movement.netID = this.netId;
            AStar.Node node = path.get(0);
            path.remove(0);
            movement.x = node.col;
            movement.y = node.row;
            x = node.col;
            y = node.row;
            server.sendEveryone(movement, -1, game.getGameId());
        }

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(botRunnable, 2000, 300, TimeUnit.MILLISECONDS);
    }

    public int getNetId() {
        return this.netId;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}

