package client;

import java.util.ArrayList;
import java.util.List;

public class Play {
    // List of all NPCs in the game
    private List<NPC> aiNpcs = new ArrayList<>();
    
    // Camera and viewport for screen management (stubbed for now - will work with libGDX)
    private Object camera;  // OrthographicCamera in real implementation
    private Object viewport; // Viewport in real implementation
    
    // Virtual world dimensions
    private static final float VIRTUAL_WIDTH = 800f;
    private static final float VIRTUAL_HEIGHT = 600f;
    
    // Player position (for resize scaling)
    private float playerX = 0f;
    private float playerY = 0f;
    
    // Called by NetworkListener when server spawns an NPC
    public void addNpc(int id, float x, float y) {
        x *= 32;  // Convert grid to pixel coordinates
        y *= 32;
        Sprite sprite = new Sprite("npc-sprite-" + id);
        NPC npc = new NPC(id, x, y, sprite);
        aiNpcs.add(npc);
        System.out.println("Added NPC " + id + " at position (" + x + ", " + y + ")");
    }
    
    // Called by NetworkListener when server moves an NPC (simple version)
    public void changeNpcLocation(int id, float x, float y) {
        x *= 32;
        y *= 32;

        for (NPC npc : aiNpcs) {
            if (npc.getId() == id) {
                npc.moveX = x;
                npc.moveY = y;
                System.out.println("Updated NPC " + id + " target to (" + x + ", " + y + ")");
            }
        }
    }
    
    // Enhanced version with timing (for smooth interpolation)
    public void changeNpcLocationWithTiming(int id, float x, float y) {
        x *= 32;
        y *= 32;

        for (NPC npc : aiNpcs) {
            if (npc.getId() == id) {
                long now = System.currentTimeMillis();
                npc.setReceiveDifference(now - npc.getLastReceive());
                npc.setLastReceive(now);
                npc.moveX = x;
                npc.moveY = y;
                System.out.println("Updated NPC " + id + " with timing: diff=" + npc.getReceiveDifference() + "ms");
            }
        }
    }
    
    // Called every frame to render all NPCs
    public void renderNPCs() {
        for (NPC npc : aiNpcs) {
            npc.setPosition(npc.x, npc.y);
            npc.draw();
        }
    }
    
    // Update all NPCs (called each frame)
    public void updateNPCs(float deltaTime) {
        for (NPC npc : aiNpcs) {
            npc.update(deltaTime);
        }
    }
    
    // Get NPC by ID (helper method)
    public NPC getNpc(int id) {
        for (NPC npc : aiNpcs) {
            if (npc.getId() == id) {
                return npc;
            }
        }
        return null;
    }
    
    // Get all NPCs
    public List<NPC> getAllNpcs() {
        return aiNpcs;
    }
    
    // Get NPC count
    public int getNpcCount() {
        return aiNpcs.size();
    }
    
    // ============ Screen Resize Functionality ============
    
    /**
     * Handles window resize events. 
     * This method scales NPC positions proportionally when the window size changes.
     * In a real libGDX implementation, this would be called automatically by the framework.
     * 
     * @param width New window width
     * @param height New window height
     */
    public void resize(int width, int height) {
        // Stub implementation (for full libGDX integration):
        // 
        // // Save old world dimensions
        // float oldWorldWidth = viewport.getWorldWidth();
        // float oldWorldHeight = viewport.getWorldHeight();
        // 
        // // Update viewport with new screen dimensions
        // viewport.update(width, height, true);
        // 
        // // Get new world dimensions after viewport update
        // float newWorldWidth = viewport.getWorldWidth();
        // float newWorldHeight = viewport.getWorldHeight();
        // 
        // // Scale player position proportionally
        // if (oldWorldWidth > 0 && oldWorldHeight > 0) {
        //     playerX = (playerX / oldWorldWidth) * newWorldWidth;
        //     playerY = (playerY / oldWorldHeight) * newWorldHeight;
        // }
        // 
        // // Recenter camera
        // camera.position.set(newWorldWidth / 2, newWorldHeight / 2, 0);
        // camera.update();
        
        // For now, just print resize info
        System.out.println("Window resized to: " + width + "x" + height);
    }
    
    /**
     * Initializes camera and viewport (to be called in create()).
     * This is where you'd set up the libGDX camera and viewport.
     */
    public void initializeViewport() {
        // Stub for libGDX integration:
        // camera = new OrthographicCamera();
        // viewport = new ScreenViewport(camera);
        // camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        // camera.update();
        
        System.out.println("Viewport initialized (stub)");
    }
    
    /**
     * Updates player position (for resize scaling).
     * In a real game, this would be called from the main game loop.
     */
    public void setPlayerPosition(float x, float y) {
        this.playerX = x;
        this.playerY = y;
    }
    
    /**
     * Gets player X position.
     */
    public float getPlayerX() {
        return playerX;
    }
    
    /**
     * Gets player Y position.
     */
    public float getPlayerY() {
        return playerY;
    }
}

