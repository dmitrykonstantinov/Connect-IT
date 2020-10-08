package breakingumbrella.connectit.entity.gamelogic.ai

import breakingumbrella.connectit.entity.context.GameContext
import breakingumbrella.connectit.entity.context.getMe
import breakingumbrella.connectit.entity.context.getUsedFiguresList
import breakingumbrella.connectit.entity.gamelogic.boundaries.IChecking
import breakingumbrella.connectit.entity.gamelogic.checking.Checking
import breakingumbrella.connectit.entity.gameobjects.Figure
import breakingumbrella.connectit.entity.gameobjects.GameField
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap

class Ai {

    //TODO Move to config
    private var targetDepth = 0 // (FieldSize * FieldSize) targetDepth number of times

    fun startAi(aiSettings: AiSettings, gameContext: GameContext, aiProfileId: String, onSuccess: (figure: Figure) -> Unit) {
        GlobalScope.launch {
            val random = Random()
            val figuresInUse = gameContext.getUsedFiguresList()
            val heatMap = HeatMap(gameContext.gameField)
            if (heatMap.isHeatMapIsEmpty()) {
                onSuccess.invoke(generateRandomTurn(gameContext.getMe(aiProfileId).figure,
                        gameContext.gameField.sizeX,
                        gameContext.gameField.sizeY))
                return@launch
            }

            targetDepth = aiSettings.targetDepth
            if (random.nextInt(100) < aiSettings.probabilityToRandomMove) {
                onSuccess.invoke(generateRandomTurn(gameContext.getMe(aiProfileId).figure, heatMap))
                return@launch
            }


            val firstDepthResult = generateFirstDepthResults(heatMap, gameContext.gameField,
                    gameContext.getMe(aiProfileId).figure)
            val aiInputs = generateAiInputs(firstDepthResult, gameContext.gameField, targetDepth,
                    gameContext.getMe(aiProfileId).figure, figuresInUse)

            val jobs = mutableListOf<Deferred<HashMap<Int, Int>>>()
            for (i in 0..aiInputs.size - 1) {
                jobs += this.mainAiLoop(aiInputs.get(i), figuresInUse, gameContext.getMe(aiProfileId).figure)
            }

            var aiScore = -25000
            var position = 0

            var i = 0

            val testRawResultArray: MutableList<HashMap<Int, Int>> = mutableListOf()
            val testResultArray: MutableList<Int> = mutableListOf()

            jobs.awaitAll().forEach { jobValue ->
                run {
                    testRawResultArray.add(jobValue)
                    var resultScore = jobValue.get(gameContext.getMe(aiProfileId).figure.figureType)!! //It's Ai Result for that position
                    jobValue.forEach {
                        //Now we compare that result with others for that turn
                        if (it.key == gameContext.getMe(aiProfileId).figure.figureType) {
                            //Don't compare Ai with Ai
                        } else if (resultScore - jobValue.get(it.key)!! < resultScore) { //Here choose the worst result from that position
                            resultScore = resultScore - jobValue.get(it.key)!!
                        }
                    }
                    testResultArray.add(resultScore)
                    if (aiScore < resultScore) {
                        position = i
                        aiScore = resultScore
                    }
                    i++
                }
            }

            onSuccess.invoke(firstDepthResult.get(position).figure)

        }
    }

    private fun generateFirstDepthResults(heatMap: HeatMap, initialGameField: GameField, aiFigure: Figure): List<AiResult> {
        val checking: IChecking = Checking()
        val firstDepthResults: MutableList<AiResult> = ArrayList()
        while (heatMap.hasNext()) {

            val position = heatMap.next()
            val figureToPut = Figure(aiFigure.figureType)
            figureToPut.setPosition(position[0], position[1])

            val gameField = GameField(initialGameField)
            gameField.addFigure(figureToPut)

            val fieldCheckResult = checking.check(gameField)

            val result = AiResult(figureToPut, hashMapOf(Pair(fieldCheckResult.figureType, fieldCheckResult.score)))
            firstDepthResults.add(result)
        }
        return firstDepthResults
    }

