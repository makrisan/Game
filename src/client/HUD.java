package client;

/**
 * Heads-Up Display (HUD) that shows game information like score and health.
 * Automatically adjusts size and position based on screen size.
 */
public class HUD {
    // HUD data
    private int score;
    private int health;
    private int maxHealth;
    
    // Layout properties
    private float scoreX, scoreY;
    private float healthBarX, healthBarY;
    private float healthBarWidth, healthBarHeight;
    private float fontSize;
    
    public HUD(int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.score = 0;
    }
    
    /**
     * Updates HUD layout based on viewport dimensions.
     * Called during resize() to keep HUD responsive.
     * 
     * @param worldWidth Current viewport world width
     * @param worldHeight Current viewport world height
     */
    public void updateLayout(float worldWidth, float worldHeight) {
        // Safe zone margin
        float margin = Math.min(worldWidth, worldHeight) * 0.02f;
        
        // Score position: top-left corner
        scoreX = margin;
        scoreY = worldHeight - margin;
        
        // Health bar dimensions: 30% of screen width, 3% of height
        healthBarWidth = worldWidth * 0.3f;
        healthBarHeight = Math.max(20f, worldHeight * 0.03f);
        
        // Health bar position: below score
        healthBarX = margin;
        healthBarY = worldHeight - margin - 30f - healthBarHeight;
        
        // Font size: 3% of smaller dimension
        fontSize = Math.min(worldWidth, worldHeight) * 0.03f;
    }
    
    /**
     * Draw method (stubbed - would use SpriteBatch and ShapeRenderer in real implementation).
     */
    public void draw() {
        // In real libGDX:
        // font.draw(batch, "Score: " + score, scoreX, scoreY);
        //
        // shapeRenderer.begin(ShapeType.Filled);
        // shapeRenderer.setColor(Color.RED);
        // shapeRenderer.rect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
        //
        // float currentHealthWidth = (health / (float)maxHealth) * healthBarWidth;
        // shapeRenderer.setColor(Color.GREEN);
        // shapeRenderer.rect(healthBarX, healthBarY, currentHealthWidth, healthBarHeight);
        // shapeRenderer.end();
    }
    
    // Game state updates
    public void addScore(int points) {
        this.score += points;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }
    
    public void takeDamage(int damage) {
        this.health = Math.max(0, health - damage);
    }
    
    public void heal(int amount) {
        this.health = Math.min(maxHealth, health + amount);
    }
    
    // Getters
    public int getScore() { return score; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public float getScoreX() { return scoreX; }
    public float getScoreY() { return scoreY; }
    public float getHealthBarX() { return healthBarX; }
    public float getHealthBarY() { return healthBarY; }
    public float getHealthBarWidth() { return healthBarWidth; }
    public float getHealthBarHeight() { return healthBarHeight; }
    public float getCurrentHealthBarWidth() {
        return (health / (float)maxHealth) * healthBarWidth;
    }
    public float getFontSize() { return fontSize; }
}
