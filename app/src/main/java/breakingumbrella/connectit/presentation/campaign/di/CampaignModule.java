package breakingumbrella.connectit.presentation.campaign.di;

import java.util.ArrayList;

import breakingumbrella.connectit.IServiceActivity;
//import breakingumbrella.tictactocmobileapp.data.repositories.gamecontext.GameContextCampaignRepository;
import breakingumbrella.connectit.data.repositories.gamecontext.GameContextAiModeRepository;
import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.data.repositories.profilegc.GcProfileLocalRepo;
import breakingumbrella.connectit.data.repositories.profilegc.IGCProfileRepository;
import breakingumbrella.connectit.domain.IGameContextFactory;
import breakingumbrella.connectit.domain.campaign.GameContextCampaignFactory;
import breakingumbrella.connectit.domain.campaign.IUpdateProfileUseCase;
import breakingumbrella.connectit.domain.campaign.UpdateProfileUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.CreateGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.FinishGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.ICreateGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IFinishGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IPutFigureUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.PutFigureUseCase;
import breakingumbrella.connectit.drawer.complete.AiGameModeComplete;
import breakingumbrella.connectit.drawer.complete.IDrawer;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.entities.EloContainer;
import breakingumbrella.connectit.entity.entities.GameState;
import breakingumbrella.connectit.entity.gamelogic.ai.Ai;
import breakingumbrella.connectit.entity.gamelogic.boundaries.IChecking;
import breakingumbrella.connectit.entity.gamelogic.checking.Checking;
import breakingumbrella.connectit.entity.gameobjects.GameSettings;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;
import breakingumbrella.connectit.error.handlers.UIAppErrorHandler;
import dagger.Module;
import dagger.Provides;

@Module
public class CampaignModule {

    private final IServiceActivity serviceActivity;

    public CampaignModule(IServiceActivity serviceActivity) {
        this.serviceActivity = serviceActivity;
    }

    @Provides
    @CampaignScope
    public IUpdateProfileUseCase provideLevelUseCase(UpdateProfileUseCase updateProfileUseCase) {
        return updateProfileUseCase;
    }

    @Provides
    @CampaignScope
    public IGCProfileRepository provideGcProfileRepository(GcProfileLocalRepo gcProfileRepository) {
        return gcProfileRepository;
    }

    @Provides
    @CampaignScope
    public IGameContextRepository provideGameRepository(GameContextAiModeRepository gameContextAiModeRepository) {
        return gameContextAiModeRepository;
    }

    @Provides
    @CampaignScope
    public IServiceActivity provideServiceActivity() {
        return serviceActivity;
    }

    @Provides
    @CampaignScope
    public IAppErrorHandler provideAppErrorHandler(UIAppErrorHandler uiAppErrorHandler) {
        return uiAppErrorHandler;
    }

    @Provides
    @CampaignScope
    public IDrawer provideCompleteDrawer(AiGameModeComplete aiGameModeComplete) {
        return aiGameModeComplete;
    }

    @Provides
    @CampaignScope
    public Ai provideAI() {
        return new Ai();
    }

    @Provides
    @CampaignScope
    public IChecking provideChecking() {
        return new Checking();
    }

    @Provides
    @CampaignScope
    public IFinishGameUseCase provideFinishGameUseCase(FinishGameUseCase finishGameAiUseCase) {
        return finishGameAiUseCase;
    }

    @Provides
    @CampaignScope
    public ICreateGameUseCase provideCreateGameUseCase(CreateGameUseCase createGameSSUseCase) {
        return createGameSSUseCase;
    }

    @Provides
    @CampaignScope
    public IPutFigureUseCase providePutFigureUseCase(PutFigureUseCase putFigureMPUseCase) {
        return putFigureMPUseCase;
    }

    @Provides
    @CampaignScope
    static GameContext provideGameContext() {
        return new GameContext(new GameSettings(), new GameState(), new ArrayList<>(), new EloContainer());
    }

    @Provides
    @CampaignScope
    public IGameContextFactory provideGameContextFactory(GameContextCampaignFactory gameContextCampaignFactory) {
        return gameContextCampaignFactory;
    }


}
