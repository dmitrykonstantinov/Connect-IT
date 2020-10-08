package breakingumbrella.connectit.data.repositories.gamecontext;

import javax.inject.Inject;

import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.entity.context.GCGameWrapperKt;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.entity.profile.GCProfile;

public class GameContextSplitModeRepository implements IGameContextRepository {

    GameContext gameContext;

    @Inject
    GameContextSplitModeRepository() {}

    @Override
    public void createGCS(GameContext gcs, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        if(gameContext == null) {
            gameContext = gcs;
            gcs.setId("LocalGameContextId");
            onSuccess.success(gcs);
        }
        else {
            onFailure.fail(new Exception("GameContext already created"));
        }
    }

    @Override
    public void getGCSById(String gcsId, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        if (gameContext == null) {
            onFailure.fail(new Exception("GameContext is null, can't isCellIsHot id"));
        } else {
            GCProfile player = gameContext.getPlayers().get(0);
            GCProfile enemyPlayer = gameContext.getPlayers().get(1);
            gameContext.getGameState().setPlayerIdWhichTurnNow(player.getId());
            if (gameContext.getTurnHistory().size() == 0) {
                onSuccess.success(gameContext);
            } else {
                if (GCGameWrapperKt.turnHistoryLast(gameContext).getFigureType() == FigureTypes.cross/*cross*/) {
                    player.setFigure(new Figure(2));
                    enemyPlayer.setFigure(new Figure(1));
                } else {
                    player.setFigure(new Figure(1));
                    enemyPlayer.setFigure(new Figure(2));
                }
                onSuccess.success(gameContext);
            }
        }
    }

    @Override
    public void updateGCS(GameContext gcs, OnSuccess<Void> onSuccess, OnFailure onFailure) {
        this.gameContext = gcs;
        onSuccess.success(null);
    }

    private GCProfile createEnemy() {
        GCProfile gcProfile = new GCProfile();
        gcProfile.setId("Enemy");
        return gcProfile;
    }

}
