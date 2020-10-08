package breakingumbrella.connectit.entity.gameobjects;

public interface IGameField {
	void addFigure(Figure figure);
	void removeFigure(Figure figure);
	void crossFigure(Figure figure);
	Figure getFigureAtPosition(int positionX, int positionY);
	boolean isFigureExistAtPosition(int positionX, int positionY);
	FieldCheckResult check();
}
