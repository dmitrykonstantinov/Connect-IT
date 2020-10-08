package breakingumbrella.connectit.data.repositories.gamecontext;

import android.os.Handler;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.gamelogic.ai.Ai;
import breakingumbrella.connectit.entity.gamelogic.ai.AiSettings;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.entity.profile.CampaignPosition;
import breakingumbrella.connectit.entity.profile.GCProfile;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class GameContextAiModeRepository implements IGameContextRepository {

    private GameContext gameContext;
    private Random rand = new Random();
    private String enemyId = "AiProfileId";
    private boolean secondChanceUsed = false;

    @Inject
    GameContextAiModeRepository() {
    }

    @Override
    public void createGCS(GameContext gcs, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        if (gameContext == null) {
            gameContext = gcs;
            gcs.setId("LocalGameContextId");
            onSuccess.success(gcs);
        } else {
            onFailure.fail(new Exception("GameContext already created"));
        }
    }

    @Override
    public void getGCSById(String gcsId, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        if (gameContext == null) {
            onFailure.fail(new Exception("GameContext is null, can't isCellIsHot id"));
            return;
        }
        if (gameContext.getTurnHistory().size() == 0 /*|| GCGameWrapperKt.turnHistoryLast(gameContext).getFigureType() == 2*/) {
            onSuccess.success(gameContext);
            return;
        }
        GCProfile player = gameContext.getPlayers().get(0); //It's me
        if (gameContext.getGameState().getPlayerIdWhichTurnNow().equals(player.getId()) && secondChanceUsed == false) {
            gameContext.getGameState().setPlayerIdWhichTurnNow(enemyId);
            onSuccess.success(gameContext);
        } else {
            secondChanceUsed = false;
            List<Integer> abilities = gameContext.getPlayers().get(1).getMyAbilities();

            int turnsLeft = gameContext.getGameField().getTotoalSize() - gameContext.getTurnHistory().size();
            int abilitiesLeft = abilities.size();

            float probability = (float) abilitiesLeft / turnsLeft;
            int res = rand.nextInt(100);
            if (res <= probability * 100 * 2 && abilities.size() > 0) { //Use Ability
                Integer abilityType = abilities.get(rand.nextInt(abilities.size()));
                abilities.remove(abilityType);
                useAbility(abilityType, player, onSuccess, onFailure);
            } else {
                startAi(figure -> {
                    gameContext.getGameState().setPlayerIdWhichTurnNow(player.getId());
                    gameContext.getTurnHistory().add(figure);
                    onSuccess.success(gameContext);
                    return null;
                });
            }
        }
    }

    private void useAbility(Integer abilityType, GCProfile player, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        switch (abilityType) {
            case AbilityType.durableFigure:
                useDurableFigure(player, onSuccess, onFailure);
                break;
            case AbilityType.invisibleFigure:
                useInvisibleFigure(player, onSuccess, onFailure);
                break;
            case AbilityType.destroyFigure:
                useDestroyFigure(player, onSuccess, onFailure);
                break;
            case AbilityType.secondChance:
                useSecondChance(player, onSuccess, onFailure);
                break;
        }
    }

    private void useDurableFigure(GCProfile player, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        startAi(figure -> {
            Figure figure2 = new Figure(FigureTypes.circle);
            figure2.setPosition(figure.getPositionX(), figure.getPositionY());
            figure2.setAbilityType(AbilityType.durableFigure);
            gameContext.getGameState().setPlayerIdWhichTurnNow(player.getId());
            gameContext.getTurnHistory().add(figure2);
            onSuccess.success(gameContext);
            return null;
        });
    }

    private void useInvisibleFigure(GCProfile player, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        startAi(figure -> {
            figure.setAbilityType(AbilityType.invisibleFigure);
            gameContext.getGameState().setPlayerIdWhichTurnNow(player.getId());
            gameContext.getTurnHistory().add(figure);
            onSuccess.success(gameContext);
            return null;
        });
    }

    private void useDestroyFigure(GCProfile player, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        int xPos = rand.nextInt(gameContext.getGameField().getSizeX());
        int yPos = rand.nextInt(gameContext.getGameField().getSizeY());
        //TODO it can be infinity if all player figures are crossed
        while (gameContext.getGameField().getFigureAtPosition(xPos, yPos).getFigureType() != player.getFigure().getFigureType()) {
            xPos = rand.nextInt(gameContext.getGameField().getSizeX());
            yPos = rand.nextInt(gameContext.getGameField().getSizeY());
        }
        Figure figureToDelete = new Figure();
        figureToDelete.setPosition(xPos, yPos);
        figureToDelete.setFigure(FigureTypes.circle);
        figureToDelete.setAbilityType(AbilityType.destroyFigure);
        gameContext.getGameState().setPlayerIdWhichTurnNow(player.getId());
        gameContext.getGameField().removeFigure(figureToDelete);
        gameContext.getTurnHistory().add(figureToDelete);
        onSuccess.success(gameContext);
    }

    private void useSecondChance(GCProfile player, OnSuccess<GameContext> onSuccess, OnFailure onFailure) {
        startAi(figure -> {
            figure.setAbilityType(AbilityType.secondChance);
            this.secondChanceUsed = true;
            gameContext.getGameState().setPlayerIdWhichTurnNow(player.getId());
            gameContext.getTurnHistory().add(figure);
            onSuccess.success(gameContext);
            return null;
        });
    }

    private void startAi(Function1<Figure, Unit> aiCallback) {
        new Handler().postDelayed(() -> {
            CampaignPosition currentPosition = gameContext.getGameSettings().getCampaignPosition();
            new Thread(() -> new Ai().startAi(new AiSettings(currentPosition.getTrip()),gameContext, enemyId, aiCallback)).start();
        }, 600);

    }

    @Override
    public void updateGCS(GameContext gcs, OnSuccess<Void> onSuccess, OnFailure onFailure) {
        this.gameContext = gcs;
        onSuccess.success(null);
    }



}
