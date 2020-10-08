package breakingumbrella.connectit.domain.classicgame.usecase;

import breakingumbrella.connectit.domain.IUseCase;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseCreateGamePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseGameFinishPresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBasePutFigurePresentation;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseShowEnemyAbility;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public interface IPutFigureUseCase extends IUseCase {

	void putFigure(Figure figure);

	Figure getCurrentFigure();

	TurnResult forceFieldCheck();

	void lateInit(IBaseCreateGamePresentation createGamePresentation, IBasePutFigurePresentation putFigurePresentation,
				  IBaseGameFinishPresentation gameFinishPresentation, IBaseShowEnemyAbility showEnemyAbility,
				  /*ITimerPresentation timerPresentation,*/ IAppErrorHandler appErrorHandler, IApiErrorHandler apiErrorHandler);

}
