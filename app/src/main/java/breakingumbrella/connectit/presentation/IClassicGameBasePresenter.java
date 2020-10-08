package breakingumbrella.connectit.presentation;

import breakingumbrella.connectit.entity.gameobjects.Figure;

public interface IClassicGameBasePresenter {
    void createGame();
    void finish();
    void surrender();
    void putFigure(Figure figure);
    Figure getCurrentFigure();
}
