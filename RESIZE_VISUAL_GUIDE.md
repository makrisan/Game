# Screen Resize Visual Guide

## The Resize Flow Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                   User Resizes Window                           │
│              (Drags corner or toggles fullscreen)               │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│              libGDX automatically calls:                        │
│              resize(int width, int height)                      │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         ▼
         ┌───────────────────────────────┐
         │   STEP 1: Save Old State      │
         │   ─────────────────────────   │
         │   oldWorldWidth = 800         │
         │   oldWorldHeight = 600        │
         │   playerX = 400 (50% of 800)  │
         │   playerY = 300 (50% of 600)  │
         └───────────────┬───────────────┘
                         │
                         ▼
         ┌───────────────────────────────┐
         │   STEP 2: Update Viewport     │
         │   ─────────────────────────   │
         │   viewport.update(1920, 1080) │
         │   (Recalculates world size)   │
         └───────────────┬───────────────┘
                         │
                         ▼
         ┌───────────────────────────────┐
         │   STEP 3: Get New State       │
         │   ─────────────────────────   │
         │   newWorldWidth = 1920        │
         │   newWorldHeight = 1080       │
         └───────────────┬───────────────┘
                         │
                         ▼
         ┌───────────────────────────────┐
         │   STEP 4: Scale Positions     │
         │   ─────────────────────────   │
         │   playerX = (400/800) * 1920  │
         │          = 0.5 * 1920         │
         │          = 960 ✓              │
         │                               │
         │   playerY = (300/600) * 1080  │
         │          = 0.5 * 1080         │
         │          = 540 ✓              │
         │                               │
         │   (Player still at 50%!)      │
         └───────────────┬───────────────┘
                         │
                         ▼
         ┌───────────────────────────────┐
         │   STEP 5: Recenter Camera     │
         │   ─────────────────────────   │
         │   camera.x = 1920/2 = 960     │
         │   camera.y = 1080/2 = 540     │
         │   camera.update()             │
         └───────────────┬───────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Render Next Frame                            │
│           Everything drawn at new scaled positions              │
└─────────────────────────────────────────────────────────────────┘
```

---

## Position Scaling Visualized

### Before Resize (800x600):
```
┌─────────────────────────────────────────┐
│  0,600                        800,600   │ ▲
│  ┌───────────────────────────────────┐  │ │
│  │                                   │  │ │
│  │                                   │  │ │
│  │            ┌──┐                   │  │ 600px
│  │            │P │ ← Player at       │  │ │
│  │            └──┘   (400, 300)      │  │ │
│  │                   (50%, 50%)      │  │ │
│  │                                   │  │ │
│  └───────────────────────────────────┘  │ │
│  0,0                          800,0     │ ▼
└─────────────────────────────────────────┘
           ◄──────── 800px ────────►
```

### After Resize (1920x1080):
```
┌─────────────────────────────────────────────────────────────────────┐
│  0,1080                                              1920,1080      │ ▲
│  ┌───────────────────────────────────────────────────────────────┐  │ │
│  │                                                               │  │ │
│  │                                                               │  │ │
│  │                                                               │  │ │
│  │                        ┌──┐                                   │  │ 1080px
│  │                        │P │ ← Player at (960, 540)            │  │ │
│  │                        └──┘   (50%, 50%) - Same ratio!        │  │ │
│  │                                                               │  │ │
│  │                                                               │  │ │
│  └───────────────────────────────────────────────────────────────┘  │ │
│  0,0                                                    1920,0      │ ▼
└─────────────────────────────────────────────────────────────────────┘
                    ◄──────────── 1920px ────────────►
```

**Key Point:** Player stays at 50% position in both dimensions!

---

## NPC Scaling Example

### Before Resize (800x600):
```
NPC 1: (320, 480)  →  40% width, 80% height
NPC 2: (640, 300)  →  80% width, 50% height
NPC 3: (400, 150)  →  50% width, 25% height
```

### After Resize to 1920x1080:
```
NPC 1: (320/800)*1920 = 768    (480/600)*1080 = 864   →  40%, 80% ✓
NPC 2: (640/800)*1920 = 1536   (300/600)*1080 = 540   →  80%, 50% ✓
NPC 3: (400/800)*1920 = 960    (150/600)*1080 = 270   →  50%, 25% ✓
```

**All NPCs maintain their relative positions!**

---

## Common Resize Mistakes

### ❌ WRONG: Don't do this
```java
public void resize(int width, int height) {
    // Missing: save old dimensions
    viewport.update(width, height);
    // Missing: scale positions
    // Result: Objects jump to wrong positions!
}
```

### ❌ WRONG: Don't do this either
```java
public void resize(int width, int height) {
    // Don't use screen pixels!
    playerX = width / 2;   // This is screen pixels, not world coords
    playerY = height / 2;
    // Result: Player position changes based on window size!
}
```

### ✅ CORRECT: Do this
```java
public void resize(int width, int height) {
    float oldW = viewport.getWorldWidth();
    float oldH = viewport.getWorldHeight();
    viewport.update(width, height, true);
    float newW = viewport.getWorldWidth();
    float newH = viewport.getWorldHeight();
    
    if (oldW > 0 && oldH > 0) {
        playerX = (playerX / oldW) * newW;  // Preserve ratio
        playerY = (playerY / oldH) * newH;
    }
    
    camera.position.set(newW/2, newH/2, 0);
    camera.update();
}
```

---

## Viewport Types Comparison

### ScreenViewport (Most Common)
```
800x600 window  →  800x600 world
1920x1080 window  →  1920x1080 world

✓ Simple 1:1 mapping
✓ No distortion
✓ Works great with resize scaling
```

### FitViewport (Fixed Aspect Ratio)
```
800x600 window (4:3)  →  800x600 world
1920x1080 window (16:9)  →  1920x1080 world with black bars

✓ Maintains exact aspect ratio
✓ May add black bars (letterboxing)
- World size is fixed
```

### ExtendViewport (Flexible)
```
800x600 window  →  800x600 world minimum
1920x1080 window  →  Extends world to show more

✓ Shows more content on bigger screens
✓ No black bars
- World size varies
```

---

## Testing the Math

Try these calculations yourself:

**Original:** 800x600 world, player at (400, 300)

**Resize to 1280x720:**
- playerX = (400 / 800) * 1280 = ?
- playerY = (300 / 600) * 720 = ?

<details>
<summary>Click to see answer</summary>

- playerX = 0.5 * 1280 = **640** (still at 50%)
- playerY = 0.5 * 720 = **360** (still at 50%)

</details>

---

## Integration Checklist

- [x] Add camera field to Play class
- [x] Add viewport field to Play class
- [x] Add player position fields (playerX, playerY)
- [x] Create initializeViewport() method
- [x] Create resize() method with 5 steps
- [x] Add position getter/setter methods
- [ ] Add libGDX dependencies (future)
- [ ] Uncomment camera/viewport code (future)
- [ ] Test with real window resizing (future)

---

## Quick Reference

**The Magic Formula:**
```java
newPosition = (oldPosition / oldDimension) * newDimension
```

**Remember:**
1. Save old dimensions **BEFORE** viewport.update()
2. Get new dimensions **AFTER** viewport.update()
3. Scale **ALL** objects (player, NPCs, UI)
4. Recenter camera at new center
5. Call camera.update()

**Division by Zero Safety:**
```java
if (oldWorldWidth > 0 && oldWorldHeight > 0) {
    // Safe to scale positions
}
```
