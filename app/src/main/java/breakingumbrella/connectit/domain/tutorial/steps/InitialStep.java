package breakingumbrella.connectit.domain.tutorial.steps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.tutorial.ITutorialStep;
import breakingumbrella.connectit.domain.tutorial.TutorialConfig;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public class InitialStep extends BaseTutorialStep implements ITutorialStep {

	private List<Class> tutorialActions;
	private TutorialConfig tutorialConfig;

	@Inject
	public InitialStep(TutorialConfig tutorialConfig) {
		this.tutorialConfig = tutorialConfig;
		tutorialActions = new ArrayList<>();
		tutorialActions.add(IShowText.class);
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
		return getPlayerScore(0);
	}

	@Override
	public Figure getFigure() {
		return getPlayerFigure();
	}

	@Override
	public GameField getField() {
		return new GameField(6, 6);
	}

	@Override
	public int getTutorialNumber() {
		return 1;
	}

}
