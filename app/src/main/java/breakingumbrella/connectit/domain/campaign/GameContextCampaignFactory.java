package breakingumbrella.connectit.domain.campaign;

import java.util.ArrayList;

import javax.inject.Inject;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.domain.IGameContextFactory;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.entities.EloContainer;
import breakingumbrella.connectit.entity.entities.GameState;
import breakingumbrella.connectit.entity.entities.GameStateNew;
import breakingumbrella.connectit.entity.gameobjects.GameSettings;
import breakingumbrella.connectit.entity.profile.GCProfile;

public class GameContextCampaignFactory implements IGameContextFactory {

    private GameFieldCreator gameFieldCreator;

    private GlobalConfig globalConfig;

    @Inject
    GameContextCampaignFactory(GameFieldCreator gameFieldCreator, GlobalConfig globalConfig) {
        this.gameFieldCreator = gameFieldCreator;
        this.globalConfig = globalConfig;
    }

    @Override
    public GameContext create(GCProfile gcProfile) {
        GameState gameState = new  GameState();
        gameState.setGameStateNew(GameStateNew.WaitingForPlayers);

        ArrayList<GCProfile> profiles = new ArrayList<>();
        profiles.add(gcProfile);

        EloContainer eloContainer = new EloContainer(gcProfile.getElo(), 50);

        GameSettings gameSettings = new GameSettings();
        gameSettings.setPlayersCount(2);
        gameSettings.setCampaignPosition(globalConfig.getProfile().getCampaignPosition());

        GCProfile aiGcProfile = new GCProfile();
        aiGcProfile.setId("AiProfileId");
        aiGcProfile.setMyAbilities(new ArrayList<>());
        profiles.add(aiGcProfile);

        GameContext gameContext = new GameContext(gameSettings, gameState, profiles, eloContainer);
        gameContext = gameFieldCreator.createGameField(gameContext, globalConfig.getProfile().getCampaignPosition());

        return gameContext;
    }
}
