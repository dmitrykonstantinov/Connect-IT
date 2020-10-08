package breakingumbrella.connectit.entity.gameobjects;

public class HeatMap {

	private int[][] heatCells;
	private Figure figure = new Figure();

	public HeatMap(GameField gameField) {
		updateHeatMap(gameField);
	}

	public HeatMap(int xSize, int ySize) {
		for(int i = 0; i < xSize; i++) {
			for(int j = 0; j < ySize; j++) {
				heatCells = new int[xSize][ySize];
			}
		}
	}

	public boolean isHeatMapIsEmpty() {
		boolean isEmpty = true;
		for(int i = 0; i < heatCells.length; i++) {
			for (int j = 0; j < heatCells[0].length; j++) {
				if(heatCells[i][j] > 0) {
					isEmpty = false;
				}
			}
		}
		return false;
	}

	public int[] getFirstHotCell() {
		int[] positon = {0, 0};
		for(int i = 0; i < heatCells.length; i++) {
			for(int j = 0; j < heatCells[0].length; j++) {
				if(heatCells[i][j] > 0) {
					positon[0] = i;
					positon[1] = j;
				}
			}
		}
		return positon;
	}

	public boolean isCellIsHot(int i, int j) {
		return heatCells[i][j] == 0 ;
	}

	public void add(int x, int y, int value) {
		heatCells[x][y] = value;
	}

	public void updateHeatMap(GameField gameField) {
		heatCells = new int[gameField.getSizeX()][gameField.getSizeY()];
		int cellHeat = 0;
		for(int i = 0; i < gameField.getSizeX(); i++) {
			for(int j = 0; j < gameField.getSizeY(); j++) {
				if(gameField.getFigureAtPosition(i, j).isEmpty()) {
					cellHeat += updateRight(gameField, i, j);
					cellHeat += updateLeft(gameField, i, j);
					cellHeat += updateTop(gameField, i, j);
					cellHeat += updateBottom(gameField, i, j);
					cellHeat += updateLowerLeftCorner(gameField, i, j);
					cellHeat += updateLowerRightCorner(gameField, i, j);
					cellHeat += updateUpperLeftCorner(gameField, i, j);
					cellHeat += updateUpperRightCorner(gameField, i, j);
					if (cellHeat > 0) {
						heatCells[i][j] = cellHeat;
					}
					cellHeat = 0;
				}
			}
		}
	}

	private int updateRight(GameField gameField, int i, int j) {
		figure = gameField.getFigureAtPosition(i, j + 1);
		if(figure.isEmpty() == false && figure.isFigureCrossed() == false) {
			return 1;
		}
		return 0;
	}

	private int updateLeft(GameField gameField, int i, int j) {
		figure = gameField.getFigureAtPosition(i, j - 1);
		if(figure.isEmpty() == false && figure.isFigureCrossed() == false) {
			return 1;
		}
		return 0;
	}

	private int updateTop(GameField gameField, int i, int j) {
		figure = gameField.getFigureAtPosition(i - 1, j);
		if(figure.isEmpty() == false && figure.isFigureCrossed() == false) {
			return 1;
		}
		return 0;
	}

	private int updateBottom(GameField gameField, int i, int j) {
		figure = gameField.getFigureAtPosition(i + 1, j);
		if(figure.isEmpty() == false && figure.isFigureCrossed() == false) {
			return 1;
		}
		return 0;
	}

	private int updateUpperRightCorner(GameField gameField, int i, int j) {
		figure = gameField.getFigureAtPosition(i - 1, j + 1);
		if(figure.isEmpty() == false && figure.isFigureCrossed() == false) {
			return 1;
		}
		return 0;
	}

	private int updateUpperLeftCorner(GameField gameField, int i, int j) {
		figure = gameField.getFigureAtPosition(i - 1, j - 1);
		if(figure.isEmpty() == false && figure.isFigureCrossed() == false) {
			return 1;
		}
		return 0;
	}

	private int updateLowerLeftCorner(GameField gameField, int i, int j) {
		figure = gameField.getFigureAtPosition(i + 1, j - 1);
		if(figure.isEmpty() == false && figure.isFigureCrossed() == false) {
			return 1;
		}
		return 0;
	}

	private int updateLowerRightCorner(GameField gameField, int i, int j) {
		figure = gameField.getFigureAtPosition(i + 1, j + 1);
		if(figure.isEmpty() == false && figure.isFigureCrossed() == false) {
			return 1;
		}
		return 0;
	}
}
