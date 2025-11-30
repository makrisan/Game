# Screen Resize Implementation Guide

This document explains all the screen resize code snippets from the study material and how they work together.

## Overview

Screen resizing in libGDX requires careful coordination between the viewport, camera, and object positions. When the window size changes, you need to:
1. Update the viewport
2. Scale object positions proportionally
3. Recenter the camera

## Code Snippets Explained

### 1. Fullscreen Mode Configuration
```java
Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
config.setFullscreenMode(displayMode);
```
**Purpose:** Sets the game to fullscreen mode using the desktop's native display resolution.  
**Location:** Main class or game launcher (before creating the application).  
**When to use:** During initial game setup if you want fullscreen by default.

---

### 2. Save Old World Dimensions
```java
float oldWorldWidth = viewport.getWorldWidth();
float oldWorldHeight = viewport.getWorldHeight();
```
**Purpose:** Store the current world dimensions BEFORE updating the viewport.  
**Location:** First lines of the `resize(int width, int height)` method.  
**Why needed:** To calculate the scaling ratio for repositioning objects.

---

### 3. Update Viewport
```java
viewport.update(width, height, true);
```
**Purpose:** Updates the viewport to match the new window size.  
**Parameters:**
- `width`: New window width in pixels
- `height`: New window height in pixels  
- `true`: Centers the camera automatically

**Location:** Middle of `resize()` method, after saving old dimensions.  
**Effect:** Recalculates world dimensions based on viewport strategy (ScreenViewport, FitViewport, etc.)

---

### 4. Get New World Dimensions
```java
float newWorldWidth = viewport.getWorldWidth();
float newWorldHeight = viewport.getWorldHeight();
```
**Purpose:** Get the updated world dimensions AFTER viewport update.  
**Location:** After `viewport.update()` in the `resize()` method.  
**Why needed:** To calculate where objects should be positioned in the new world.

---

### 5. Scale Object Positions
```java
if (oldWorldWidth > 0 && oldWorldHeight > 0) {
   playerX = (playerX / oldWorldWidth) * newWorldWidth;
   playerY = (playerY / oldWorldHeight) * newWorldHeight;
}
```
**Purpose:** Scale object positions proportionally to maintain their relative position on screen.  
**How it works:**
- Divide by old dimension = get position as a ratio (0.0 to 1.0)
- Multiply by new dimension = convert ratio back to new coordinate
- Example: Player at X=400 in 800px width → 50% position → stays at 50% in new width

**Location:** After getting new dimensions in `resize()`.  
**Safety check:** `if (oldWorldWidth > 0 && oldWorldHeight > 0)` prevents division by zero.

---

### 6. Recenter Camera
```java
camera.position.set(newWorldWidth / 2, newWorldHeight / 2, 0);
```
**Purpose:** Position the camera at the center of the new world dimensions.  
**Parameters:**
- `newWorldWidth / 2`: Center X coordinate
- `newWorldHeight / 2`: Center Y coordinate
- `0`: Z coordinate (always 0 for 2D games)

**Location:** End of `resize()` method.  
**Note:** Must call `camera.update()` after this to apply changes.

---

## Complete resize() Method Template

```java
@Override
public void resize(int width, int height) {
    // Step 1: Save old dimensions
    float oldWorldWidth = viewport.getWorldWidth();
    float oldWorldHeight = viewport.getWorldHeight();
    
    // Step 2: Update viewport
    viewport.update(width, height, true);
    
    // Step 3: Get new dimensions
    float newWorldWidth = viewport.getWorldWidth();
    float newWorldHeight = viewport.getWorldHeight();
    
    // Step 4: Scale all object positions
    if (oldWorldWidth > 0 && oldWorldHeight > 0) {
        playerX = (playerX / oldWorldWidth) * newWorldWidth;
        playerY = (playerY / oldWorldHeight) * newWorldHeight;
        
        // Scale NPCs too
        for (NPC npc : npcs) {
            npc.x = (npc.x / oldWorldWidth) * newWorldWidth;
            npc.y = (npc.y / oldWorldHeight) * newWorldHeight;
        }
    }
    
    // Step 5: Recenter camera
    camera.position.set(newWorldWidth / 2, newWorldHeight / 2, 0);
    camera.update();
}
```

---

## Integration with Play.java

For the current project, the resize functionality has been added to `Play.java`:

1. **Added fields:**
   - `camera` (OrthographicCamera)
   - `viewport` (Viewport)
   - `VIRTUAL_WIDTH` and `VIRTUAL_HEIGHT` constants
   - `playerX` and `playerY` for player position

2. **Added methods:**
   - `resize(int width, int height)` - handles window resize
   - `initializeViewport()` - sets up camera and viewport
   - `setPlayerPosition(float x, float y)` - updates player position
   - `getPlayerX()` / `getPlayerY()` - gets player position

3. **To apply to NPCs:**
   When resize happens, you should also scale NPC positions:
   ```java
   for (NPC npc : aiNpcs) {
       npc.x = (npc.x / oldWorldWidth) * newWorldWidth;
       npc.y = (npc.y / oldWorldHeight) * newWorldHeight;
       npc.moveX = (npc.moveX / oldWorldWidth) * newWorldWidth;
       npc.moveY = (npc.moveY / oldWorldHeight) * newWorldHeight;
   }
   ```

---

## Testing

Run `ResizeTest.java` to see resize functionality in action:
```bash
javac -d bin src/client/ResizeTest.java src/client/Play.java src/client/GameExample.java
java -cp bin client.ResizeTest
```

This demonstrates:
- Viewport initialization
- Window resize simulation
- Position scaling calculations
- Ratio preservation verification

---

## Notes

- The current implementation is **stubbed** (no libGDX dependencies)
- When libGDX is added, uncomment the camera/viewport code
- Resize is called automatically by libGDX when window size changes
- Always scale ALL objects (player, NPCs, UI elements) in resize()
- Use viewport strategies (ScreenViewport, FitViewport) to control scaling behavior
