package breakingumbrella.connectit.presentation.aigamemode;

import breakingumbrella.connectit.entity.gameobjects.Figure;

public interface IAiGamePresenter {
	void putFigure(Figure figure);
	void createGame();
	Figure getCurrentFigure();
	void finish();
}
