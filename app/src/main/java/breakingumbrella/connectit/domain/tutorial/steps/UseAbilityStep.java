package breakingumbrella.connectit.domain.tutorial.steps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.tutorial.ITutorialStep;
import breakingumbrella.connectit.domain.tutorial.TutorialConfig;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IPutFigure;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowAbility;
import breakingumbrella.connectit.domain.tutorial.presentation.actions.IShowText;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public class UseAbilityStep extends BaseTutorialStep implements ITutorialStep {

    private List<Class> tutorialActions;
    private TutorialConfig tutorialConfig;
    private int txtNumber;

    @Inject
    public UseAbilityStep(TutorialConfig tutorialConfig) {
        this.tutorialConfig = tutorialConfig;
        tutorialActions = new ArrayList<>();
        tutorialActions.add(IShowText.class);
        tutorialActions.add(IShowAbility.class);
        tutorialActions.add(IPutFigure.class);
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
        return tutorialConfig.getTutorialText(this.getClass(), txtNumber++);
    }

    @Override
    public HashMap<Integer, Integer> getScore() {
        return super.getPlayerScore(1);
    }

    @Override
    public Figure getFigure() {
        Figure figure = super.getPlayerFigure();
        figure.setPosition(4, 3);
        return figure;
    }

    @Override
    public GameField getField() {
        GameField gameField = new GameField(6, 6);
        Figure figure1 = new Figure(FigureTypes.circle);
        figure1.setPosition(4, 4);
        Figure figure2 = new Figure(FigureTypes.circle);
        figure2.setPosition(4, 5);
        gameField.addFigure(figure1);
        gameField.addFigure(figure2);
        return gameField;
    }

    @Override
    public int getTutorialNumber() {
        return 5;
    }
}
