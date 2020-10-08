package breakingumbrella.connectit.presentation.campaign;

import breakingumbrella.connectit.entity.gameobjects.Figure;

public interface ICampaignPresenter {

    void putFigure(Figure figure);
    void createGame();
    Figure getCurrentFigure();
    void finish();

}
