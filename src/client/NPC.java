package client;

public class NPC extends Sprite {
    // ↓ All fields at the top
    public float x;
    public float y;
    public float moveX;
    public float moveY;
    private final int id;
    
    private long lastReceive = 0; 
    private long receiveDifference = 0;
    
    // ↓ Constructor
    public NPC(int id, float x, float y, Sprite sprite) {
        super(sprite);
        this.id = id;
        this.x = x;
        this.y = y;
        this.moveX = x;
        this.moveY = y;
    }  // ← Don't forget closing brace
    
    // ↓ Methods after constructor
    public int getId() {
        return id;
    }
    
    public long getLastReceive() {
        return lastReceive;
    }
    
    public void setLastReceive(long time) {
        this.lastReceive = time;
    }
    
    public void setReceiveDifference(long diff) {
        this.receiveDifference = diff;
    }
    
    public long getReceiveDifference() {
        return this.receiveDifference;
    }
    
    public void update(float deltaTime) {
        double speed = (deltaTime / ((float) this.getReceiveDifference())) * 1000;
        if (x > moveX) {
            x -= (32 * speed);
        }
        if (x < moveX) {
            x += (32 * speed);
        }
        if (y > moveY) {
            y -= (32 * speed);
        }
        if (y < moveY) {
            y += (32 * speed);
        }
    }
    
    // Commented out for testing without libGDX
    // public void draw(Batch batch) {
    //     this.update(Gdx.graphics.getDeltaTime());
    //     super.draw(batch);
    // }
}