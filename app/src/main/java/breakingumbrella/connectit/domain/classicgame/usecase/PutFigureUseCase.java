package breakingumbrella.connectit.domain.classicgame.usecase;

import java.lang.reflect.Type;

import javax.inject.Inject;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseShowEnemyAbility;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.domain.gamestate.StateHolder;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseCreateGamePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseGameFinishPresentation;
import breakingumbrella.connectit.entity.context.GCGameWrapperKt;
import breakingumbrella.connectit.entity.context.GameMetaInfo;
import breakingumbrella.connectit.domain.gamestate.IStateChanger;
import breakingumbrella.connectit.domain.gamestate.WaitingForEnemyTurn;
import breakingumbrella.connectit.domain.gamestate.WaitingForPlayerTurn;
import breakingumbrella.connectit.domain.classicgame.presentation.IBasePutFigurePresentation;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.AppError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public class PutFigureUseCase implements IPutFigureUseCase, IStateChanger {

	private StateHolder stateHolder;
	private IBaseCreateGamePresentation createGamePresentation;
	private IBasePutFigurePresentation putFigurePresentation;
	private IBaseGameFinishPresentation gameFinishPresentation;
	private IBaseShowEnemyAbility showEnemyAbility;
//	private ITimerPresentation timerPresentation;
	private IAppErrorHandler appErrorHandler;
	private IApiErrorHandler apiErrorHandler;
	private IGameContextRepository gameContextRepository;
	private GlobalConfig globalConfig;
	private GameMetaInfo gameMetaInfo;
	private IProfileRepository profileRepository;

	@Inject
	public PutFigureUseCase(StateHolder stateHolder, IGameContextRepository gameContextRepository,
							IProfileRepository profileRepository, GameMetaInfo gameMetaInfo, GlobalConfig globalConfig) {
		this.stateHolder = stateHolder;
		this.gameContextRepository = gameContextRepository;
		this.gameMetaInfo = gameMetaInfo;
		this.globalConfig = globalConfig;
		this.profileRepository = profileRepository;
	}

	@Override
	public void lateInit(IBaseCreateGamePresentation createGamePresentation, IBasePutFigurePresentation putFigurePresentation,
						 IBaseGameFinishPresentation gameFinishPresentation, IBaseShowEnemyAbility showEnemyAbility,
			/*ITimerPresentation timerPresentation,*/ IAppErrorHandler appErrorHandler, IApiErrorHandler apiErrorHandler) {
		this.putFigurePresentation = putFigurePresentation;
		this.createGamePresentation = createGamePresentation;
		this.showEnemyAbility = showEnemyAbility;
//		this.timerPresentation = timerPresentation;
		this.gameFinishPresentation = gameFinishPresentation;
		this.appErrorHandler = appErrorHandler;
		this.apiErrorHandler = apiErrorHandler;
		gameContextRepository.getGCSById(gameMetaInfo.getGameId(), gameContext -> {
			if (stateHolder.getGameState() == null) {
				if (GCGameWrapperKt.isItMyTurn(gameContext, gameMetaInfo.getGcProfileId())) {
					changeState(WaitingForPlayerTurn.class);
				} else {
					changeState(WaitingForEnemyTurn.class);
				}
			}
		}, exc1 -> apiErrorHandler.handleApiError(new ApiError(exc1)));
	}

	@Override
	public void changeState(Type state) {
		try {
			if (state == WaitingForPlayerTurn.class) {
				stateHolder.setGameState(new WaitingForPlayerTurn(gameMetaInfo, gameContextRepository));
			} else if (state == WaitingForEnemyTurn.class) {
				stateHolder.setGameState(new WaitingForEnemyTurn(globalConfig, gameMetaInfo, gameContextRepository, profileRepository));
			}
			lateInitGameState();
		} catch (Exception e) {
			appErrorHandler.handleAppError(new AppError(e));
		}
	}


	private void lateInitGameState() {
		stateHolder.getGameState().lateInit(this::changeState,
				putFigurePresentation,
				createGamePresentation,
				showEnemyAbility,
//				timerPresentation,
				gameFinishPresentation,
				apiErrorHandler,
				appErrorHandler);
	}

	@Override
	public void putFigure(Figure figure) {
		stateHolder.getGameState().putFigure(figure);
	}

	@Override
	public Figure getCurrentFigure() {
		return stateHolder.getGameState().getCurrentFigure();
	}

	@Override
	public TurnResult forceFieldCheck() {
		return stateHolder.getGameState().forceFieldCheck();
	}

	@Override
	public void terminate(IAppErrorHandler appErrorHandler) {
		stateHolder.getGameState().terminate();
	}

}
