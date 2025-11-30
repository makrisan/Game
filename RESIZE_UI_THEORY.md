# UI Responsiveness Theory - Buttons and HUD

## Integration into Existing Material

This extends the resize methodology to cover UI elements like buttons and HUD components.

---

## 3. Kohandage nupud ja HUD (Heads-Up Display) ekraani suuruse muutustele

Lisaks mängija ja objektide positsioonide kohandamisele tuleb tagada, et ka kasutajaliides (UI) - nupud, tekstid, ikoonid ja HUD elemendid kohanduksid dünaamiliselt ekraani suurusega. 

---

## Nuppude Responsiivsus

### Probleem:
Kui nupp on määratud fikseeritud koordinaatidega (nt `x=100, y=50`), siis:
- Väiksel ekraanil võib nupp olla liiga suur või asetuda ekraani servale
- Suurel ekraanil võib nupp jääda nurka ja olla raskesti leitav

### Lahendus: Protsendipõhised Positsioonid

Nuppude positsioonid arvutatakse ekraani suuruse suhtes:

```java
// Nupu laius ja kõrgus kui protsent ekraanist
float buttonWidth = viewport.getWorldWidth() * 0.15f;   // 15% ekraani laiusest
float buttonHeight = viewport.getWorldHeight() * 0.08f; // 8% ekraani kõrgusest

// Nupu positsioon (nt alumises paremas nurgas)
float buttonX = viewport.getWorldWidth() - buttonWidth - 20f;  // 20px servast
float buttonY = 20f; // 20px põhjast

// Või keskele joondatud nupp
float centerButtonX = (viewport.getWorldWidth() - buttonWidth) / 2;
float centerButtonY = (viewport.getWorldHeight() - buttonHeight) / 2;
```

### Näide: Pause Nupp

```java
public class PauseButton {
    private float x, y, width, height;
    
    // Konstruktor loob nupu ekraani üleval paremas nurgas
    public PauseButton(Viewport viewport) {
        updatePosition(viewport);
    }
    
    // Kutsutakse välja resize() meetodis
    public void updatePosition(Viewport viewport) {
        // Nupu suurus: 10% ekraani laiusest ja kõrgusest
        this.width = viewport.getWorldWidth() * 0.1f;
        this.height = viewport.getWorldHeight() * 0.1f;
        
        // Positsioon: ülemine parem nurk, 10px servast
        this.x = viewport.getWorldWidth() - width - 10f;
        this.y = viewport.getWorldHeight() - height - 10f;
    }
    
    public void draw(SpriteBatch batch) {
        // Joonista nupp arvutatud koordinaatidel
        batch.draw(pauseTexture, x, y, width, height);
    }
    
    public boolean isClicked(float touchX, float touchY) {
        return touchX >= x && touchX <= x + width &&
               touchY >= y && touchY <= y + height;
    }
}
```

---

## HUD Responsiivsus

### HUD Elemendid:

HUD sisaldab tavaliselt:
- **Skoor/punktid** (ülemine vasak nurk)
- **Elu/tervis** (ülemine vasak või keskel)
- **Aeg** (ülemine keskpaik)
- **Minimapp** (alumine parem nurk)
- **Nupud** (servades)

### Põhimõte:

Iga HUD element peab:
1. Arvutama oma suuruse ekraani suuruse põhjal
2. Asetuma ekraani suhtelisele positsioonile (nt 5% servast)
3. Kasutama teksti fonti, mis skaleerub ekraaniga

### Näide: HUD Klass

