package breakingumbrella.connectit.domain.classicgame.usecase;

import java.util.ArrayList;

import javax.inject.Inject;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.data.repositories.profilegc.IGCProfileRepository;
import breakingumbrella.connectit.domain.IGameContextFactory;
import breakingumbrella.connectit.domain.gamestate.StateHolder;
import breakingumbrella.connectit.domain.classicgame.middleware.GetGameContext;
import breakingumbrella.connectit.domain.classicgame.middleware.GetGcProfile;
import breakingumbrella.connectit.domain.gamestate.WaitingForEnemyTurn;
import breakingumbrella.connectit.domain.gamestate.WaitingForPlayerTurn;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseCreateGamePresentation;
import breakingumbrella.connectit.entity.context.GameMetaInfo;
import breakingumbrella.connectit.entity.context.GCGameWrapperKt;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.entities.GameStateNew;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.profile.GCProfile;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.AppError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public class CreateGameUseCase implements ICreateGameUseCase {

	private IGameContextRepository gameRepository;
	private IGCProfileRepository gcProfileRepository;
	private IProfileRepository profileRepository;
	private StateHolder stateHolder;
	private IBaseCreateGamePresentation createGamePresentation;
	private IAppErrorHandler appErrorHandler;
	private IApiErrorHandler apiErrorHandler;
	private IGameContextFactory gameContextFactory;
	private GlobalConfig globalConfig;
	private GameMetaInfo gameMetaInfo;
//    private GameContext gameContext;

	private boolean isStateIsTerminated = false;

	private GetGcProfile getGcProfile = new GetGcProfile();
	private GetGameContext getGameContext = new GetGameContext();

	@Inject
	public CreateGameUseCase(GameMetaInfo gameMetaInfo, IGameContextRepository gameRepository, IProfileRepository profileRepository,
							 IGCProfileRepository gcProfileRepository, StateHolder stateHolder,
							 GlobalConfig globalConfig, IGameContextFactory gameContextFactory) {
		this.gameMetaInfo = gameMetaInfo;
		this.gcProfileRepository = gcProfileRepository;
		this.gameRepository = gameRepository;
		this.stateHolder = stateHolder;
		this.globalConfig = globalConfig;
		this.profileRepository = profileRepository;
		this.gameContextFactory = gameContextFactory;
	}

	private void handleApiException(Exception exception) {
		apiErrorHandler.handleApiError(new ApiError(exception));
	}

	@Override
	public void createGame(IBaseCreateGamePresentation createGamePresentation, IAppErrorHandler appErrorHandler, IApiErrorHandler apiErrorHandler) {
		this.createGamePresentation = createGamePresentation;
		this.appErrorHandler = appErrorHandler;
		this.apiErrorHandler = apiErrorHandler;
		//Step 1 - Check if room already exist
		//Step 2 - Create room, event if many room already exists
		//Step 3 - Starting to update room info
		//Step 4 - Merge Rooms
		//Step 5 - Start Game
		try {
			getGcProfile.getGcProfile(globalConfig.getProfile(), gcProfileRepository,
					gcProfile -> handleGcProfile(gcProfile),
					this::handleApiException);
		} catch (Exception ex) {
			handleApiException(ex);
		}
	}

	private void handleGcProfile(GCProfile gcProfile) { //GameContext is creating here
		gameMetaInfo.setGcProfileId(gcProfile.getId());
		getGameContext.get(gcProfile, gameRepository, gameContextFactory,
				this::startGame,
				this::handleApiException);
	}

	private void startGame(GameContext gcs) {
		gcs.getGameState().setGameStateNew(GameStateNew.InProgress);
		gcs.getGameState().setPlayerIdWhichTurnNow(gcs.getPlayers().get(0).getId()); //TODO Replace with Random
		gcs.getPlayers().get(0).setFigure(new Figure(1)); //TODO Replace with Random
		gcs.getPlayers().get(1).setFigure(new Figure(2)); //TODO Replace with Random
		gcs.setTurnHistory(new ArrayList<>());
		gameRepository.updateGCS(gcs, aVoid -> switchToNextState(gcs), exc1 -> apiErrorHandler.handleApiError(new ApiError(exc1)));
	}

	private void switchToNextState(GameContext gcs) {
		try {
			if (GCGameWrapperKt.isItMyTurn(gcs, gameMetaInfo.getGcProfileId())) {
				stateHolder.setGameState(new WaitingForPlayerTurn(gameMetaInfo, gameRepository));
			} else {
				stateHolder.setGameState(new WaitingForEnemyTurn(globalConfig, gameMetaInfo, gameRepository, profileRepository));
			}
			gameMetaInfo.setGameId(gcs.getId());
			createGamePresentation.onGameCreated(gcs);
		} catch (Exception e) {
			appErrorHandler.handleAppError(new AppError(e));
		}
	}

	@Override
	public void terminate(IAppErrorHandler appErrorHandler) {
		isStateIsTerminated = true;
	}

}