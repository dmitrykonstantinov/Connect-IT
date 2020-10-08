package breakingumbrella.connectit.presentation.splitscreenmode.di;

import java.util.ArrayList;

import breakingumbrella.connectit.IServiceActivity;
import breakingumbrella.connectit.data.repositories.gamecontext.GameContextSplitModeRepository;
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
import breakingumbrella.connectit.drawer.complete.SplitScreenGameComplete;
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
public class SplitScreenModule {

	private final IServiceActivity serviceActivity;

	public SplitScreenModule(IServiceActivity serviceActivity){
		this.serviceActivity = serviceActivity;
	}

	@Provides
	@SplitScreenScope
	public IGCProfileRepository provideGcProfileRepository(GcProfileLocalRepo gcProfileRepository) {
		return gcProfileRepository;
	}

	@Provides
	@SplitScreenScope
	public IGameContextRepository provideGameRepository(GameContextSplitModeRepository gameContextRepository) {
		return gameContextRepository;
	}

	@Provides
	@SplitScreenScope
	public IServiceActivity provideServiceActivity() {
		return serviceActivity;
	}

	@Provides
	@SplitScreenScope
	public IFinishGameUseCase provideFinishGameUseCase(FinishGameUseCase finishGameSSUseCase) {
		return finishGameSSUseCase;
	}

//	@Provides
//	@SplitScreenScope
//	public ICreateGameSSUseCase provideCreateGameUseCase(CreateGameSSUseCase createGameSSUseCase) {
//		return createGameSSUseCase;
//	}

	@Provides
	@SplitScreenScope
	public ICreateGameUseCase provideCreateGameUseCase(CreateGameUseCase createGameSSUseCase) {
		return createGameSSUseCase;
	}

	@Provides
	@SplitScreenScope
	public IPutFigureUseCase providePutFigureUseCase(PutFigureUseCase putFigureMPUseCase) {
		return putFigureMPUseCase;
	}

	@Provides
	@SplitScreenScope
	public IAppErrorHandler provideAppErrorHandler(UIAppErrorHandler uiAppErrorHandler) {
		return uiAppErrorHandler;
	}

	@Provides
	@SplitScreenScope
	public IDrawer provideCompleteDrawer(SplitScreenGameComplete splitScreenGameComplete) {
		return splitScreenGameComplete;
	}

	@Provides
	@SplitScreenScope
	public Ai provideAI() {
		return new Ai();
	}

	@Provides
	@SplitScreenScope
	public IChecking provideChecking() {
		return new Checking();
	}


	@Provides
	@SplitScreenScope
	static GameContext provideGameContext() {
		return new GameContext(new GameSettings(), new GameState(), new ArrayList<>(), new EloContainer());
	}

	@Provides
	@SplitScreenScope
	public IGameContextFactory provideGameContextFactory(GameContextClassicFactory gameContextClassicFactory) {
		return gameContextClassicFactory;
	}

}
