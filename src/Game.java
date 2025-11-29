import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<NPC> aiBots = new ArrayList<>();

    public Game(int gameId) {
        this.generateAiBots();
    }

    private void generateAiBots() {
        this.aiBots.add(new NPC(0, 0));
        this.aiBots.add(new NPC(1, 1));
    }

    public List<NPC> getAiBots() {
        return this.aiBots;
    }
}

