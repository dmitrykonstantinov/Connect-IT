package breakingumbrella.connectit.presentation.splitscreenmode;

import breakingumbrella.connectit.entity.gameobjects.Figure;

public interface ISplitScreenGamePresenter {
	void putFigure(Figure figure);
	void createGame();
	Figure getCurrentFigure();
}
