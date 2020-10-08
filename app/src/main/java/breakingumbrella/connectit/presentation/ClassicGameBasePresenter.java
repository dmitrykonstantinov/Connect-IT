package breakingumbrella.connectit.presentation;

import com.arellomobile.mvp.MvpPresenter;

import breakingumbrella.connectit.domain.classicgame.presentation.IBaseCreateGamePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseGameFinishPresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBasePutFigurePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseShowEnemyAbility;
import breakingumbrella.connectit.domain.classicgame.usecase.ICreateGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IFinishGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IPutFigureUseCase;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.entities.GameResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.MatchResult;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.AppError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public abstract class ClassicGameBasePresenter<T extends IClassicGamePresentation> extends MvpPresenter<T> implements
		IClassicGameBasePresenter, IBasePutFigurePresentation, IBaseGameFinishPresentation, IBaseShowEnemyAbility,
		IBaseCreateGamePresentation/*, ITimerPresentation*/, IAppErrorHandler, IApiErrorHandler {

	protected ICreateGameUseCase createGameUseCase;
	protected IPutFigureUseCase putFigureUseCase;
	protected IFinishGameUseCase finishGameUseCase;

	public ClassicGameBasePresenter(ICreateGameUseCase createGameUseCase, IPutFigureUseCase putFigureUseCase,
									IFinishGameUseCase finishGameUseCase) {
		this.createGameUseCase = createGameUseCase;
		this.putFigureUseCase = putFigureUseCase;
		this.finishGameUseCase = finishGameUseCase;
	}

	@Override
	public void createGame() {
		createGameUseCase.createGame(this::onGameCreated, this::handleAppError, this::handleApiError);
	}

	@Override
	public void onGameCreated(GameContext gameContext) {
		getViewState().setGameInfoVisual(gameContext);
		putFigureUseCase.lateInit(this::onGameCreated, this::onGameFieldChange, this::onGameFinish, this::showEnemyAbility,
				/*this::onTick,*/ this::handleAppError, this::handleApiError);
	}

	@Override
	public void surrender() {
		finishGameUseCase.surrender(this::onGameFinish, this::handleAppError, this::handleApiError);
	}

	@Override
	public void onGameFieldChange(TurnResult result) {
		if (result.isFieldOnly) {
			getViewState().drawFieldChange(result);
		} else {
			getViewState().drawTurn(result);
		}
	}

	@Override
	public void onGameFinish(GameResult gameResult) {
		if (gameResult.getMatchResult() == MatchResult.Lose) {
			getViewState().drawLose();
		} else if (gameResult.getMatchResult() == MatchResult.Win) {
			getViewState().drawWin();
		} else if (gameResult.getMatchResult() == MatchResult.Tie) {
			getViewState().drawTie();
		}
	}

//	@Override
//	public void onTick(float progress, float secondsRemain) {
//		getViewState().updateTimerState(progress, (int) secondsRemain);
//	}

	@Override
	public void handleAppError(AppError appError) {
		getViewState().handleAppError(appError);
	}

	@Override
	public void handleApiError(ApiError apiError) {
		getViewState().handleApiError(apiError);
	}

	@Override
	public Figure getCurrentFigure() {
		return putFigureUseCase.getCurrentFigure();
	}

	@Override
	public void finish() {
		createGameUseCase.terminate(this::handleAppError);
		putFigureUseCase.terminate(this::handleAppError);
		finishGameUseCase.terminate(this::handleAppError);
	}

	@Override
	public void putFigure(Figure figure) {
		putFigureUseCase.putFigure(figure);
	}

}