    private fun generateAiInputs(firstDepthResults: List<AiResult>, initialGameField: GameField,
                                 targetDepth: Int, aiFigure: Figure, figuresInUse: List<Int>): List<AiInput> {
        val inputs: MutableList<AiInput> = ArrayList()
        for (result in firstDepthResults) {
            val input = AiInput(initialGameField,
                    aiFigure,
                    result.score,
                    targetDepth,
                    1)
            val gameField = GameField(initialGameField)
            gameField.addFigure(result.figure)
            input.currentDepth = 1
            input.score = result.score
            input.gameField = gameField
            input.targetDepth = targetDepth
            input.currentDepthFigure = Figure(getNextFigureType(figuresInUse, input.currentDepthFigure)) //switch to next figure
            inputs.add(input)
        }
        return inputs
    }

    fun CoroutineScope.mainAiLoop(firstInput: AiInput, figuresInUse: List<Int>, figureAi: Figure): Deferred<HashMap<Int, Int>> {
        return async {
            val score: HashMap<Int, Int> = hashMapOf()
            for (value in figuresInUse) {
                score.put(value, 0)
            }
            val checking: IChecking = Checking()
            val heatMap = HeatMap(firstInput.gameField)
            val stack = Stack<AiInput>()
            stack.push(firstInput)

            while (stack.size > 0) {

                val input = stack.pop()

                if (input.currentDepth < input.targetDepth) {

                    heatMap.updateHeatMap(input.gameField)

                    while (heatMap.hasNext()) {
                        val position = heatMap.next()
                        val figureToPut = Figure(input.currentDepthFigure.figureType)
                        figureToPut.setPosition(position[0], position[1])

                        val gameField = GameField(input.gameField)
                        gameField.addFigure(figureToPut)

                        val fieldCheckResult = checking.check(gameField)

                        if (fieldCheckResult.score > 0) {
                            score.put(fieldCheckResult.figureType, score.get(fieldCheckResult.figureType)!! +
                                    (fieldCheckResult.score * 200) / (input.currentDepth * 2))
                        }

                        //No need to push new inputs to stack because it will be never proceed cause of target depth
                        if (input.currentDepth + 1 < input.targetDepth) {
                            val newInput = AiInput(gameField,
                                    //TODO Here i add to stack inputs that never will be proceed cause of currentDepth
                                    Figure(getNextFigureType(figuresInUse, input.currentDepthFigure)),
                                    score,
                                    targetDepth,
                                    input.currentDepth + 1)
                            stack.push(newInput)
                        }
                    }
                } else {
                    if (input.score.get(input.currentDepthFigure.figureType)!! > score.get(input.currentDepthFigure.figureType)!!) {
                        score.put(input.currentDepthFigure.figureType, input.score.get(input.currentDepthFigure.figureType)!!)
                    }
                }
            }
            score
        }
    }

    private fun getNextFigureType(figureList: List<Int>, figure: Figure): Int {
        var currentFigureIndex = figureList.indexOf(figure.figureType)
        if (currentFigureIndex >= figureList.size - 1) {
            currentFigureIndex = 0
        } else {
            currentFigureIndex++
        }
        return figureList.get(currentFigureIndex)
    }

    private fun generateRandomTurn(aiFigure: Figure, xSize: Int, ySize: Int): Figure {
        val random = Random()
        val figure = Figure()
        figure.setFigure(aiFigure.figureType)
        figure.setPosition(random.nextInt(xSize), random.nextInt(ySize))
        return figure
    }

    private fun generateRandomTurn(aiFigure: Figure, heatMap: HeatMap): Figure {
        val random = Random()
        val figure = Figure()
        figure.setFigure(aiFigure.figureType)
        figure.setPosition(heatMap.getFirstHotCell()!![0], heatMap.getFirstHotCell()!![1])
        return figure
    }

}

data class AiInput(
        var gameField: GameField,
        var currentDepthFigure: Figure,
        var score: HashMap<Int, Int>,
        var targetDepth: Int,
        var currentDepth: Int
)

data class AiResult(
        var figure: Figure,
        var score: HashMap<Int, Int>
)