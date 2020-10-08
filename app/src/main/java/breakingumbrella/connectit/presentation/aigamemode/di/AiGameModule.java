package breakingumbrella.connectit.presentation.aigamemode.di;

import java.util.ArrayList;

import breakingumbrella.connectit.IServiceActivity;
import breakingumbrella.connectit.data.repositories.gamecontext.GameContextAiModeRepository;
import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.data.repositories.profilegc.GcProfileLocalRepo;
import breakingumbrella.connectit.data.repositories.profilegc.IGCProfileRepository;
import breakingumbrella.connectit.domain.IGameContextFactory;
import breakingumbrella.connectit.domain.classicgame.GameContextClassicFactory;
import breakingumbrella.connectit.domain.classicgame.usecase.CreateGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.FinishGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.ICreateGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IFinishGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IPutFigureUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.PutFigureUseCase;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.drawer.complete.IDrawer;
import breakingumbrella.connectit.drawer.complete.AiGameModeComplete;
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
public class AiGameModule {

	private final IServiceActivity serviceActivity;

	public AiGameModule(IServiceActivity serviceActivity){
		this.serviceActivity = serviceActivity;
	}

	@Provides
	@AiGameScope
	public IGCProfileRepository provideGcProfileRepository(GcProfileLocalRepo gcProfileRepository) {
		return gcProfileRepository;
	}

	@Provides
	@AiGameScope
	public IGameContextRepository provideGameRepository(GameContextAiModeRepository gameContextRepository) {
		return gameContextRepository;
	}

	@Provides
	@AiGameScope
	public IServiceActivity provideServiceActivity() {
		return serviceActivity;
	}

	@Provides
	@AiGameScope
	public IAppErrorHandler provideAppErrorHandler(UIAppErrorHandler uiAppErrorHandler) {
		return uiAppErrorHandler;
	}

	@Provides
	@AiGameScope
	public IDrawer provideCompleteDrawer(AiGameModeComplete aiGameModeComplete) {
		return aiGameModeComplete;
	}

	@Provides
	@AiGameScope
	public Ai provideAI() {
		return new Ai();
	}

	@Provides
	@AiGameScope
	public IChecking provideChecking() {
		return new Checking();
	}

	@Provides
	@AiGameScope
	public IFinishGameUseCase provideFinishGameUseCase(FinishGameUseCase finishGameAiUseCase) {
		return finishGameAiUseCase;
	}

	@Provides
	@AiGameScope
	public ICreateGameUseCase provideCreateGameUseCase(CreateGameUseCase createGameSSUseCase) {
		return createGameSSUseCase;
	}

	@Provides
	@AiGameScope
	public IPutFigureUseCase providePutFigureUseCase(PutFigureUseCase putFigureMPUseCase) {
		return putFigureMPUseCase;
	}

	@Provides
	@AiGameScope
	static GameContext provideGameContext() {
		return new GameContext(new GameSettings(), new GameState(), new ArrayList<>(), new EloContainer());
	}

	@Provides
	@AiGameScope
	public IGameContextFactory provideGameContextFactory(GameContextClassicFactory gameContextClassicFactory) {
		return gameContextClassicFactory;
	}

}
