package breakingumbrella.connectit.domain.tutorial.steps;

import java.util.HashMap;

import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.entity.gameobjects.Figure;

public abstract class BaseTutorialStep {

	protected Figure getPlayerFigure() {
		Figure figure = new Figure();
		figure.setFigure(FigureTypes.cross);
		figure.setPosition(4, 4);
		return figure;
	}

	protected HashMap<Integer, Integer> getPlayerScore(int playerScore) {
		HashMap<Integer, Integer> score = new HashMap<>();
		score.put(1, playerScore);
		return score;
	}

}
