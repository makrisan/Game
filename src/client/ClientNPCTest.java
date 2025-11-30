package client;

public class ClientNPCTest {
    public static void main(String[] args) {
        System.out.println("Testing Client NPC...");
        
        // Create a simple sprite
        Sprite sprite = new Sprite("test-sprite");
        
        // Create an NPC at position (0, 0)
        NPC npc = new NPC(1, 0f, 0f, sprite);
        
        System.out.println("Created NPC with ID: " + npc.getId());
        System.out.println("Initial position: (" + npc.x + ", " + npc.y + ")");
        System.out.println("Initial target: (" + npc.moveX + ", " + npc.moveY + ")");
        
        // Simulate receiving a movement command from server
        System.out.println("\n--- Simulating server movement update ---");
        npc.moveX = 100f;  // Move to (100, 100)
        npc.moveY = 100f;
        System.out.println("New target: (" + npc.moveX + ", " + npc.moveY + ")");
        
        // Simulate a few update frames
        System.out.println("\n--- Simulating movement frames ---");
        for (int i = 0; i < 5; i++) {
            // Simulate 16ms per frame (60 FPS)
            npc.update(0.016f);
            System.out.printf("Frame %d: position (%.2f, %.2f)\n", i + 1, npc.x, npc.y);
        }
        
        // Test timing methods
        System.out.println("\n--- Testing timing methods ---");
        System.out.println("Receive difference: " + npc.getReceiveDifference() + "ms");
        npc.setReceiveDifference(200);
        System.out.println("Updated receive difference: " + npc.getReceiveDifference() + "ms");
        
        long now = System.currentTimeMillis();
        npc.setLastReceive(now);
        System.out.println("Last receive time set to: " + npc.getLastReceive());
        
        System.out.println("\n✅ Test completed successfully! LET'S GOOOOO XOXO");
    }
}
