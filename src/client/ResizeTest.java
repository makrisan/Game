package client;

/**
 * Test for screen resize functionality.
 * Demonstrates how the resize method scales positions when window size changes.
 */
public class ResizeTest {
    
    public static void main(String[] args) {
        System.out.println("=== Screen Resize Test ===\n");
        
        // Test 1: Basic resize functionality with Play class
        testPlayResize();
        
        System.out.println();
        
        // Test 2: GameExample resize with position scaling
        testGameExampleResize();
        
        System.out.println();
        
        // Test 3: Player position scaling calculation
        testPositionScaling();
    }
    
    private static void testPlayResize() {
        System.out.println("Test 1: Play class resize");
        System.out.println("--------------------------");
        
        Play play = new Play();
        play.initializeViewport();
        
        // Set initial player position
        play.setPlayerPosition(400f, 300f);
        System.out.println("Initial player position: (" + play.getPlayerX() + ", " + play.getPlayerY() + ")");
        
        // Simulate window resize
        play.resize(1920, 1080);
        play.resize(800, 600);
        play.resize(1280, 720);
        
        System.out.println("✓ Play resize test completed");
    }
    
    private static void testGameExampleResize() {
        System.out.println("Test 2: GameExample resize with scaling");
        System.out.println("----------------------------------------");
        
        GameExample game = new GameExample();
        game.create();
        
        System.out.println("Initial position: (" + game.getPlayerX() + ", " + game.getPlayerY() + ")");
        
        // Test resize to larger window
        System.out.println("\nResizing to 1920x1080:");
        game.resize(1920, 1080);
        
        // Test resize to smaller window
        System.out.println("\nResizing to 640x480:");
        game.resize(640, 480);
        
        // Test resize back to original
        System.out.println("\nResizing back to 800x600:");
        game.resize(800, 600);
        
        game.dispose();
        
        System.out.println("✓ GameExample resize test completed");
    }
    
    private static void testPositionScaling() {
        System.out.println("Test 3: Position scaling calculation");
        System.out.println("-------------------------------------");
        
        // Simulate the resize scaling calculation
        float oldWorldWidth = 800f;
        float oldWorldHeight = 600f;
        float playerX = 400f;  // Center X
        float playerY = 300f;  // Center Y
        
        System.out.println("Original dimensions: " + oldWorldWidth + "x" + oldWorldHeight);
        System.out.println("Original player position: (" + playerX + ", " + playerY + ")");
        
        // Scale to 1920x1080
        float newWorldWidth = 1920f;
        float newWorldHeight = 1080f;
        
        float scaledX = (playerX / oldWorldWidth) * newWorldWidth;
        float scaledY = (playerY / oldWorldHeight) * newWorldHeight;
        
        System.out.println("\nAfter scaling to " + newWorldWidth + "x" + newWorldHeight + ":");
        System.out.println("Scaled player position: (" + scaledX + ", " + scaledY + ")");
        System.out.println("Position ratio: (" + (scaledX/newWorldWidth) + ", " + (scaledY/newWorldHeight) + ")");
        
        // Verify ratio is preserved
        float originalRatioX = playerX / oldWorldWidth;
        float originalRatioY = playerY / oldWorldHeight;
        float newRatioX = scaledX / newWorldWidth;
        float newRatioY = scaledY / newWorldHeight;
        
        boolean ratioPreserved = Math.abs(originalRatioX - newRatioX) < 0.001f && 
                                 Math.abs(originalRatioY - newRatioY) < 0.001f;
        
        System.out.println("Ratio preserved: " + ratioPreserved);
        System.out.println("✓ Position scaling calculation test completed");
    }
}
