package breakingumbrella.connectit.domain.classicgame.middleware;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.domain.gameobjects.MatchRatingResult;
import breakingumbrella.connectit.entity.context.GCGameWrapperKt;
import breakingumbrella.connectit.entity.context.GCProfileWrapperKt;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.context.GameMetaInfo;
import breakingumbrella.connectit.entity.entities.GameResult;
import breakingumbrella.connectit.entity.entities.GameStateNew;
import breakingumbrella.connectit.entity.profile.EloCalculator;

public class FinishGame {

    public void finish(GameContext gameContext, GameMetaInfo gameMetaInfo, GlobalConfig globalConfig,
                       IGameContextRepository gameRepository, IProfileRepository profileRepository,
                       OnSuccess<GameResult> onSuccess, OnFailure onFailure) {
        EloCalculator eloCalculator = new EloCalculator();
        int myNewRating;
        int oldRating;
        if (GCProfileWrapperKt.getMe(gameContext, gameMetaInfo.getGcProfileId()).getScore() >
                GCProfileWrapperKt.getEnemy(gameContext, gameMetaInfo.getGcProfileId()).getScore()) {
            myNewRating = eloCalculator.getWinRating(globalConfig.getProfile().getElo(), GCProfileWrapperKt.getEnemy(gameContext, gameMetaInfo.getGcProfileId()).getElo());
        } else if (GCProfileWrapperKt.getMe(gameContext, gameMetaInfo.getGcProfileId()).getScore() <
                GCProfileWrapperKt.getEnemy(gameContext, gameMetaInfo.getGcProfileId()).getScore()) {
            myNewRating = eloCalculator.getLoseRating(globalConfig.getProfile().getElo(), GCProfileWrapperKt.getEnemy(gameContext, gameMetaInfo.getGcProfileId()).getElo());
        } else {
            myNewRating = eloCalculator.getTieRating(globalConfig.getProfile().getElo(), GCProfileWrapperKt.getEnemy(gameContext, gameMetaInfo.getGcProfileId()).getElo());
        }
        oldRating = globalConfig.getProfile().getElo();
        globalConfig.getProfile().setElo(myNewRating);
        profileRepository.updateProfile(globalConfig.getProfile(), aVoid -> {
            //Do nothing
        }, onFailure::fail);
        gameContext.getGameState().setGameStateNew(GameStateNew.ScheduledToDelete);
        gameRepository.updateGCS(gameContext, aVoid -> {
            //Do nothing
        }, onFailure::fail);
        GameResult gameResult = new GameResult();
        gameResult.setMatchResult(GCGameWrapperKt.getMatchResult(gameContext, gameMetaInfo.getGcProfileId()));
        gameResult.setMatchRatingResult(new MatchRatingResult(myNewRating, oldRating, gameContext.getTurnNumber()));

        onSuccess.success(gameResult);
    }

}
