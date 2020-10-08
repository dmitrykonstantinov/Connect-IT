package breakingumbrella.connectit.entity.context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameMetaInfo {

    @Inject
    public GameMetaInfo() { }

    String gcProfileId;
    String gameId;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGcProfileId() {
        return gcProfileId;
    }

    public void setGcProfileId(String gcProfileId) {
        this.gcProfileId = gcProfileId;
    }
}
