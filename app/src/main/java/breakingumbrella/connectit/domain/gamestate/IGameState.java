package breakingumbrella.connectit.domain.gamestate;

import breakingumbrella.connectit.domain.classicgame.presentation.IBaseCreateGamePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseGameFinishPresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBasePutFigurePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseShowEnemyAbility;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public interface IGameState {

	void lateInit(IStateChanger stateChanger, IBasePutFigurePresentation putFigurePresentation,
				  IBaseCreateGamePresentation createGamePresentation, IBaseShowEnemyAbility showEnemyAbility,
				  /*ITimerPresentation timerPresentation,*/ IBaseGameFinishPresentation gameFinishPresentation,
				  IApiErrorHandler apiErrorHandler, IAppErrorHandler appErrorHandler);

	void putFigure(Figure figure);

	TurnResult forceFieldCheck();

	Figure getCurrentFigure();

	void terminate();

}
