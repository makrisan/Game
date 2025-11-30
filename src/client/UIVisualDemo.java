package client;

/**
 * Visual demonstration of responsive UI at different screen sizes.
 * Shows ASCII art representation of button and HUD positions.
 */
public class UIVisualDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║    Responsive UI Visual Demonstration           ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
        
        demonstrateScreenSize(800, 600, "Small Desktop");
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        demonstrateScreenSize(1920, 1080, "Full HD Desktop");
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        demonstrateScreenSize(480, 800, "Mobile Portrait");
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        demonstrateScreenSize(2560, 1080, "Ultrawide Monitor");
    }
    
    private static void demonstrateScreenSize(int width, int height, String name) {
        System.out.println("📱 " + name + " - " + width + "x" + height);
        System.out.println("─".repeat(60));
        
        // Create UI elements
        Button pauseButton = new Button("Pause", AnchorPoint.TOP_RIGHT);
        Button menuButton = new Button("Menu", AnchorPoint.TOP_LEFT);
        Button centerButton = new Button("Start", AnchorPoint.CENTER);
        HUD hud = new HUD(100);
        hud.setScore(12345);
        hud.setHealth(75);
        
        // Update layouts
        pauseButton.updateLayout(width, height);
        menuButton.updateLayout(width, height);
        centerButton.updateLayout(width, height);
        hud.updateLayout(width, height);
        
        // Print layout
        System.out.println("\n🔘 Buttons:");
        printButton("Menu (TOP_LEFT)", menuButton);
        printButton("Pause (TOP_RIGHT)", pauseButton);
        printButton("Start (CENTER)", centerButton);
        
        System.out.println("\n📊 HUD:");
        System.out.printf("  Score Display:  pos(%.0f, %.0f)  font: %.1fpx%n",
            hud.getScoreX(), hud.getScoreY(), hud.getFontSize());
        System.out.printf("  Health Bar:     pos(%.0f, %.0f)  size: %.0fx%.0fpx%n",
            hud.getHealthBarX(), hud.getHealthBarY(),
            hud.getHealthBarWidth(), hud.getHealthBarHeight());
        System.out.printf("  Health Fill:    %.0fpx (%.0f%% of bar)%n",
            hud.getCurrentHealthBarWidth(),
            (hud.getHealth() / (float)hud.getMaxHealth()) * 100);
        
        // Draw ASCII representation
        System.out.println("\n📐 Screen Layout (ASCII):");
        drawScreenLayout(width, height, pauseButton, menuButton, centerButton);
    }
    
    private static void printButton(String label, Button button) {
        System.out.printf("  %-20s pos(%.0f, %.0f)  size: %.0fx%.0fpx%n",
            label, button.getX(), button.getY(),
            button.getWidth(), button.getHeight());
    }
    
    private static void drawScreenLayout(int width, int height, Button pause, Button menu, Button center) {
        int asciiWidth = 60;
        int asciiHeight = 15;
        
        char[][] screen = new char[asciiHeight][asciiWidth];
        
        // Fill with spaces
        for (int y = 0; y < asciiHeight; y++) {
            for (int x = 0; x < asciiWidth; x++) {
                screen[y][x] = ' ';
            }
        }
        
        // Draw border
        for (int x = 0; x < asciiWidth; x++) {
            screen[0][x] = '─';
            screen[asciiHeight - 1][x] = '─';
        }
        for (int y = 0; y < asciiHeight; y++) {
            screen[y][0] = '│';
            screen[y][asciiWidth - 1] = '│';
        }
        screen[0][0] = '┌';
        screen[0][asciiWidth - 1] = '┐';
        screen[asciiHeight - 1][0] = '└';
        screen[asciiHeight - 1][asciiWidth - 1] = '┘';
        
        // Scale button positions to ASCII grid
        int menuX = scaleToAscii(menu.getX(), width, asciiWidth);
        int menuY = scaleToAscii(height - menu.getY(), height, asciiHeight);
        int pauseX = scaleToAscii(pause.getX(), width, asciiWidth);
        int pauseY = scaleToAscii(height - pause.getY(), height, asciiHeight);
        int centerX = scaleToAscii(center.getX(), width, asciiWidth);
        int centerY = scaleToAscii(height - center.getY(), height, asciiHeight);
        
        // Draw buttons
        placeLabel(screen, menuX, menuY, "[MENU]");
        placeLabel(screen, pauseX, pauseY, "[PAUSE]");
        placeLabel(screen, centerX, centerY, "[START]");
        
        // Draw HUD indicator
        placeLabel(screen, 2, 1, "HUD");
        placeLabel(screen, 2, 2, "━━━"); // Health bar
        
        // Print screen
        for (int y = 0; y < asciiHeight; y++) {
            System.out.print("  ");
            for (int x = 0; x < asciiWidth; x++) {
                System.out.print(screen[y][x]);
            }
            System.out.println();
        }
        
        System.out.println("\n  Legend: [MENU] [PAUSE] = Buttons, HUD = Score/Health");
    }
    
    private static int scaleToAscii(float pos, int realSize, int asciiSize) {
        int scaled = (int)((pos / realSize) * asciiSize);
        return Math.max(1, Math.min(asciiSize - 2, scaled));
    }
    
    private static void placeLabel(char[][] screen, int x, int y, String label) {
        if (y < 0 || y >= screen.length) return;
        
        for (int i = 0; i < label.length() && (x + i) < screen[0].length; i++) {
            if (x + i >= 0 && x + i < screen[0].length) {
                screen[y][x + i] = label.charAt(i);
            }
        }
    }
}
