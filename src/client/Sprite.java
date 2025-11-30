package client;

public class Sprite {
    protected String name;

    public Sprite(String name) {
        this.name = name;
    }

    public Sprite(Sprite other) {
        this.name = other != null ? other.name : null;
    }

    public String getName() {
        return name;
    }

    public void setPosition(float x, float y) {
        // stub: no-op for now
    }

    public void draw() {
        // stub: no-op for now
    }

    @Override
    public String toString() {
        return "Sprite(" + name + ")";
    }
}