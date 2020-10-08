package breakingumbrella.connectit.domain.tutorial;

import java.util.LinkedList;
import java.util.Queue;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.tutorial.steps.InitialStep;
import breakingumbrella.connectit.domain.tutorial.steps.PutFigureDiagonal;
import breakingumbrella.connectit.domain.tutorial.steps.PutFigureLongLine;
import breakingumbrella.connectit.domain.tutorial.steps.PutFigureStep;
import breakingumbrella.connectit.domain.tutorial.steps.PutFigureWithEnemyStep;
import breakingumbrella.connectit.domain.tutorial.steps.UseAbilityStep;

public class TutorialStepHolder {

    private Queue<ITutorialStep> tutorialSteps;

    @Inject
    TutorialStepHolder(InitialStep initialStep, PutFigureStep putFigureStep,
                       PutFigureWithEnemyStep putFigureWithEnemyStep, PutFigureLongLine putFigureLongLine,
                       PutFigureDiagonal putFigureDiagonal, UseAbilityStep useAbilityStep) {
        tutorialSteps = new LinkedList<>();
        tutorialSteps.add(initialStep);
        tutorialSteps.add(putFigureStep);
        tutorialSteps.add(putFigureWithEnemyStep);
        tutorialSteps.add(putFigureLongLine);
        tutorialSteps.add(putFigureDiagonal);
        tutorialSteps.add(useAbilityStep);
    }

    public ITutorialStep getTutorialStep() {
        return tutorialSteps.poll();
    }
}
