package breakingumbrella.connectit.domain.classicgame;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.IGameContextFactory;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.entities.EloContainer;
import breakingumbrella.connectit.entity.entities.GameState;
import breakingumbrella.connectit.entity.entities.GameStateNew;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.GameField;
import breakingumbrella.connectit.entity.gameobjects.GameSettings;
import breakingumbrella.connectit.entity.profile.GCProfile;

public class GameContextClassicFactory implements IGameContextFactory {

    @Inject
    GameContextClassicFactory() {}

    @Override
    public GameContext create(GCProfile gcProfile) {
        GameState gameState = new  GameState();
        gameState.setGameStateNew(GameStateNew.WaitingForPlayers);

        ArrayList<GCProfile> profiles = new ArrayList<>();
        profiles.add(gcProfile);

        GCProfile aiGcProfile = new GCProfile();
        aiGcProfile.setId("AiProfileId");
        List<Integer> abilities = new ArrayList<>();
        abilities.add(AbilityType.durableFigure);
        abilities.add(AbilityType.invisibleFigure);
        abilities.add(AbilityType.destroyFigure);
        abilities.add(AbilityType.secondChance);
        aiGcProfile.setMyAbilities(abilities);

        profiles.add(aiGcProfile);

        EloContainer eloContainer = new EloContainer(gcProfile.getElo(), 50);

        GameSettings gameSettings = new GameSettings();
        gameSettings.setPlayersCount(2);

        GameContext gameContext = new GameContext(gameSettings, gameState, profiles, eloContainer);
        gameContext.setGameField(new GameField(8, 8));

        gameContext.getGameSettings().setTargetTurnsNumber(64);
        return gameContext;
    }

}
