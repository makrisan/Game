package client;

public class PlayTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Play (Game Screen) ===\n");
        
        // Create a Play instance (game screen)
        Play play = new Play();
        
        // Test 1: Add NPCs
        System.out.println("--- Test 1: Spawning NPCs ---");
        play.addNpc(1, 0f, 0f);     // Spawn NPC 1 at (0, 0)
        play.addNpc(2, 5f, 5f);     // Spawn NPC 2 at (5, 5)
        System.out.println("Total NPCs: " + play.getNpcCount() + "\n");
        
        // Test 2: Move NPCs (simple version)
        System.out.println("--- Test 2: Moving NPCs (simple) ---");
        play.changeNpcLocation(1, 10f, 10f);  // Move NPC 1 to (10, 10)
        NPC npc1 = play.getNpc(1);
        System.out.println("NPC 1 target: (" + npc1.moveX + ", " + npc1.moveY + ")\n");
        
        // Test 3: Move NPCs with timing
        System.out.println("--- Test 3: Moving NPCs (with timing) ---");
        try { Thread.sleep(100); } catch (InterruptedException e) {}  // Wait 100ms
        play.changeNpcLocationWithTiming(2, 15f, 15f);  // Move NPC 2
        NPC npc2 = play.getNpc(2);
        System.out.println("NPC 2 receive difference: " + npc2.getReceiveDifference() + "ms\n");
        
        // Test 4: Update and simulate movement
        System.out.println("--- Test 4: Simulating movement frames ---");
        System.out.println("Initial NPC 1 position: (" + npc1.x + ", " + npc1.y + ")");
        
        // Simulate 5 frames
        for (int i = 0; i < 5; i++) {
            play.updateNPCs(0.016f);  // 60 FPS (16ms per frame)
            System.out.printf("Frame %d: NPC 1 at (%.2f, %.2f)\n", i + 1, npc1.x, npc1.y);
        }
        
        System.out.println("\n--- Test 5: Get all NPCs ---");
        System.out.println("Total NPCs in game: " + play.getAllNpcs().size());
        for (NPC npc : play.getAllNpcs()) {
            System.out.printf("  - NPC %d at (%.2f, %.2f)\n", npc.getId(), npc.x, npc.y);
        }
        
        System.out.println("\n✅ Play test completed successfully!");
    }
}
