package breakingumbrella.connectit.domain.tutorial.usecase;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.tutorial.ITutorialStep;
import breakingumbrella.connectit.domain.tutorial.TutorialStepHolder;
import breakingumbrella.connectit.domain.tutorial.presentation.IOnStepCompleted;
import breakingumbrella.connectit.domain.tutorial.presentation.IShowStep;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IPutFigure;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowAbility;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowScore;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;

public class TutorialUseCase implements ITutorialUseCase {

    private TutorialStepHolder stepHolder;
    private ITutorialStep tutorialStep;
    private int currentActionCnt;

    private IOnStepCompleted onStepCompleted;
    private IShowStep showStep;
    private IShowText showText;
    private IShowScore showScore;
    private IPutFigure putFigure;
    private IShowAbility showAbility;

    @Inject
    TutorialUseCase(TutorialStepHolder stepHolder) {
        this.stepHolder = stepHolder;
    }

    @Override
    public void startTutorial(IOnStepCompleted onStepCompleted, IShowStep showStep, IShowText showText,
                              IShowScore showScore, IPutFigure putFigure, IShowAbility showAbility) {
        this.onStepCompleted = onStepCompleted;
        this.showStep = showStep;
        this.showText = showText;
        this.showScore = showScore;
        this.putFigure = putFigure;
        this.showAbility = showAbility;
        startNewStep();
    }

    @Override
    public void nextAction() {
        currentActionCnt++;
        if (tutorialStep.getTutorialActions().size() > currentActionCnt - 1) {
            invokeAction(tutorialStep.getTutorialActions().get(currentActionCnt - 1), tutorialStep);
        } else {
            onStepCompleted.onStepCompleted(tutorialStep);
            startNewStep();
        }
    }

    private void startNewStep() {
        currentActionCnt = 0;
        tutorialStep = stepHolder.getTutorialStep();
        showStep.showStep(tutorialStep);
    }

    private void invokeAction(Class tutorialAction, ITutorialStep tutorialStep) {
        if (tutorialAction == IShowText.class) {
            showText.showText(tutorialStep.getText());
        } else if (tutorialAction == IShowScore.class) {
            showScore.showScore(tutorialStep.getScore().get(1));
        } else if (tutorialAction == IPutFigure.class) {
            putFigure.putFigure(tutorialStep.getFigure());
        } else if (tutorialAction == IShowAbility.class) {
        	showAbility.showAbility();
        }
    }

}
