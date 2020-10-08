//package breakingumbrella.tictactocmobileapp.data.repositories.gamecontext;
//
//import javax.inject.Inject;
//
//import breakingumbrella.tictactocmobileapp.data.repositories.boundaries.OnFailure;
//import breakingumbrella.tictactocmobileapp.data.repositories.boundaries.OnSuccess;
//import breakingumbrella.tictactocmobileapp.entity.gamelogic.ai.AiSettings;
//import breakingumbrella.tictactocmobileapp.entity.gameobjects.FigureTypes;
//import breakingumbrella.tictactocmobileapp.entity.context.GCGameWrapperKt;
//import breakingumbrella.tictactocmobileapp.entity.context.GameContext;
//import breakingumbrella.tictactocmobileapp.entity.gamelogic.ai.Ai;
//import breakingumbrella.tictactocmobileapp.entity.profile.GCProfile;
//
//public class GameContextCampaignRepository implements IGameContextRepository {
//
//    private GameContext gameContext;
//    private Ai ai;
//
//    @Inject
//    GameContextCampaignRepository() {
//    }
//
//    @Override
//    public void createGCS(GameContext gcs, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
//        if (gameContext == null) {
//            gameContext = gcs;
//            gcs.setId("LocalGameContextId");
//            onSuccess.success(gcs);
//        } else {
//            onFailure.fail(new Exception("GameContext already created"));
//        }
//    }
//
//    @Override
//    public void getGCSById(String gcsId, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
//        if (gameContext == null) {
//            onFailure.fail(new Exception("GameContext is null, can't isCellIsHot id"));
//            return;
//        }
//        if (gameContext.getTurnHistory().size() == 0 ||
//                GCGameWrapperKt.turnHistoryLast(gameContext).getFigureType() == FigureTypes.circle) {
//            onSuccess.success(gameContext);
//            return;
//        }
//        GCProfile player = gameContext.getPlayers().get(0); //It's me
//        if (GCGameWrapperKt.turnHistoryLast(gameContext).getFigureType() == FigureTypes.cross) {
//            new Thread(() -> new Ai().startAi(new AiSettings(gameContext.getGameSettings().getCampaignPosition().getTrip()), gameContext, "AiProfileId",
//                    figure -> {
//                        gameContext.getGameState().setPlayerIdWhichTurnNow(player.getId());
//                        gameContext.getTurnHistory().add(figure);
//                        onSuccess.success(gameContext);
//                        return null;
//                    })).start();
//        }
//    }
//
//    @Override
//    public void updateGCS(GameContext gcs, OnSuccess<Void> onSuccess, OnFailure onFailure) {
//        this.gameContext = gcs;
//        onSuccess.success(null);
//    }
//}
//
//
//
//
