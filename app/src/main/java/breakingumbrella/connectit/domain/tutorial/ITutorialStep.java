package breakingumbrella.connectit.domain.tutorial;

import java.util.HashMap;
import java.util.List;

import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public interface ITutorialStep {

	List<Class> getTutorialActions();

	String getStepName();

	String getText();

	HashMap<Integer, Integer> getScore();

	Figure getFigure();

	GameField getField();

	int getTutorialNumber();
}