```java
public class HUD {
    private Viewport viewport;
    private BitmapFont font;
    
    // HUD elemendide positsioonid
    private float scoreX, scoreY;
    private float healthX, healthY;
    private float healthBarWidth, healthBarHeight;
    
    public HUD(Viewport viewport) {
        this.viewport = viewport;
        this.font = new BitmapFont();
        updateLayout();
    }
    
    // Kutsutakse välja resize() meetodis
    public void updateLayout() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        
        // Skoori positsioon: ülemine vasak nurk
        scoreX = worldWidth * 0.02f;  // 2% servast
        scoreY = worldHeight * 0.98f; // 98% alt
        
        // Eluriba mõõtmed ja positsioon
        healthBarWidth = worldWidth * 0.3f;   // 30% ekraani laiusest
        healthBarHeight = worldHeight * 0.03f; // 3% kõrgusest
        healthX = worldWidth * 0.02f;
        healthY = worldHeight * 0.90f;
        
        // Fondi suurus ekraani põhjal
        float fontSize = Math.min(worldWidth, worldHeight) * 0.03f;
        font.getData().setScale(fontSize / font.getLineHeight());
    }
    
    public void draw(SpriteBatch batch, int score, int health, int maxHealth) {
        batch.begin();
        
        // Joonista skoor
        font.draw(batch, "Score: " + score, scoreX, scoreY);
        
        // Joonista eluriba taust (punane)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(healthX, healthY, healthBarWidth, healthBarHeight);
        
        // Joonista eluriba (roheline - proportsionaalne tervisele)
        float currentHealthWidth = (health / (float)maxHealth) * healthBarWidth;
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(healthX, healthY, currentHealthWidth, healthBarHeight);
        shapeRenderer.end();
        
        batch.end();
    }
    
    public void dispose() {
        font.dispose();
    }
}
```

---

## resize() Meetodi Laiendamine UI-ga

Täielik resize() meetod, mis hõlmab mängijat, objekte, nuppe ja HUD-i:

```java
@Override
public void resize(int width, int height) {
    // 1. Salvesta vanad mõõtmed
    float oldWorldWidth = viewport.getWorldWidth();
    float oldWorldHeight = viewport.getWorldHeight();
    
    // 2. Uuenda viewport
    viewport.update(width, height, true);
    
    // 3. Hangi uued mõõtmed
    float newWorldWidth = viewport.getWorldWidth();
    float newWorldHeight = viewport.getWorldHeight();
    
    // 4. Skaala mängija positsioon
    if (oldWorldWidth > 0 && oldWorldHeight > 0) {
        playerX = (playerX / oldWorldWidth) * newWorldWidth;
        playerY = (playerY / oldWorldHeight) * newWorldHeight;
        
        // Skaala NPC-d
        for (NPC npc : npcs) {
            npc.x = (npc.x / oldWorldWidth) * newWorldWidth;
            npc.y = (npc.y / oldWorldHeight) * newWorldHeight;
        }
    }
    
    // 5. Uuenda UI elemendid
    pauseButton.updatePosition(viewport);
    menuButton.updatePosition(viewport);
    hud.updateLayout();
    
    // 6. Tsentreeri kaamera
    camera.position.set(newWorldWidth / 2, newWorldHeight / 2, 0);
    camera.update();
}
```

---

## Praktilised Näpunäited

### 1. Minimaalsed ja Maksimaalsed Suurused

Mõnikord on vaja seada piirangud, et UI ei muutuks liiga väikeseks ega liiga suureks:

```java
// Nupu laius: 15% ekraanist, aga mitte väiksem kui 80px ega suurem kui 200px
float buttonWidth = Math.max(80f, Math.min(200f, viewport.getWorldWidth() * 0.15f));
```

### 2. Ankrupunktid (Anchor Points)

Määrake, kus UI element peaks kinnituma:

```java
// TOP_LEFT (üleval vasakul)
x = margin;
y = viewport.getWorldHeight() - height - margin;

// TOP_RIGHT (üleval paremal)
x = viewport.getWorldWidth() - width - margin;
y = viewport.getWorldHeight() - height - margin;

// BOTTOM_LEFT (all vasakul)
x = margin;
y = margin;

// BOTTOM_RIGHT (all paremal)
x = viewport.getWorldWidth() - width - margin;
y = margin;

// CENTER (keskele)
x = (viewport.getWorldWidth() - width) / 2;
y = (viewport.getWorldHeight() - height) / 2;
```

### 3. Teksti Skaleerimine

```java
// Fondi suurus põhineb väiksemasel ekraani dimensioonil
float baseFontSize = Math.min(viewport.getWorldWidth(), viewport.getWorldHeight());
float fontSize = baseFontSize * 0.04f; // 4% väiksemast dimensioonist

font.getData().setScale(fontSize / font.getLineHeight());
```

### 4. Safe Zone (Turvaline Tsoon)

Jätke serva äärest turvaline tsoon, et UI ei oleks liiga lähedal ekraani servale:

```java
float safeZoneMargin = Math.min(viewport.getWorldWidth(), viewport.getWorldHeight()) * 0.02f;
// 2% väiksemast dimensioonist
```

