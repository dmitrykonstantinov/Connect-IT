package breakingumbrella.connectit.entity.gamelogic.boundaries;


import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public interface IAi {

	void startAi(GameField gameField, Figure aiFigure, Figure playerFigure, int xSize, int ySize, IAiUseCase iAiUseCase);

}
