package breakingumbrella.connectit.entity.gameobjects;

import java.io.Serializable;

public class GameField implements IGameField, Serializable {

	private Figure[][] horizontalLines;
	private Figure[][] verticalLines;
	private Figure[][] leftDiagonalLines;
	private Figure[][] rightDiagonalLines;

	private Figure emptyFigure;
	private int sizeX;
	private int sizeY;

	public GameField(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		initAll(sizeX, sizeY);
		initEmptyField();
	}

	public GameField(GameField gameField) {
		this.sizeX = gameField.sizeX;
		this.sizeY = gameField.sizeY;
		initAll(sizeX, sizeY);
		initGameFieldFrom(gameField);
	}

	private void initAll(int sizeX, int sizeY) {
		//TODO Initialisation is wrong
		horizontalLines = new Figure[sizeX][sizeY];
		verticalLines = new Figure[sizeY][sizeX];
		leftDiagonalLines = new Figure[sizeX + sizeY][sizeY];
		rightDiagonalLines = new Figure[sizeX + sizeY][sizeY];
		emptyFigure = new Figure();
	}

	private void initGameFieldFrom(GameField gameField) {
		RawGameField.initRowFrom(horizontalLines, gameField.horizontalLines, sizeX, sizeY);
		RawGameField.initRowFrom(verticalLines, gameField.verticalLines, sizeX, sizeY);
		RawGameField.initRowFrom(leftDiagonalLines, gameField.leftDiagonalLines, sizeX, sizeY);
		RawGameField.initRowFrom(rightDiagonalLines, gameField.rightDiagonalLines, sizeX, sizeY);
	}

	private void initEmptyField() {
		RawGameField.initWithEmptyFigure(horizontalLines, sizeX, sizeY, emptyFigure);
		RawGameField.initWithEmptyFigure(verticalLines, sizeX, sizeY, emptyFigure);
		RawGameField.initWithEmptyFigure(leftDiagonalLines, sizeX, sizeY, emptyFigure);
		RawGameField.initWithEmptyFigure(rightDiagonalLines, sizeX, sizeY, emptyFigure);
	}


	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getTotoalSize() {
		return sizeX * sizeY;
	}

	//========================= SERIALISATION && CHECKING =========================//
	/*
	 * Don't use it explicitly. Use it ONLY throw RawGameField helper
	 */

	public Figure[][] getHorizontalLines() {
		return horizontalLines;
	}

	public void setHorizontalLines(Figure[][] horizontalLines) {
		this.horizontalLines = horizontalLines;
	}

	public Figure[][] getVerticalLines() {
		return verticalLines;
	}

	public void setVerticalLines(Figure[][] verticalLines) {
		this.verticalLines = verticalLines;
	}

	public Figure[][] getLeftDiagonalLines() {
		return leftDiagonalLines;
	}

	public void setLeftDiagonalLines(Figure[][] leftDiagonalLines) {
		this.leftDiagonalLines = leftDiagonalLines;
	}

	public Figure[][] getRightDiagonalLines() {
		return rightDiagonalLines;
	}

	public void setRightDiagonalLines(Figure[][] rightDiagonalLines) {
		this.rightDiagonalLines = rightDiagonalLines;
	}

	//========================= SERIALISATION && CHECKING =========================//


	private boolean isPositionFitField(int x, int y) {
		return x >= 0 && y >= 0 && x < sizeX && y < sizeY;
	}

	@Override
	public void addFigure(Figure figure) {
		RawGameField.addToHorizontal(figure.copy(), horizontalLines);
		RawGameField.addToVertical(figure.copy(), verticalLines);
		RawGameField.addToLeftDiagonal(figure.copy(), leftDiagonalLines, sizeX);
		RawGameField.addToRightDiagonal(figure.copy(), rightDiagonalLines, sizeX);
	}

	@Override
	public void removeFigure(Figure figure) {
		RawGameField.removeHorizontal(figure, horizontalLines);
		RawGameField.removeVertical(figure, verticalLines);
		RawGameField.removeLeftDiagonal(figure, leftDiagonalLines, sizeX);
		RawGameField.removeRightDiagonal(figure, rightDiagonalLines, sizeX);
	}

	@Override
	public void crossFigure(Figure figure) {
		Figure figureLocal;
		figureLocal = horizontalLines[figure.getPositionX()][figure.getPositionY()];
		if (figureLocal != null) {
			figureLocal.crossFigure();
		}

		int[] invertedPositions = RawGameField.invertToVerticalPosition(figure.getPositionX(), figure.getPositionY());
		figureLocal = verticalLines[invertedPositions[0]][invertedPositions[1]];
		if (figureLocal != null) {
			figureLocal.crossFigure();
		}

		invertedPositions = RawGameField.invertToLeftDiagonalPositions(figure.getPositionX(), figure.getPositionY(), sizeX);
		figureLocal = leftDiagonalLines[invertedPositions[0]][invertedPositions[1]];
		if (figureLocal != null) {
			figureLocal.crossFigure();
		}

		invertedPositions = RawGameField.invertToRightDiagonalPositions(figure.getPositionX(), figure.getPositionY(), sizeX);
		figureLocal = rightDiagonalLines[invertedPositions[0]][invertedPositions[1]];
		if (figureLocal != null) {
			figureLocal.crossFigure();
		}
	}

	/**
	 * BE AWARE! that if there are now figure at position it will be always return the same empty figure
	 * BE AWARE! it is return only from horizontal line so does not change returning figure, it does not affect gameField
	 *
	 * @return
	 */

