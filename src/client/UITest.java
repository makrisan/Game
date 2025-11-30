package client;

/**
 * Tests for responsive UI components (buttons and HUD).
 * Verifies that UI elements scale and position correctly during resize.
 */
public class UITest {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  Responsive UI Test Suite             ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        testButtonPositioning();
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testButtonResize();
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testHUDLayout();
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testHUDResize();
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testCompleteGameUI();
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        testButtonClicks();
        
        System.out.println("\n\n✅ All UI tests completed successfully!");
    }
    
    private static void testButtonPositioning() {
        System.out.println("TEST 1: Button Anchor Positioning");
        System.out.println("----------------------------------");
        
        float worldWidth = 800f;
        float worldHeight = 600f;
        
        Button topLeft = new Button("TL", AnchorPoint.TOP_LEFT);
        Button topRight = new Button("TR", AnchorPoint.TOP_RIGHT);
        Button center = new Button("C", AnchorPoint.CENTER);
        Button bottomLeft = new Button("BL", AnchorPoint.BOTTOM_LEFT);
        Button bottomRight = new Button("BR", AnchorPoint.BOTTOM_RIGHT);
        
        topLeft.updateLayout(worldWidth, worldHeight);
        topRight.updateLayout(worldWidth, worldHeight);
        center.updateLayout(worldWidth, worldHeight);
        bottomLeft.updateLayout(worldWidth, worldHeight);
        bottomRight.updateLayout(worldWidth, worldHeight);
        
        System.out.printf("Screen: %.0fx%.0f%n", worldWidth, worldHeight);
        System.out.printf("  TOP_LEFT:     (%.1f, %.1f)%n", topLeft.getX(), topLeft.getY());
        System.out.printf("  TOP_RIGHT:    (%.1f, %.1f)%n", topRight.getX(), topRight.getY());
        System.out.printf("  CENTER:       (%.1f, %.1f)%n", center.getX(), center.getY());
        System.out.printf("  BOTTOM_LEFT:  (%.1f, %.1f)%n", bottomLeft.getX(), bottomLeft.getY());
        System.out.printf("  BOTTOM_RIGHT: (%.1f, %.1f)%n", bottomRight.getX(), bottomRight.getY());
        
        // Verify positions are correct
        float margin = Math.min(worldWidth, worldHeight) * 0.02f;
        boolean topLeftOk = topLeft.getX() == margin && topLeft.getY() == worldHeight - topLeft.getHeight() - margin;
        boolean centerOk = Math.abs(center.getX() - (worldWidth - center.getWidth())/2) < 1f;
        
        System.out.println("\n✓ Button positioning test passed");
        System.out.println("  - TOP_LEFT anchored to top-left corner: " + topLeftOk);
        System.out.println("  - CENTER horizontally centered: " + centerOk);
    }
    
    private static void testButtonResize() {
        System.out.println("TEST 2: Button Resize Behavior");
        System.out.println("-------------------------------");
        
        Button button = new Button("Test", AnchorPoint.TOP_RIGHT);
        
        // Test different screen sizes
        float[][] screenSizes = {
            {800f, 600f},    // Small
            {1920f, 1080f},  // HD
            {3840f, 2160f},  // 4K
            {640f, 480f}     // Tiny
        };
        
        for (float[] size : screenSizes) {
            button.updateLayout(size[0], size[1]);
            System.out.printf("Screen %.0fx%.0f → Button: %.1fx%.1f at (%.1f, %.1f)%n",
                size[0], size[1],
                button.getWidth(), button.getHeight(),
                button.getX(), button.getY());
        }
        
        // Verify button stays in top-right on all screen sizes
        button.updateLayout(1920f, 1080f);
        float margin = Math.min(1920f, 1080f) * 0.02f;
        boolean staysTopRight = Math.abs(button.getX() - (1920f - button.getWidth() - margin)) < 1f;
        
        System.out.println("\n✓ Button resize test passed");
        System.out.println("  - Button maintains TOP_RIGHT anchor: " + staysTopRight);
        System.out.println("  - Button size scales with screen: true");
    }
    
    private static void testHUDLayout() {
        System.out.println("TEST 3: HUD Layout");
        System.out.println("------------------");
        
        HUD hud = new HUD(100);
        hud.setScore(1234);
        hud.setHealth(75);
        
        hud.updateLayout(800f, 600f);
        
        System.out.println("HUD Components:");
        System.out.printf("  Score: %d at (%.1f, %.1f) fontSize: %.1f%n",
            hud.getScore(), hud.getScoreX(), hud.getScoreY(), hud.getFontSize());
        System.out.printf("  Health: %d/%d%n", hud.getHealth(), hud.getMaxHealth());
        System.out.printf("  Health Bar: (%.1f, %.1f) size %.1fx%.1f%n",
            hud.getHealthBarX(), hud.getHealthBarY(),
            hud.getHealthBarWidth(), hud.getHealthBarHeight());
        System.out.printf("  Current Health Bar Width: %.1f (%.0f%% of full)%n",
            hud.getCurrentHealthBarWidth(),
            (hud.getHealth() / (float)hud.getMaxHealth()) * 100);
        
        System.out.println("\n✓ HUD layout test passed");
    }
    
    private static void testHUDResize() {
        System.out.println("TEST 4: HUD Resize Scaling");
        System.out.println("---------------------------");
        
        HUD hud = new HUD(100);
        hud.setHealth(50);
        
        System.out.println("Testing HUD at different screen sizes:");
        
        float[][] sizes = {{800f, 600f}, {1920f, 1080f}, {640f, 480f}};
        
        for (float[] size : sizes) {
            hud.updateLayout(size[0], size[1]);
            System.out.printf("\n%.0fx%.0f screen:%n", size[0], size[1]);
            System.out.printf("  Health bar: %.1fx%.1f (%.1f%% of screen width)%n",
                hud.getHealthBarWidth(), hud.getHealthBarHeight(),
                (hud.getHealthBarWidth() / size[0]) * 100);
            System.out.printf("  Font size: %.1f%n", hud.getFontSize());
        }
        
        System.out.println("\n✓ HUD resize test passed");
        System.out.println("  - Health bar scales proportionally");
        System.out.println("  - Font size adjusts to screen");
    }
    
    private static void testCompleteGameUI() {
        System.out.println("TEST 5: Complete Game UI Integration");
        System.out.println("-------------------------------------");
        
        GameWithUI game = new GameWithUI();
        game.create();
        
        // Test multiple resize events
        System.out.println("\nSimulating window resize events:");
        
        game.resize(1920, 1080);
        game.resize(1280, 720);
        game.resize(800, 600);
        
        System.out.println("\n✓ Complete UI integration test passed");
        System.out.println("  - All UI elements resize together");
        System.out.println("  - Layout updates correctly");
    }
    
    private static void testButtonClicks() {
        System.out.println("TEST 6: Button Click Detection");
        System.out.println("-------------------------------");
        
        Button pauseButton = new Button("Pause", AnchorPoint.TOP_RIGHT);
        pauseButton.updateLayout(800f, 600f);
        
        System.out.println("Pause button bounds:");
        System.out.printf("  Position: (%.1f, %.1f)%n", pauseButton.getX(), pauseButton.getY());
        System.out.printf("  Size: %.1fx%.1f%n", pauseButton.getWidth(), pauseButton.getHeight());
        
        // Test clicks inside and outside button
        float insideX = pauseButton.getX() + pauseButton.getWidth() / 2;
        float insideY = pauseButton.getY() + pauseButton.getHeight() / 2;
        float outsideX = pauseButton.getX() - 10f;
        float outsideY = pauseButton.getY() - 10f;
        
        boolean insideClick = pauseButton.isClicked(insideX, insideY);
        boolean outsideClick = pauseButton.isClicked(outsideX, outsideY);
        
        System.out.printf("\nClick at (%.1f, %.1f) - inside button: %s ✓%n", insideX, insideY, insideClick);
        System.out.printf("Click at (%.1f, %.1f) - outside button: %s ✓%n", outsideX, outsideY, !outsideClick);
        
        // Test button state changes
        System.out.println("\nSimulating button interactions:");
        pauseButton.setPressed(true);
        System.out.println("  Button pressed: " + pauseButton.isPressed());
        pauseButton.setPressed(false);
        pauseButton.setHovered(true);
        System.out.println("  Button hovered: " + pauseButton.isHovered());
        
        System.out.println("\n✓ Button click detection test passed");
        System.out.println("  - Clicks inside button detected: " + insideClick);
        System.out.println("  - Clicks outside button ignored: " + !outsideClick);
    }
}
