package breakingumbrella.connectit.domain.tutorial.usecase;

import breakingumbrella.connectit.domain.tutorial.presentation.IOnStepCompleted;
import breakingumbrella.connectit.domain.tutorial.presentation.IShowStep;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IPutFigure;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowAbility;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowScore;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;

public interface ITutorialUseCase {

	void startTutorial(IOnStepCompleted onStepCompleted, IShowStep showStep, IShowText showText,
					   IShowScore showScore, IPutFigure putFigure, IShowAbility showAbility);

	void nextAction();
}
