package breakingumbrella.connectit.domain.tutorial.usecase;

import breakingumbrella.connectit.domain.tutorial.TutorialStepHolder;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IPutFigure;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowScore;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;

public class CreateTutorialUseCase implements ICreateTutorialUseCase {

	private TutorialStepHolder tutorialStepHolder;

	public CreateTutorialUseCase(TutorialStepHolder tutorialStepHolder) {
		this.tutorialStepHolder = tutorialStepHolder;
	}

	public void CreateTutorialSteps(IShowText showText, IPutFigure putFigure, IShowScore showScore) {

	}

}
