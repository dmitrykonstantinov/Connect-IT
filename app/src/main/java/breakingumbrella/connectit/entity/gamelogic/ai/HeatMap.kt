package breakingumbrella.connectit.entity.gamelogic.ai

import breakingumbrella.connectit.entity.gameobjects.Figure
import breakingumbrella.connectit.entity.gameobjects.GameField

class HeatMap(gameField: GameField) : Iterator<IntArray> {

    init {
        updateHeatMap(gameField)
    }

    private data class Cell(val heat: Int, val position: IntArray)

    private lateinit var heatCells: MutableList<Cell>

    private lateinit var iterator: ListIterator<Cell>

    private var figure = Figure()

    fun isHeatMapIsEmpty(): Boolean {
        return heatCells.isEmpty()
    }

    fun getFirstHotCell(): IntArray? {
        sort()
        return heatCells.first().position
    }

    fun sort() {
        heatCells = heatCells.sortedBy { it.heat } .toMutableList()
    }

    fun updateHeatMap(gameField: GameField) {
        heatCells = mutableListOf()
        iterator = heatCells.listIterator()
        var cellHeat = 0
        for (i in 0 until gameField.sizeX) {
            for (j in 0 until gameField.sizeY) {
                if (gameField.getFigureAtPosition(i, j).isEmpty) {
                    cellHeat += updateRight(gameField, i, j)
                    cellHeat += updateLeft(gameField, i, j)
                    cellHeat += updateTop(gameField, i, j)
                    cellHeat += updateBottom(gameField, i, j)
                    cellHeat += updateLowerLeftCorner(gameField, i, j)
                    cellHeat += updateLowerRightCorner(gameField, i, j)
                    cellHeat += updateUpperLeftCorner(gameField, i, j)
                    cellHeat += updateUpperRightCorner(gameField, i, j)
                    if (cellHeat > 0) {
                        heatCells.add(Cell(cellHeat, intArrayOf(i, j)))
                    }
                    cellHeat = 0
                }
            }
        }
    }

    //===================ITERATOR==================//

    private var currentIndex = 0

    override fun hasNext(): Boolean {
        return currentIndex < heatCells.size
    }

    override fun next(): IntArray {
        return heatCells[currentIndex++].position
    }

    //===================ITERATOR==================//

    private fun updateRight(gameField: GameField, i: Int, j: Int): Int {
        figure = gameField.getFigureAtPosition(i, j + 1)
        return if (figure.isEmpty == false && figure.isFigureCrossed == false) {
            1
        } else 0
    }

    private fun updateLeft(gameField: GameField, i: Int, j: Int): Int {
        figure = gameField.getFigureAtPosition(i, j - 1)
        return if (figure.isEmpty == false && figure.isFigureCrossed == false) {
            1
        } else 0
    }

    private fun updateTop(gameField: GameField, i: Int, j: Int): Int {
        figure = gameField.getFigureAtPosition(i - 1, j)
        return if (figure.isEmpty == false && figure.isFigureCrossed == false) {
            1
        } else 0
    }

    private fun updateBottom(gameField: GameField, i: Int, j: Int): Int {
        figure = gameField.getFigureAtPosition(i + 1, j)
        return if (figure.isEmpty == false && figure.isFigureCrossed == false) {
            1
        } else 0
    }

    private fun updateUpperRightCorner(gameField: GameField, i: Int, j: Int): Int {
        figure = gameField.getFigureAtPosition(i - 1, j + 1)
        return if (figure.isEmpty == false && figure.isFigureCrossed == false) {
            1
        } else 0
    }

    private fun updateUpperLeftCorner(gameField: GameField, i: Int, j: Int): Int {
        figure = gameField.getFigureAtPosition(i - 1, j - 1)
        return if (figure.isEmpty == false && figure.isFigureCrossed == false) {
            1
        } else 0
    }

    private fun updateLowerLeftCorner(gameField: GameField, i: Int, j: Int): Int {
        figure = gameField.getFigureAtPosition(i + 1, j - 1)
        return if (figure.isEmpty == false && figure.isFigureCrossed == false) {
            1
        } else 0
    }

    private fun updateLowerRightCorner(gameField: GameField, i: Int, j: Int): Int {
        figure = gameField.getFigureAtPosition(i + 1, j + 1)
        return if (figure.isEmpty == false && figure.isFigureCrossed == false) {
            1
        } else 0
    }

}