package breakingumbrella.connectit.data.repositories;

import com.google.firebase.firestore.FieldPath;

import javax.inject.Inject;

public class DataPaths {

    @Inject
    DataPaths(){}

    public FieldPath getEloPath() {
        String eloContainerPath = "eloContainer";
        String eloPath = "elo";
        return FieldPath.of(eloContainerPath, eloPath);
    }

    public FieldPath getGameStatePath() {
        String gameStatePath = "gameState";
        String gameStateLegacyPath = "gameStateNew";
        return FieldPath.of(gameStatePath, gameStateLegacyPath);
    }

}
