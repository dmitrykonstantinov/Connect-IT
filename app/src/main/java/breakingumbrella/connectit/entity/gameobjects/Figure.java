package breakingumbrella.connectit.entity.gameobjects;

public class Figure {

	private int internalFigureRepresentation;
	private int positionX;
	private int positionY;
	private int abilityType;

	public Figure() {
		internalFigureRepresentation = 0;
	}

	public Figure(Integer figureType) {
		setFigure(figureType);
	}

	public Figure(Figure figure) {
		internalFigureRepresentation = figure.internalFigureRepresentation;
		positionX = figure.getPositionX();
		positionY = figure.getPositionY();
		abilityType = figure.getAbilityType();
	}

	public Figure copy() {
		return new Figure(this);
	}

	public boolean isEmpty() {
		return internalFigureRepresentation == 0;
	}

	public void setPosition(int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void crossFigure() {
		internalFigureRepresentation = -internalFigureRepresentation;
	}

	public void setFigure(Integer figure) {
		internalFigureRepresentation = figure;
	}

	public boolean isFigureCrossed() {
		return internalFigureRepresentation < 0;
	}

	public int getFigureType() {
		return internalFigureRepresentation;
	}

	public Integer getAbilityType() {
		return abilityType;
	}

	public void setAbilityType(int abilityType) {
		this.abilityType = abilityType;
	}
}
