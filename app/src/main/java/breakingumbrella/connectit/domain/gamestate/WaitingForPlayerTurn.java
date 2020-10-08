package breakingumbrella.connectit.domain.gamestate;

import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseCreateGamePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseGameFinishPresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseShowEnemyAbility;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.entity.context.GameMetaInfo;
import breakingumbrella.connectit.entity.context.GCGameWrapperKt;
import breakingumbrella.connectit.entity.context.GCProfileWrapperKt;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.domain.classicgame.presentation.IBasePutFigurePresentation;
import breakingumbrella.connectit.entity.entities.GameResult;
import breakingumbrella.connectit.entity.gamelogic.checking.Checking;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.FieldCheckResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.AppError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public class WaitingForPlayerTurn implements IGameState/*, OnTimerTickCallback*/ {

	private IGameContextRepository gameRepository;
	private GameContext gameContext;
//	private GameTimer timer;
	private IBasePutFigurePresentation putFigurePresentation;
	private IBaseGameFinishPresentation gameFinishPresentation;
//	private IApiErrorHandler apiErrorHandler;
	private IAppErrorHandler appErrorHandler;
	private IStateChanger stateChanger;
//	private ITimerPresentation timerPresentation;
	private GameMetaInfo gameMetaInfo;
	private boolean isInit = false;
//	private boolean isAbilityActive = false;
//	private Integer abilityType;

	public WaitingForPlayerTurn(GameMetaInfo gameMetaInfo, IGameContextRepository gameRepository) {
		this.gameRepository = gameRepository;
		this.gameMetaInfo = gameMetaInfo;
	}

	@Override
	public void lateInit(IStateChanger stateChanger, IBasePutFigurePresentation putFigurePresentation,
						 IBaseCreateGamePresentation createGamePresentation, IBaseShowEnemyAbility showEnemyAbility,
						 /*ITimerPresentation timerPresentation,*/ IBaseGameFinishPresentation gameFinishPresentation,
						 IApiErrorHandler apiErrorHandler, IAppErrorHandler appErrorHandler) {
		this.stateChanger = stateChanger;
		this.putFigurePresentation = putFigurePresentation;
//		this.timerPresentation = timerPresentation;
		this.gameFinishPresentation = gameFinishPresentation;
//		this.apiErrorHandler = apiErrorHandler;
		this.appErrorHandler = appErrorHandler;
//		timer = new GameTimer();
		gameRepository.getGCSById(gameMetaInfo.getGameId(), gameContext -> {
			WaitingForPlayerTurn.this.gameContext = gameContext;
//			timer.initTimer(1, 50, 20, true, this);
//			timer.startTimer();
			isInit = true;
		}, exc1 -> apiErrorHandler.handleApiError(new ApiError(exc1)));
	}

	@Override
	public void putFigure(Figure figure) {
		if (isInit == false) {
			throw new Error("State is not fully initialized");
		}
		if (handleAbility(figure)) {
			return;
		}
		if (handleFigureIsExist(figure)) {
			return;
		}
//		timer.stopTimer();
		handleAddFigure(figure);
		handleUpdateGameContext();
//		timerPresentation.onTick(100, 0);
	}

	@Override
	public TurnResult forceFieldCheck() {
		FieldCheckResult fieldCheckResult = new Checking().check(gameContext.getGameField());
		return GCGameWrapperKt.createTurnResult(gameContext, gameMetaInfo.getGcProfileId(), fieldCheckResult);
	}

	private boolean handleAbility(Figure figure) {
		//TODO New ability amount handling logic is in View now. Its wrong and temporary. I'll fix it later.
//		if(figure.getAbilityType() != AbilityType.none) {
//			GCProfile myProfile = GCProfileWrapperKt.getMe(gameContext, gameMetaInfo.getGcProfileId());
//			if(myProfile.getMyAbilities().indexOf(figure.getAbilityType()) == -1) {
//				return true; //Ability already used
//			}
//			else {
//				myProfile.getMyAbilities().remove(figure.getAbilityType());
//			}
//		}
		if (figure.getAbilityType() == AbilityType.destroyFigure) {
			if (gameContext.getGameField().isFigureExistAtPosition(figure.getPositionX(), figure.getPositionY())) {
				gameContext.getGameField().removeFigure(figure);
			}
		} else if (figure.getAbilityType() == AbilityType.secondChance) {
			FieldCheckResult fieldCheckResult = GCGameWrapperKt.addFigure(gameContext, figure, gameMetaInfo.getGcProfileId());
			putFigurePresentation.onGameFieldChange(GCGameWrapperKt.createTurnResult(gameContext, gameMetaInfo.getGcProfileId(), fieldCheckResult));
		}
		return false;
	}

	private boolean handleFigureIsExist(Figure figure) {
		if (gameContext.getGameField().isFigureExistAtPosition(figure.getPositionX(), figure.getPositionY())) {
			Figure figureAtPosition = gameContext.getGameField().getFigureAtPosition(figure.getPositionX(), figure.getPositionY());
			if (figureAtPosition.getAbilityType() == AbilityType.invisibleFigure) {
				figureAtPosition.setAbilityType(AbilityType.none);
			}
			putFigurePresentation.onGameFieldChange(GCGameWrapperKt.createFieldAndScoreTurnResult(gameContext, gameMetaInfo.getGcProfileId()));
			return true;
		}
		return false;
	}

	private void handleAddFigure(Figure figure) {
		FieldCheckResult fieldCheckResult = GCGameWrapperKt.addFigure(gameContext, figure, gameMetaInfo.getGcProfileId());
		GCGameWrapperKt.switchPlayerTurn(gameContext, gameMetaInfo.getGcProfileId());
		putFigurePresentation.onGameFieldChange(GCGameWrapperKt.createTurnResult(gameContext, gameMetaInfo.getGcProfileId(), fieldCheckResult));
	}

	private void handleUpdateGameContext() {
		gameRepository.updateGCS(gameContext, aVoid -> {
			if (GCGameWrapperKt.isGameEnd(gameContext)) {
				GameResult gameResult = new GameResult();
				gameResult.setMatchResult(GCGameWrapperKt.getMatchResult(gameContext, gameMetaInfo.getGcProfileId()));
				gameResult.setMatchRatingResult(GCProfileWrapperKt.getNewRating(gameContext, gameMetaInfo.getGcProfileId()));
				gameFinishPresentation.onGameFinish(gameResult);
			} else {
				stateChanger.changeState(WaitingForEnemyTurn.class);
			}
		}, exc1 -> appErrorHandler.handleAppError(new AppError(exc1)));
	}

	@Override
	public void terminate() {
//		timer.stopTimer();
	}

	@Override
	public Figure getCurrentFigure() {
		if (gameContext == null) {
			return new Figure();
		}
		return GCProfileWrapperKt.getMe(gameContext, gameMetaInfo.getGcProfileId()).getFigure();
	}

//	@Override
//	public void OnTick(long progress, long milSecondsRemain) {
//		//On slow devices, new activity view loading slowly than domain models
//		//it's lead to NPE when domain try to touch view throw callbacks that was not initialised
//		//So lets check for null for the first
//		if (timerPresentation == null) {
//			return;
//		}
//		timerPresentation.onTick(progress, milSecondsRemain);
//	}

//	@Override
//	public void OnTimerFinish() {
//		timerPresentation.onTick(100, 0);
//		Figure figure = GCGameWrapperKt.getRandomPosition(gameContext);
//		figure.setFigure(getCurrentFigure().getFigureType());
//		putFigure(figure);
//	}

}