---

## Täielik UI Näide

```java
public class ResponsiveUI {
    private Viewport viewport;
    private List<UIElement> elements;
    
    public ResponsiveUI(Viewport viewport) {
        this.viewport = viewport;
        this.elements = new ArrayList<>();
        
        // Lisa UI elemendid
        elements.add(new Button("Pause", AnchorPoint.TOP_RIGHT));
        elements.add(new Button("Menu", AnchorPoint.TOP_LEFT));
        elements.add(new HealthBar(AnchorPoint.TOP_LEFT));
        elements.add(new ScoreDisplay(AnchorPoint.TOP_CENTER));
    }
    
    public void resize() {
        // Uuenda kõigi UI elementide suurused ja positsioonid
        for (UIElement element : elements) {
            element.updateLayout(viewport);
        }
    }
    
    public void draw(SpriteBatch batch) {
        for (UIElement element : elements) {
            element.draw(batch);
        }
    }
}

// Abstraktne UI element
abstract class UIElement {
    protected float x, y, width, height;
    protected AnchorPoint anchor;
    
    public UIElement(AnchorPoint anchor) {
        this.anchor = anchor;
    }
    
    public abstract void updateLayout(Viewport viewport);
    public abstract void draw(SpriteBatch batch);
    
    protected void calculatePosition(Viewport viewport, float width, float height) {
        float margin = Math.min(viewport.getWorldWidth(), viewport.getWorldHeight()) * 0.02f;
        
        switch (anchor) {
            case TOP_LEFT:
                x = margin;
                y = viewport.getWorldHeight() - height - margin;
                break;
            case TOP_RIGHT:
                x = viewport.getWorldWidth() - width - margin;
                y = viewport.getWorldHeight() - height - margin;
                break;
            case TOP_CENTER:
                x = (viewport.getWorldWidth() - width) / 2;
                y = viewport.getWorldHeight() - height - margin;
                break;
            case BOTTOM_LEFT:
                x = margin;
                y = margin;
                break;
            case BOTTOM_RIGHT:
                x = viewport.getWorldWidth() - width - margin;
                y = margin;
                break;
            case CENTER:
                x = (viewport.getWorldWidth() - width) / 2;
                y = (viewport.getWorldHeight() - height) / 2;
                break;
        }
    }
}

enum AnchorPoint {
    TOP_LEFT, TOP_RIGHT, TOP_CENTER,
    BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM_CENTER,
    CENTER
}
```

---

## Kokkuvõte

**UI Responsiivsuse Põhiprintsiibid:**

1. **Proportsionaalsed Suurused** - Kasuta protsente, mitte piksli väärtusi
2. **Ankrupunktid** - Kinnita elemendid ekraani konkreetsetesse osadesse
3. **Minimaalsed/Maksimaalsed Piirid** - Ära lase UI-l muutuda liiga väikeseks või suureks
4. **Turvaline Tsoon** - Hoia UI elementid piisavalt kaugel servadest
5. **Skaleeru Teksti** - Fondi suurus peab kohanduma ekraaniga
6. **Uuenda resize()-s** - Kõik UI peab ümber arvutama positsioonid ja suurused

**resize() Meetodi Samm-Sammult:**
1. Salvesta vanad mõõtmed
2. Uuenda viewport
3. Hangi uued mõõtmed
4. Skaala mängu objektid (mängija, NPC-d)
5. **Uuenda UI layout (nupud, HUD, menüüd)**
6. Tsentreeri kaamera

---

## Integreerimine Olemasolevasse Materjali

Lisa see jaotis punkti 2 järele:

```
1. Avage mäng täisekraanirežiimis.
   [Olemasolev sisu...]

2. Kohandage mängu loogikat ja graafikat...
   [Olemasolev sisu...]

3. Kohandage nupud ja HUD ekraani suuruse muutustele
   [Uus sisu - see dokument]
   
   3.1. Nuppude responsiivsus
        - Protsendipõhised positsioonid
        - Ankrupunktid
        
   3.2. HUD responsiivsus
        - Skoor, elu, aeg
        - Proportsionaalsed suurused
        
   3.3. resize() meetodi laiendamine
        - UI elementide uuendamine
        - Teksti skaleerimine

4. Täielik näide mini-mängust
   [Olemasolev GameExample + UI elemendid]
```