	@Override
	public Figure getFigureAtPosition(int positionX, int positionY) {
		if (isFigureExistAtPosition(positionX, positionY)) {
			Figure figure = horizontalLines[positionX][positionY];
			if (figure != null) {
				return figure;
			}
		}
		return emptyFigure;
	}


	@Override
	public boolean isFigureExistAtPosition(int positionX, int positionY) {
		if (isPositionFitField(positionX, positionY) &&
				horizontalLines[positionX][positionY] != null &&
				horizontalLines[positionX][positionY].isEmpty() == false) {
			return true;
		}
		return false;
	}

	@Override
	public FieldCheckResult check() {
		return new FieldCheckResult();
	}


	//================================ RAW =====================================//


	public enum LineType {
		horizontal,
		vertical,
		leftDiagonal,
		rightDiagonal
	}

	public static class RawGameField {

		public static Figure getFigureFromRow(GameField gameField, LineType lineType, int i, int j) {
			switch (lineType) {
				case horizontal:
					return getFigureFromRow(gameField, gameField.getHorizontalLines(), i, j);
				case vertical:
					return getFigureFromRow(gameField, gameField.getVerticalLines(), i, j);
				case leftDiagonal:
					return getFigureFromRow(gameField, gameField.getLeftDiagonalLines(), i, j);
				case rightDiagonal:
					return getFigureFromRow(gameField, gameField.getRightDiagonalLines(), i, j);
				default:
					throw new Error("Unknown Line Type");
			}
		}

		private static Figure getFigureFromRow(GameField gameField, Figure[][] row, int i, int j) {
			Figure figure = row[i][j];
			if (figure == null) {
				return gameField.emptyFigure;
			} else {
				return figure;
			}
		}

		private static void initRowFrom(Figure[][] row, Figure[][] initFromRow, int sizeX, int sizeY) {
			//TODO Not working for right diagonal for example
			for (int i = 0; i < sizeX; i++) {
				if (initFromRow[i] == null) {
					continue;
				}
				for (int j = 0; j < sizeY; j++) {
					Figure figure = initFromRow[i][j];
					if (figure == null) {
						continue;
					}
					row[i][j] = new Figure(figure);
				}
			}
		}

		private static void initWithEmptyFigure(Figure[][] row, int sizeX, int sizeY, Figure figure) {
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < sizeY; j++) {
					row[i][j] = figure;
				}
			}
		}

		private static int[] invertToVerticalPosition(int positionX, int positionY) {
			return new int[]{positionY, positionX};
		}

		private static int[] invertToLeftDiagonalPositions(int positionX, int positionY, int sizeX) {
			int i, j;

			i = ((sizeX - 1) - positionX) + positionY;
			if (positionY < positionX) {
				j = positionY;
			} else {
				j = positionX;
			}


			return new int[]{i, j};
		}

		private static int[] invertToRightDiagonalPositions(int positionX, int positionY, int sizeX) {
			int i, j;

			i = (positionX + positionY);
			if (positionX + positionY <= (sizeX - 1)) {
				j = positionY;
			} else {
				j = sizeX - positionX;
			}

			return new int[]{i, j};
		}

		private static void addToHorizontal(Figure figure, Figure[][] rows) {
			int xPos = figure.getPositionX();
			int yPos = figure.getPositionY();

			rows[xPos][yPos] = figure;
		}

		private static void addToVertical(Figure figure, Figure[][] rows) {
			int[] positions = invertToVerticalPosition(figure.getPositionX(), figure.getPositionY());
			int xPos = positions[0];
			int yPos = positions[1];

			rows[xPos][yPos] = figure;
		}

		private static void addToLeftDiagonal(Figure figure, Figure[][] rows, int sizeX) {
			int[] positions = invertToLeftDiagonalPositions(figure.getPositionX(), figure.getPositionY(), sizeX);
			int xPos = positions[0];
			int yPos = positions[1];

			rows[xPos][yPos] = figure;
		}

		private static void addToRightDiagonal(Figure figure, Figure[][] rows, int sizeX) {
			int[] positions = invertToRightDiagonalPositions(figure.getPositionX(), figure.getPositionY(), sizeX);
			int xPos = positions[0];
			int yPos = positions[1];

			rows[xPos][yPos] = figure;
		}

		private static void removeHorizontal(Figure figure, Figure[][] rows) {
			int xPos = figure.getPositionX();
			int yPos = figure.getPositionY();

			rows[xPos][yPos] = null;
		}

		private static void removeVertical(Figure figure, Figure[][] rows) {
			int[] positions = invertToVerticalPosition(figure.getPositionX(), figure.getPositionY());
			int xPos = positions[0];
			int yPos = positions[1];

			rows[xPos][yPos] = null;
		}

		private static void removeLeftDiagonal(Figure figure, Figure[][] rows, int sizeX) {
			int[] positions = invertToLeftDiagonalPositions(figure.getPositionX(), figure.getPositionY(), sizeX);
			int xPos = positions[0];
			int yPos = positions[1];

			rows[xPos][yPos] = null;
		}

		private static void removeRightDiagonal(Figure figure, Figure[][] rows, int sizeX) {
			int[] positions = invertToRightDiagonalPositions(figure.getPositionX(), figure.getPositionY(), sizeX);
			int xPos = positions[0];
			int yPos = positions[1];

			rows[xPos][yPos] = null;
		}
	}

}