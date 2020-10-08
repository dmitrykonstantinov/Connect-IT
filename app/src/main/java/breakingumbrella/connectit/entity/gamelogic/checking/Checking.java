package breakingumbrella.connectit.entity.gamelogic.checking;

import java.util.ArrayList;
import java.util.List;

import breakingumbrella.connectit.entity.gamelogic.boundaries.IChecking;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.FieldCheckResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public class Checking implements IChecking {

    //Constant to move to config
    private int figureCountToCross = 3;

    public FieldCheckResult check(GameField gameField) {
        FieldCheckResult fieldCheckResult = new FieldCheckResult();

        Combinations combinations = checkRows(gameField, GameField.LineType.horizontal, gameField.getSizeX(), gameField.getSizeY());
        combinations.add(checkRows(gameField, GameField.LineType.vertical, gameField.getSizeX(), gameField.getSizeY()));
        combinations.add(checkRows(gameField, GameField.LineType.leftDiagonal, gameField.getLeftDiagonalLines().length, gameField.getSizeY()));
        combinations.add(checkRows(gameField, GameField.LineType.rightDiagonal, gameField.getRightDiagonalLines().length, gameField.getSizeY()));

        fieldCheckResult.fieldChanges = combinations.getMostValuableCombination();
        if (fieldCheckResult.fieldChanges.size() > 0) {
            fieldCheckResult.figureType = getWinFigure(fieldCheckResult.fieldChanges).getFigureType();
        }
        if (fieldCheckResult.fieldChanges.size() == figureCountToCross) {
            fieldCheckResult.score = 1;
        } else {
            fieldCheckResult.score = fieldCheckResult.fieldChanges.size();
        }

        crossFigures(gameField, fieldCheckResult);

        return fieldCheckResult;
    }

    private void crossFigures(GameField gameField, FieldCheckResult fieldCheckResult) {
        for (Figure figure : fieldCheckResult.fieldChanges) {
            gameField.crossFigure(figure);
        }
    }

    private Combinations checkRows(GameField gameField, GameField.LineType lineType, int sizeX, int sizeY) {
        List<List<Figure>> combinationsToCross = new ArrayList<>();
        List<Figure> buffer;
        for (int x = 0; x < sizeX; x++) {
            buffer = getNewBuffer();
            checkRow(buffer, combinationsToCross, gameField, lineType, sizeY, x);
            if (canCross(buffer)) {
                combinationsToCross.add(buffer);
            }
        }
        return new Combinations(combinationsToCross);
    }

    private void checkRow(List<Figure> buffer, List<List<Figure>> combinationsToCross,
                          GameField gameField, GameField.LineType lineType, int sizeY, int x) {
        for (int y = 0; y < sizeY; y++) {
            Figure figure = GameField.RawGameField.getFigureFromRow(gameField, lineType, x, y);
            if (addToBuffer(buffer, figure) == false) {
                if (canCross(buffer)) {
                    combinationsToCross.add(buffer);
                }
                buffer = getNewBuffer();
            }
        }
        if (canCross(buffer)) {
            combinationsToCross.add(buffer);
        }
    }


    private boolean canCross(List<Figure> buffer) {
        if (buffer.size() < figureCountToCross) {
            return false;
        }
        Figure previousFigure = null;
        int sameFigureInRowCount = 1;
        for (int i = 0; i < buffer.size(); i++) {
            if (previousFigure == null) {
                previousFigure = buffer.get(i);
            } else if (previousFigure.getFigureType() == buffer.get(i).getFigureType()) {
                sameFigureInRowCount++;
                if (sameFigureInRowCount == figureCountToCross) {
                    return true;
                }
            } else {
                sameFigureInRowCount = 1;
                previousFigure = buffer.get(i);
            }
        }
        return false;
    }

    private Figure getWinFigure(List<Figure> buffer) {
        Figure previousFigure = null;
        int sameFigureInRowCount = 1;
        for (int i = 0; i < buffer.size(); i++) {
            if (previousFigure == null) {
                previousFigure = buffer.get(i);
            } else if (previousFigure.getFigureType() == buffer.get(i).getFigureType()) {
                sameFigureInRowCount++;
                if (sameFigureInRowCount == figureCountToCross) {
                    return buffer.get(i);
                }
            } else {
                sameFigureInRowCount = 1;
                previousFigure = buffer.get(i);
            }
        }
        return null;
    }

    private void removeLastFromBuffer(List<Figure> buffer) {
        buffer.remove(buffer.size() - 1);
    }

    private List<Figure> getNewBuffer() {
        return new ArrayList<>();
    }

    /*
     * Return true if successfully added
     * */
    private boolean addToBuffer(List<Figure> buffer, Figure figure) {
        if (figure.isEmpty() || figure.isFigureCrossed()) {
            return false;
        } else if (buffer.size() > 0
                && buffer.get(buffer.size() - 1).getFigureType() != figure.getFigureType()
                && (getWinFigure(buffer) == null || getWinFigure(buffer).getFigureType() != figure.getFigureType())
                && figure.getAbilityType() == AbilityType.durableFigure) {
            return false;
        } else if (buffer.size() > 0
                && buffer.get(buffer.size() - 1).getFigureType() != figure.getFigureType()
                && (getWinFigure(buffer) == null || getWinFigure(buffer).getFigureType() != figure.getFigureType())
                && buffer.get(buffer.size() - 1).getAbilityType() == AbilityType.durableFigure) {
            return false;
        } else if (buffer.size() >= figureCountToCross) {
            if (isLastFiguresIsSame(buffer) && buffer.get(buffer.size() - 1).getFigureType() == figure.getFigureType()) {
                return false;
            }
        }
        buffer.add(figure);
        return true;
    }

    private boolean isLastFiguresIsSame(List<Figure> buffer) {
        Figure figure = buffer.get(buffer.size() - 1);//Last
        for (int i = buffer.size() - 1; i >= buffer.size() - figureCountToCross; i--) {
            if (figure.getFigureType() != buffer.get(i).getFigureType()) {
                return false;
            }
        }
        return true;
    }

    private class Combinations {

        List<List<Figure>> combinationsToCross;

        private Combinations(List<List<Figure>> combinationsToCross) {
            this.combinationsToCross = combinationsToCross;
        }

        private void add(Combinations combinations) {
            this.combinationsToCross.addAll(combinations.combinationsToCross);
        }

        private List<Figure> getMostValuableCombination() {
            int maxSize = 0, position = 0;
            for (int i = 0; i < combinationsToCross.size(); i++) {
                if (combinationsToCross.get(i).size() > maxSize) {
                    maxSize = combinationsToCross.get(i).size();
                    position = i;
                }
            }
            return combinationsToCross.size() == 0 ? new ArrayList<>() : combinationsToCross.get(position);
        }

    }

}
