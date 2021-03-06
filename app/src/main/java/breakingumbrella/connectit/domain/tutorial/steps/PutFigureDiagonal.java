package breakingumbrella.connectit.domain.tutorial.steps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.domain.tutorial.ITutorialStep;
import breakingumbrella.connectit.domain.tutorial.TutorialConfig;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IPutFigure;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowScore;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public class PutFigureDiagonal extends BaseTutorialStep implements ITutorialStep {

	private List<Class> tutorialActions;
	private TutorialConfig tutorialConfig;

	@Inject
	public PutFigureDiagonal(TutorialConfig tutorialConfig) {
		this.tutorialConfig = tutorialConfig;
		tutorialActions = new ArrayList<>();
		tutorialActions.add(IShowText.class);
		tutorialActions.add(IPutFigure.class);
		tutorialActions.add(IShowScore.class);
	}

	@Override
	public List<Class> getTutorialActions() {
		return tutorialActions;
	}

	@Override
	public String getStepName() {
		return tutorialConfig.getTutorialName(this.getClass());
	}

	@Override
	public String getText() {
		return tutorialConfig.getTutorialText(this.getClass());
	}

	@Override
	public HashMap<Integer, Integer> getScore() {
		return super.getPlayerScore(1);
	}

	@Override
	public Figure getFigure() {
		Figure figure = super.getPlayerFigure();
		figure.setPosition(1, 4);
		return figure;
	}

	@Override
	public GameField getField() {
		//TODO Called twice
		GameField gameField = new GameField(6, 6);
		Figure figure1 = new Figure(FigureTypes.circle);
		figure1.setPosition(4, 1);
		Figure figure2 = new Figure(FigureTypes.cross);
		figure2.setPosition(3, 2);
		Figure figure3 = new Figure(FigureTypes.cross);
		figure3.setPosition(2, 3);
		gameField.addFigure(figure1);
		gameField.addFigure(figure2);
		gameField.addFigure(figure3);

		return gameField;
	}

	@Override
	public int getTutorialNumber() {
		return 5;
	}

}
