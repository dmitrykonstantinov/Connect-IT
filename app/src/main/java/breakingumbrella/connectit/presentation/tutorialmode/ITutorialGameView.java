package breakingumbrella.connectit.presentation.tutorialmode;

import breakingumbrella.connectit.IViewBase;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.domain.tutorial.ITutorialStep;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IPutFigure;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowAbility;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowScore;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public interface ITutorialGameView extends IViewBase, IAppErrorHandler, IShowText, IShowScore, IPutFigure, IShowAbility {

	void onStepCompleted(ITutorialStep tutorialStep);
	void showNewStep(ITutorialStep tutorialStep);
	void drawField(TurnResult turnResult);

}
