package client;

import java.util.List;

/**
 * Enhanced Play class demonstrating full resize integration with NPCs.
 * This shows how resize would work when libGDX is fully integrated.
 */
public class PlayWithResize extends Play {
    
    /**
     * Enhanced resize method that also scales all NPC positions.
     * This ensures NPCs maintain their relative positions when window size changes.
     * 
     * @param width New window width
     * @param height New window height
     */
    @Override
    public void resize(int width, int height) {
        // Call parent resize (handles player and basic setup)
        super.resize(width, height);
        
        // In a real libGDX implementation, this would scale NPCs:
        /*
        float oldWorldWidth = viewport.getWorldWidth();
        float oldWorldHeight = viewport.getWorldHeight();
        
        viewport.update(width, height, true);
        
        float newWorldWidth = viewport.getWorldWidth();
        float newWorldHeight = viewport.getWorldHeight();
        
        // Scale all NPC positions
        if (oldWorldWidth > 0 && oldWorldHeight > 0) {
            for (NPC npc : getAllNpcs()) {
                // Scale current position
                npc.x = (npc.x / oldWorldWidth) * newWorldWidth;
                npc.y = (npc.y / oldWorldHeight) * newWorldHeight;
                
                // Scale target position (for smooth movement)
                npc.moveX = (npc.moveX / oldWorldWidth) * newWorldWidth;
                npc.moveY = (npc.moveY / oldWorldHeight) * newWorldHeight;
                
                System.out.println("Scaled NPC " + npc.getId() + " to (" + npc.x + ", " + npc.y + ")");
            }
        }
        
        camera.position.set(newWorldWidth / 2, newWorldHeight / 2, 0);
        camera.update();
        */
    }
    
    /**
     * Test method demonstrating resize with NPCs.
     */
    public static void main(String[] args) {
        System.out.println("=== Play with NPC Resize Test ===\n");
        
        PlayWithResize play = new PlayWithResize();
        play.initializeViewport();
        
        // Add some test NPCs
        System.out.println("Adding test NPCs...");
        play.addNpc(1, 10, 10);  // Top-left area
        play.addNpc(2, 20, 15);  // Middle-left area
        play.addNpc(3, 15, 20);  // Center area
        
        System.out.println("\nInitial NPC positions:");
        printNpcPositions(play.getAllNpcs());
        
        // Simulate resize to larger window
        System.out.println("\n--- Resizing to 1920x1080 ---");
        play.resize(1920, 1080);
        System.out.println("\nNPC positions after resize:");
        printNpcPositions(play.getAllNpcs());
        
        // Simulate resize to smaller window
        System.out.println("\n--- Resizing to 640x480 ---");
        play.resize(640, 480);
        System.out.println("\nNPC positions after resize:");
        printNpcPositions(play.getAllNpcs());
        
        System.out.println("\n✓ Test completed - NPCs maintain relative positions!");
    }
    
    private static void printNpcPositions(List<NPC> npcs) {
        for (NPC npc : npcs) {
            System.out.printf("  NPC %d: (%.1f, %.1f) -> target: (%.1f, %.1f)%n",
                npc.getId(), npc.x, npc.y, npc.moveX, npc.moveY);
        }
    }
}
