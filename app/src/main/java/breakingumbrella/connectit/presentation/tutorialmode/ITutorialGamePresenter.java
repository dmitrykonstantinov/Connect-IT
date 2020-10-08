package breakingumbrella.connectit.presentation.tutorialmode;

import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public interface ITutorialGamePresenter {

	void beginTutorial();

	void nextAction();

	void checkFigure(Figure figure, GameField gameField);
}
