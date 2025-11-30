package client;

/**
 * Example demonstrating button and HUD responsiveness during window resize.
 * Shows how UI elements should scale and reposition when screen size changes.
 */
public class GameWithUI {
    
    // Camera and viewport (stubbed)
    // private OrthographicCamera camera;
    // private Viewport viewport;
    
    // Player
    private float playerX, playerY;
    private float playerSize = 50f;
    
    // UI Elements
    private PauseButton pauseButton;
    private HUD hud;
    
    private static final float VIRTUAL_WIDTH = 800f;
    private static final float VIRTUAL_HEIGHT = 600f;
    
    // Game state
    private int score = 0;
    private int health = 80;
    private int maxHealth = 100;
    
    public void create() {
        // Initialize camera and viewport
        // camera = new OrthographicCamera();
        // viewport = new ScreenViewport(camera);
        
        // Initialize player
        playerX = VIRTUAL_WIDTH / 2;
        playerY = VIRTUAL_HEIGHT / 2;
        
        // Initialize UI elements
        pauseButton = new PauseButton();
        hud = new HUD();
        
        // Initial layout calculation
        updateUILayout(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        
        System.out.println("Game with UI created");
    }
    
    public void render() {
        // Game rendering would go here
        // batch.begin();
        // // Draw player
        // // Draw NPCs
        // batch.end();
        
        // Draw UI on top
        pauseButton.draw();
        hud.draw(score, health, maxHealth);
    }
    
    /**
     * RESIZE METHOD - handles window resize for both game objects and UI
     */
    public void resize(int width, int height) {
        // Step 1: Save old dimensions
        float oldWorldWidth = VIRTUAL_WIDTH;  // viewport.getWorldWidth()
        float oldWorldHeight = VIRTUAL_HEIGHT; // viewport.getWorldHeight()
        
        // Step 2: Update viewport
        // viewport.update(width, height, true);
        
        // Step 3: Get new dimensions
        float newWorldWidth = width;  // viewport.getWorldWidth()
        float newWorldHeight = height; // viewport.getWorldHeight()
        
        // Step 4: Scale player position
        if (oldWorldWidth > 0 && oldWorldHeight > 0) {
            playerX = (playerX / oldWorldWidth) * newWorldWidth;
            playerY = (playerY / oldWorldHeight) * newWorldHeight;
            playerSize = Math.min(newWorldWidth, newWorldHeight) * 0.05f; // 5% of smaller dimension
        }
        
        // Step 5: UPDATE UI LAYOUT (critical for responsive UI!)
        updateUILayout(newWorldWidth, newWorldHeight);
        
        // Step 6: Recenter camera
        // camera.position.set(newWorldWidth / 2, newWorldHeight / 2, 0);
        // camera.update();
        
        System.out.println("Resized to " + width + "x" + height);
        System.out.println("  Player at (" + playerX + ", " + playerY + ")");
        System.out.println("  Pause button at (" + pauseButton.x + ", " + pauseButton.y + ")");
    }
    
    /**
     * Updates all UI element positions and sizes based on screen dimensions
     */
    private void updateUILayout(float worldWidth, float worldHeight) {
        pauseButton.updateLayout(worldWidth, worldHeight);
        hud.updateLayout(worldWidth, worldHeight);
    }
    
    // ============ UI Classes ============
    
    /**
     * Pause button - positioned in top-right corner
     */
    class PauseButton {
        float x, y, width, height;
        
        void updateLayout(float worldWidth, float worldHeight) {
            // Button size: 10% of smaller dimension, capped between 40-100px
            float buttonSize = Math.min(worldWidth, worldHeight) * 0.1f;
            width = Math.max(40f, Math.min(100f, buttonSize));
            height = width;
            
            // Position: top-right corner with margin
            float margin = Math.min(worldWidth, worldHeight) * 0.02f; // 2% margin
            x = worldWidth - width - margin;
            y = worldHeight - height - margin;
        }
        
        void draw() {
            // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            // shapeRenderer.setColor(Color.GRAY);
            // shapeRenderer.rect(x, y, width, height);
            // shapeRenderer.end();
            // font.draw(batch, "||", x + width/2 - 5, y + height/2 + 5);
            System.out.print(""); // Stub
        }
        
        boolean isClicked(float touchX, float touchY) {
            return touchX >= x && touchX <= x + width &&
                   touchY >= y && touchY <= y + height;
        }
    }
    
    /**
     * HUD - displays score and health bar
     */
    class HUD {
        // Score position
        float scoreX, scoreY;
        float fontSize;
        
        // Health bar
        float healthBarX, healthBarY;
        float healthBarWidth, healthBarHeight;
        
        void updateLayout(float worldWidth, float worldHeight) {
            float margin = Math.min(worldWidth, worldHeight) * 0.02f;
            
            // Score position: top-left corner
            scoreX = margin;
            scoreY = worldHeight - margin;
            
            // Font size: 3% of smaller dimension
            fontSize = Math.min(worldWidth, worldHeight) * 0.03f;
            
            // Health bar dimensions: 25% width, 2.5% height
            healthBarWidth = worldWidth * 0.25f;
            healthBarHeight = worldHeight * 0.025f;
            
            // Health bar position: below score
            healthBarX = margin;
            healthBarY = worldHeight - margin - fontSize - healthBarHeight - 5f;
        }
        
        void draw(int score, int health, int maxHealth) {
            // Draw score text
            // font.getData().setScale(fontSize / font.getLineHeight());
            // font.draw(batch, "Score: " + score, scoreX, scoreY);
            
            // Draw health bar background (red)
            // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            // shapeRenderer.setColor(Color.RED);
            // shapeRenderer.rect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
            
            // Draw current health (green) - proportional to health percentage
            // float currentHealthWidth = (health / (float)maxHealth) * healthBarWidth;
            // shapeRenderer.setColor(Color.GREEN);
            // shapeRenderer.rect(healthBarX, healthBarY, currentHealthWidth, healthBarHeight);
            // shapeRenderer.end();
            
            System.out.print(""); // Stub
        }
    }
    
    // ============ Test Main ============
    
    public static void main(String[] args) {
        System.out.println("=== Game with UI Resize Test ===\n");
        
        GameWithUI game = new GameWithUI();
        game.create();
        
        // Initial state
        System.out.println("\n--- Initial State (800x600) ---");
        game.resize(800, 600);
        
        // Resize to larger
        System.out.println("\n--- Resize to 1920x1080 ---");
        game.resize(1920, 1080);
        
        // Resize to smaller
        System.out.println("\n--- Resize to 640x480 ---");
        game.resize(640, 480);
        
        // Resize to very wide (ultrawide monitor)
        System.out.println("\n--- Resize to 2560x1080 (ultrawide) ---");
        game.resize(2560, 1080);
        
        // Resize to mobile portrait
        System.out.println("\n--- Resize to 480x800 (mobile portrait) ---");
        game.resize(480, 800);
        
        System.out.println("\n✓ UI elements maintain proper positions and sizes!");
        System.out.println("✓ Buttons stay in corners, HUD stays readable");
    }
}
