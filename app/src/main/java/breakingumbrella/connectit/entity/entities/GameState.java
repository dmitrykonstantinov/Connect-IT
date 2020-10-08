package breakingumbrella.connectit.entity.entities;

public class GameState {

    private GameStateNew gameStateNew;
    private String playerIdWhichTurnNow;
    private long timeSinceSheduled;

    public GameStateNew getGameStateNew() {
        return gameStateNew;
    }

    public void setGameStateNew(GameStateNew gameStateNew) {
        this.gameStateNew = gameStateNew;
    }

    public String getPlayerIdWhichTurnNow() {
        return playerIdWhichTurnNow;
    }

    public void setPlayerIdWhichTurnNow(String playerIdWhichTurnNow) {
        this.playerIdWhichTurnNow = playerIdWhichTurnNow;
    }

    public long getTimeSinceSheduled() {
        return timeSinceSheduled;
    }

    public void setTimeSinceSheduled(long timeSinceScheduled) {
        this.timeSinceSheduled = timeSinceScheduled;
    }

}
