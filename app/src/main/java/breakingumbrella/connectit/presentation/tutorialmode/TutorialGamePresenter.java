package breakingumbrella.connectit.presentation.tutorialmode;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.domain.tutorial.ITutorialStep;
import breakingumbrella.connectit.domain.tutorial.presentation.IOnFieldCheckResult;
import breakingumbrella.connectit.domain.tutorial.presentation.IOnStepCompleted;
import breakingumbrella.connectit.domain.tutorial.presentation.IShowStep;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IPutFigure;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowAbility;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowScore;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;
import breakingumbrella.connectit.domain.tutorial.usecase.ICheckTutorialFieldUseCase;
import breakingumbrella.connectit.domain.tutorial.usecase.ITutorialUseCase;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

@InjectViewState
public class TutorialGamePresenter extends MvpPresenter<ITutorialGameView> implements ITutorialGamePresenter,
        IOnStepCompleted, IOnFieldCheckResult, IShowStep, IShowText, IShowScore, IPutFigure, IShowAbility {

    private ITutorialUseCase ITutorialUseCase;
    private ICheckTutorialFieldUseCase checkTutorialFieldUseCase;

    @Inject
    TutorialGamePresenter(ITutorialUseCase ITutorialUseCase, ICheckTutorialFieldUseCase checkTutorialFieldUseCase) {
        this.ITutorialUseCase = ITutorialUseCase;
        this.checkTutorialFieldUseCase = checkTutorialFieldUseCase;
    }

    @Override
    public void beginTutorial() {
        ITutorialUseCase.startTutorial(this::onStepCompleted, this::showStep, this::showText, this::showScore, this::putFigure, this::showAbility);
    }

    @Override
    public void nextAction() {
        ITutorialUseCase.nextAction();
    }

    @Override
    public void checkFigure(Figure figure, GameField gameField) {
//		gameField.addFigure(figure);
        checkTutorialFieldUseCase.checkField(figure, gameField, this::onFieldCheckResult);
    }

    @Override
    public void onStepCompleted(ITutorialStep tutorialStep) {
        getViewState().onStepCompleted(tutorialStep);
    }

    @Override
    public void showStep(ITutorialStep tutorialStep) {
        getViewState().showNewStep(tutorialStep);
    }

    @Override
    public void showText(String text) {
        getViewState().showText(text);
    }

    @Override
    public void showScore(int score) {
        getViewState().showScore(score);
    }

    @Override
    public void putFigure(Figure figure) {
        getViewState().putFigure(figure);
    }

    @Override
    public void onFieldCheckResult(TurnResult turnResult) {
        getViewState().drawField(turnResult);
    }

    @Override

    public void showAbility() {
        getViewState().showAbility();
    }
}
