package breakingumbrella.connectit.domain.tutorial.presentation.actions;

import breakingumbrella.connectit.domain.tutorial.ITutorialAction;
import breakingumbrella.connectit.entity.gameobjects.Figure;

public interface IPutFigure extends ITutorialAction {
	void putFigure(Figure figure);
}
