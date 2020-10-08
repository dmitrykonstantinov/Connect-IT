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

public class PutFigureLongLine extends BaseTutorialStep implements ITutorialStep {

    private List<Class> tutorialActions;
    private TutorialConfig tutorialConfig;
    private int txtNumber;

    @Inject
    public PutFigureLongLine(TutorialConfig tutorialConfig) {
        this.tutorialConfig = tutorialConfig;
        tutorialActions = new ArrayList<>();
        tutorialActions.add(IShowText.class);
        tutorialActions.add(IPutFigure.class);
        tutorialActions.add(IShowText.class);
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
        return tutorialConfig.getTutorialText(this.getClass(), txtNumber++);
    }

    @Override
    public HashMap<Integer, Integer> getScore() {
        return super.getPlayerScore(1);
    }

    @Override
    public Figure getFigure() {
        Figure figure = super.getPlayerFigure();
        figure.setPosition(4, 5);
        return figure;
    }

    @Override
    public GameField getField() {
        GameField gameField = new GameField(7, 7);
        Figure figure1 = new Figure(FigureTypes.circle);
        figure1.setPosition(4, 0);
        Figure figure3 = new Figure(FigureTypes.cross);
        figure3.setPosition(4, 1);
        Figure figure4 = new Figure(FigureTypes.circle);
        figure4.setPosition(4, 2);
        Figure figure5 = new Figure(FigureTypes.circle);
        figure5.setPosition(4, 3);
        Figure figure6 = new Figure(FigureTypes.cross);
        figure6.setPosition(4, 4);
        Figure figure7 = new Figure(FigureTypes.cross);
        figure7.setPosition(4, 6);
        gameField.addFigure(figure1);
        gameField.addFigure(figure3);
        gameField.addFigure(figure4);
        gameField.addFigure(figure5);
        gameField.addFigure(figure6);
        gameField.addFigure(figure7);

        return gameField;
    }

    @Override
    public int getTutorialNumber() {
        return 4;
    }

}
