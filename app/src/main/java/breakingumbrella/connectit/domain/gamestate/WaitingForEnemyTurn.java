package breakingumbrella.connectit.domain.gamestate;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.domain.classicgame.middleware.FinishGame;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseCreateGamePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseGameFinishPresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseShowEnemyAbility;
import breakingumbrella.connectit.entity.context.GameMetaInfo;
import breakingumbrella.connectit.entity.context.GCGameWrapperKt;
import breakingumbrella.connectit.entity.context.GCProfileWrapperKt;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.domain.classicgame.presentation.IBasePutFigurePresentation;
import breakingumbrella.connectit.entity.entities.GameResult;
import breakingumbrella.connectit.entity.gamelogic.checking.Checking;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.FieldCheckResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public class WaitingForEnemyTurn implements IGameState {

	private GameContext gameContext;
	private IGameContextRepository gameRepository;
	private IBasePutFigurePresentation putFigurePresentation;
	private IBaseGameFinishPresentation gameFinishPresentation;
	private IBaseShowEnemyAbility showEnemyAbility;
	private IStateChanger stateChanger;
	private IApiErrorHandler apiErrorHandler;
	private IAppErrorHandler appErrorHandler;
	private boolean isStateTerminated = false;
	private GameMetaInfo gameMetaInfo;
	private String myId;
	private FinishGame finishGame;
	private GlobalConfig globalConfig;
	private IProfileRepository profileRepository;

	public WaitingForEnemyTurn(GlobalConfig globalConfig, GameMetaInfo gameMetaInfo,
							   IGameContextRepository gameRepository, IProfileRepository profileRepository) {
		this.gameRepository = gameRepository;
		this.gameMetaInfo = gameMetaInfo;
		this.myId = gameMetaInfo.getGcProfileId();
		this.globalConfig = globalConfig;
		this.profileRepository = profileRepository;
	}

	@Override
	public void lateInit(IStateChanger stateChanger, IBasePutFigurePresentation putFigurePresentation,
						 IBaseCreateGamePresentation createGamePresentation, IBaseShowEnemyAbility showEnemyAbility,
						 /*ITimerPresentation timerPresentation,*/ IBaseGameFinishPresentation gameFinishPresentation,
						 IApiErrorHandler apiErrorHandler, IAppErrorHandler appErrorHandler) {
		this.putFigurePresentation = putFigurePresentation;
		this.gameFinishPresentation = gameFinishPresentation;
		this.showEnemyAbility = showEnemyAbility;
		this.apiErrorHandler = apiErrorHandler;
		this.appErrorHandler = appErrorHandler;
		this.stateChanger = stateChanger;
		getEnemyTurn();
	}

	private void getEnemyTurn() {
		gameRepository.getGCSById(gameMetaInfo.getGameId(),
				gameContext -> putEnemyFigure(gameContext.getTurnHistory().get(gameContext.getTurnHistory().size() - 1), gameContext),
				exc -> apiErrorHandler.handleApiError(new ApiError(exc)));
	}

	private void finishGame(GameContext gameContext) {
		GameResult gameResult = new GameResult();
		gameResult.setMatchResult(GCGameWrapperKt.getMatchResult(gameContext, gameMetaInfo.getGcProfileId()));
		gameResult.setMatchRatingResult(GCProfileWrapperKt.getNewRating(gameContext, gameMetaInfo.getGcProfileId()));
		gameFinishPresentation.onGameFinish(gameResult);
	}

	private void putEnemyFigure(Figure figure, GameContext gameContext) {
		if (figure.getAbilityType() != AbilityType.none) {
			showEnemyAbility.showEnemyAbility(figure.getAbilityType());
		}
		FieldCheckResult fieldCheckResult = GCGameWrapperKt.addFigure(gameContext, figure, gameMetaInfo.getGcProfileId());
		TurnResult turnResult = GCGameWrapperKt.createTurnResult(gameContext, gameMetaInfo.getGcProfileId(), fieldCheckResult);
		putFigurePresentation.onGameFieldChange(turnResult);
		if (GCGameWrapperKt.isGameEnd(gameContext)) {
			finishGame(gameContext);
		} else if (figure.getAbilityType() == AbilityType.secondChance) {
			getEnemyTurn();
		} else {
			stateChanger.changeState(WaitingForPlayerTurn.class);
		}
	}

	@Override
	public void putFigure(Figure figure) {
		//Do nothing cause it's not player turn
	}

	@Override
	public TurnResult forceFieldCheck() {
		FieldCheckResult fieldCheckResult = new Checking().check(gameContext.getGameField());
		return GCGameWrapperKt.createTurnResult(gameContext, gameMetaInfo.getGcProfileId(), fieldCheckResult);
	}

	@Override
	public void terminate() {
		isStateTerminated = true;
	}

	@Override
	public Figure getCurrentFigure() {
		if (gameContext == null) {
			return new Figure();
		}
		return GCProfileWrapperKt.getMe(gameContext, gameMetaInfo.getGcProfileId()).getFigure();
	}
}
