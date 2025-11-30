package client;

/**
 * Responsive button that adjusts size and position based on screen size.
 * Uses anchor points to stay in specific screen locations.
 */
public class Button {
    private String label;
    private float x, y, width, height;
    private AnchorPoint anchor;
    
    // Visual properties
    private boolean isPressed = false;
    private boolean isHovered = false;
    
    public Button(String label, AnchorPoint anchor) {
        this.label = label;
        this.anchor = anchor;
    }
    
    /**
     * Updates button size and position based on viewport dimensions.
     * Called during resize() to keep button responsive.
     * 
     * @param worldWidth Current viewport world width
     * @param worldHeight Current viewport world height
     */
    public void updateLayout(float worldWidth, float worldHeight) {
        // Button size: 15% of screen width, 8% of screen height
        // But constrained between min and max values
        this.width = Math.max(80f, Math.min(200f, worldWidth * 0.15f));
        this.height = Math.max(40f, Math.min(100f, worldHeight * 0.08f));
        
        // Calculate position based on anchor point
        calculatePosition(worldWidth, worldHeight);
    }
    
    /**
     * Calculates button position based on anchor point.
     * Uses safe zone margin to keep button away from screen edges.
     */
    private void calculatePosition(float worldWidth, float worldHeight) {
        // Safe zone: 2% of smaller dimension
        float margin = Math.min(worldWidth, worldHeight) * 0.02f;
        
        switch (anchor) {
            case TOP_LEFT:
                x = margin;
                y = worldHeight - height - margin;
                break;
            case TOP_RIGHT:
                x = worldWidth - width - margin;
                y = worldHeight - height - margin;
                break;
            case TOP_CENTER:
                x = (worldWidth - width) / 2;
                y = worldHeight - height - margin;
                break;
            case BOTTOM_LEFT:
                x = margin;
                y = margin;
                break;
            case BOTTOM_RIGHT:
                x = worldWidth - width - margin;
                y = margin;
                break;
            case BOTTOM_CENTER:
                x = (worldWidth - width) / 2;
                y = margin;
                break;
            case CENTER:
                x = (worldWidth - width) / 2;
                y = (worldHeight - height) / 2;
                break;
        }
    }
    
    /**
     * Checks if a touch/click is within button bounds.
     */
    public boolean isClicked(float touchX, float touchY) {
        return touchX >= x && touchX <= x + width &&
               touchY >= y && touchY <= y + height;
    }
    
    /**
     * Draw method (stubbed - would use SpriteBatch in real implementation).
     */
    public void draw() {
        // In real libGDX:
        // batch.draw(buttonTexture, x, y, width, height);
        // font.draw(batch, label, x + width/2, y + height/2);
    }
    
    // Getters
    public String getLabel() { return label; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public AnchorPoint getAnchor() { return anchor; }
    
    // State setters
    public void setPressed(boolean pressed) { this.isPressed = pressed; }
    public void setHovered(boolean hovered) { this.isHovered = hovered; }
    public boolean isPressed() { return isPressed; }
    public boolean isHovered() { return isHovered; }
}
