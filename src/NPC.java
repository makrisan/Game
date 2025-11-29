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

    private void moveThread() {
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

