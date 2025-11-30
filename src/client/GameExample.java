package client;

/**
 * Example libGDX game implementation showing resize functionality.
 * This is based on the study material example - demonstrates how camera,
 * viewport, and resize work together in a complete game.
 * 
 * When libGDX dependencies are added, this class would extend ApplicationAdapter
 * and implement the full game loop with proper rendering.
 */
public class GameExample {
    
    // Rendering components (stubbed without libGDX)
    // private SpriteBatch batch;
    // private OrthographicCamera camera;
    // private Viewport viewport;
    // private ShapeRenderer shapeRenderer;
    
    // Player properties
    private float playerX, playerY;
    private float playerSpeed = 500f; // pixels per second
    private float playerSize = 50f;
    
    // Virtual world dimensions
    private static final float VIRTUAL_WIDTH = 800f;
    private static final float VIRTUAL_HEIGHT = 600f;
    
    /**
     * Called when the game starts.
     * Sets up rendering components and initial positions.
     */
    public void create() {
        // Initialize rendering (with libGDX):
        // batch = new SpriteBatch();
        // shapeRenderer = new ShapeRenderer();
        // camera = new OrthographicCamera();
        // viewport = new ScreenViewport(camera);
        
        // Set initial player position (center of screen)
        playerX = VIRTUAL_WIDTH / 2 - playerSize / 2;
        playerY = VIRTUAL_HEIGHT / 2 - playerSize / 2;
        
        // Position camera at world center
        // camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        // camera.update();
        
        System.out.println("Game created - Player at (" + playerX + ", " + playerY + ")");
    }
    
    /**
     * Main game loop - called every frame.
     * Handles input, updates game state, and renders.
     */
    public void render() {
        // Get time since last frame (with libGDX):
        // float deltaTime = Gdx.graphics.getDeltaTime();
        float deltaTime = 0.016f; // Simulated ~60 FPS
        
        // Update camera
        // camera.update();
        // batch.setProjectionMatrix(camera.combined);
        
        // Clear screen with gray background
        // Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Adjust player size based on screen size (responsive design)
        // playerSize = Math.min(viewport.getWorldWidth(), viewport.getWorldHeight()) * 0.1f;
        
        // Handle player movement (with libGDX input):
        // if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        //     playerX -= playerSpeed * deltaTime;
        // }
        // if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        //     playerX += playerSpeed * deltaTime;
        // }
        // if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        //     playerY += playerSpeed * deltaTime;
        // }
        // if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        //     playerY -= playerSpeed * deltaTime;
        // }
        
        // Keep player within screen bounds
        // playerX = Math.max(0, Math.min(playerX, viewport.getWorldWidth() - playerSize));
        // playerY = Math.max(0, Math.min(playerY, viewport.getWorldHeight() - playerSize));
        
        // Render player as red rectangle
        // shapeRenderer.setProjectionMatrix(camera.combined);
        // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // shapeRenderer.setColor(Color.RED);
        // shapeRenderer.rect(playerX, playerY, playerSize, playerSize);
        // shapeRenderer.end();
    }
    
    /**
     * CRITICAL: Resize method - handles window size changes.
     * This is called automatically by libGDX when the window is resized.
     * 
     * The key steps are:
     * 1. Save old world dimensions
     * 2. Update viewport (which recalculates world size)
     * 3. Scale all object positions proportionally
     * 4. Recenter camera
     * 
     * @param width New window width in pixels
     * @param height New window height in pixels
     */
    public void resize(int width, int height) {
        // Step 1: Save old world dimensions BEFORE viewport update
        float oldWorldWidth = 800f;  // In real code: viewport.getWorldWidth()
        float oldWorldHeight = 600f; // In real code: viewport.getWorldHeight()
        
        // Step 2: Update viewport with new screen dimensions
        // viewport.update(width, height, true); // true = center camera
        
        // Step 3: Get new world dimensions AFTER viewport update
        float newWorldWidth = 800f;  // In real code: viewport.getWorldWidth()
        float newWorldHeight = 600f; // In real code: viewport.getWorldHeight()
        
        // Step 4: Scale player position proportionally
        // This ensures the player stays in the same relative position
        if (oldWorldWidth > 0 && oldWorldHeight > 0) {
            playerX = (playerX / oldWorldWidth) * newWorldWidth;
            playerY = (playerY / oldWorldHeight) * newWorldHeight;
        }
        
        // Step 5: Recenter camera at new world center
        // camera.position.set(newWorldWidth / 2, newWorldHeight / 2, 0);
        // camera.update();
        
        System.out.println("Resized to " + width + "x" + height + 
                           " - Player scaled to (" + playerX + ", " + playerY + ")");
    }
    
    /**
     * Cleanup - called when game exits.
     */
    public void dispose() {
        // Release resources
        // batch.dispose();
        // shapeRenderer.dispose();
        System.out.println("Game disposed");
    }
    
    // Getters for testing
    public float getPlayerX() { return playerX; }
    public float getPlayerY() { return playerY; }
    public float getPlayerSize() { return playerSize; }
}
