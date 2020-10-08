package breakingumbrella.connectit.presentation;

import breakingumbrella.connectit.IViewBase;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public interface IClassicGamePresentation extends IViewBase, IAppErrorHandler, IApiErrorHandler {
    void drawTurn(TurnResult turnResult);
    void drawFieldChange(TurnResult turnResult);
    void finishGame();
//    void updateTimerState(float progress, int secondsRemain);
    void drawLose();
    void drawWin();
    void drawTie();
    void setGameInfoVisual(GameContext gameContext);
}
