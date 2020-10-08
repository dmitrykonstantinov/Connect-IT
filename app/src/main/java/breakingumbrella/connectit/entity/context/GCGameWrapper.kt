package breakingumbrella.connectit.entity.context

import breakingumbrella.connectit.domain.gameobjects.TurnResult
import breakingumbrella.connectit.entity.entities.ClientGameState
import breakingumbrella.connectit.entity.entities.EloContainer
import breakingumbrella.connectit.entity.entities.GameState
import breakingumbrella.connectit.entity.entities.GameStateNew
import breakingumbrella.connectit.entity.gamelogic.boundaries.IChecking
import breakingumbrella.connectit.entity.gamelogic.checking.Checking
import breakingumbrella.connectit.entity.gameobjects.AbilityType
import breakingumbrella.connectit.entity.gameobjects.FieldCheckResult
import breakingumbrella.connectit.entity.gameobjects.Figure
import breakingumbrella.connectit.entity.gameobjects.MatchResult
import breakingumbrella.connectit.entity.profile.GCProfile
import java.lang.Exception
import java.util.ArrayList

fun GameContext.isItMyTurn(myGcProfileId : String) : Boolean {
    return this.gameState.playerIdWhichTurnNow.equals(this.getMe(myGcProfileId).id)
}

fun GameContext.addFigure(figure : Figure, myGcProfileId: String) : FieldCheckResult {
    this.turnHistory.add(figure)
    if(figure.abilityType != AbilityType.destroyFigure) {
        this.gameField.addFigure(figure)
        this.incrementTurnNumber()

    }
    val icheking : IChecking = Checking()
    val fieldCheckResult = icheking.check(gameField)
    this.updateScore(fieldCheckResult)
    return fieldCheckResult
}

fun GameContext.updateScore(fieldCheckResult: FieldCheckResult) {
    for (player in this.players) {
        if(player.figure.figureType == fieldCheckResult.figureType) {
            player.score += fieldCheckResult.score
        }
    }
}

fun GameContext.isGameEnd() : Boolean {
//    if (turnNumber > 25) return true
//    else return false
    return this.turnNumber == this.gameSettings.targetTurnsNumber
}

fun GameContext.getMatchResult(myGcProfileId: String) : MatchResult {
    if(this.getMe(myGcProfileId).score > this.getEnemy(myGcProfileId).score) {
        return MatchResult.Win
    }
    else if (this.getMe(myGcProfileId).score == this.getEnemy(myGcProfileId).score) {
        return MatchResult.Tie
    }
    else {
        return MatchResult.Lose
    }
}

fun GameContext.getScore(myGcProfileId: String) : HashMap<Int, Int> {
    val score : HashMap<Int, Int> = hashMapOf()
    score.put(this.getMe(myGcProfileId).figure.figureType, this.getMe(myGcProfileId).score)
    score.put(this.getEnemy(myGcProfileId).figure.figureType, this.getEnemy(myGcProfileId).score)
    return score
}

fun GameContext.getFiguresList() : List<Figure> {
    val figuresList = mutableListOf<Figure>()
    for(player in this.players) {
        figuresList.add(player.figure)
    }
    return figuresList
}

fun GameContext.getFigureDependOnTurn(myGcProfileId: String) : Figure {
    val figureTuPut = Figure()
    if (this.turnNumber % 2 == 0) {
        figureTuPut.setFigure(this.getMe(myGcProfileId).getFigure().figureType)
    } else {
        figureTuPut.setFigure(this.getEnemy(myGcProfileId).getFigure().figureType)
    }
    return figureTuPut
}

fun GameContext.createTurnResult(myGcProfileId : String, fieldCheckResult: FieldCheckResult) : TurnResult {
    val turnResult = TurnResult()
    turnResult.gameField = this.gameField
    turnResult.score = this.getScore(myGcProfileId)
    turnResult.figure = this.turnHistory.lastOrNull()
    turnResult.positions = fieldCheckResult.fieldChanges
    turnResult.isEmpty = false
    return turnResult
}

fun GameContext.createFieldAndScoreTurnResult(myGcProfileId : String): TurnResult {
    val turnResult = TurnResult()
    turnResult.gameField = this.gameField
    turnResult.score = this.getScore(myGcProfileId)
    return turnResult
}

fun GameContext.getRandomPosition() : Figure {
    for (i in 0 until this.gameField.sizeX) {
        for (j in 0 until this.gameField.sizeY) {
            if (this.gameField.isFigureExistAtPosition(i, j) == false) {
                val figure = Figure()
                figure.setPosition(i, j)
                return figure
            }
        }
    }
    return Figure()
}

fun GameContext.createNew(gcProfile: GCProfile) : GameContext {
    val gameState = GameState()
    gameState.gameStateNew = GameStateNew.WaitingForPlayers

    val profiles = ArrayList<GCProfile>()
    profiles.add(gcProfile)

    val eloContainer = EloContainer(gcProfile.getElo(), 50)
    return GameContext(gameSettings, gameState, profiles, eloContainer)
}

fun GameContext.removeMe(gameContexts: MutableList<GameContext>) : MutableList<GameContext> {
    for (i in gameContexts.indices.reversed()) {
        if (gameContexts[i].id == this.getId()) {
            gameContexts.removeAt(i)
        }
    }
    return gameContexts
}

fun GameContext.getClientGameState(myGcProfileId: String) : ClientGameState {
    if (this.gameState.gameStateNew == GameStateNew.InProgress) {
        if (this.gameState.playerIdWhichTurnNow == myGcProfileId) {
            return ClientGameState.Move
        } else return ClientGameState.Wait
    } else if (this.gameState.gameStateNew == GameStateNew.WaitingForPlayers) {
        return ClientGameState.WaitingForPlayers
    } else return ClientGameState.Finished
}

fun GameContext.switchPlayerTurn(myGcProfileId: String) {
    this.gameState.playerIdWhichTurnNow = this.getEnemy(myGcProfileId).id
}

fun GameContext.fromGC(gameContext: GameContext) {
    this.gameState = gameContext.gameState
    this.turnNumber = gameContext.turnNumber
    this.gameField = gameContext.gameField
    this.eloContainer = gameContext.eloContainer
    this.gameSettings = gameContext.gameSettings
    this.id = gameContext.id
    this.players = gameContext.players
    this.turnHistory = gameContext.turnHistory
}

fun GameContext.turnHistoryLast() : Figure? {
    try {
        return this.turnHistory.last()
    }
    catch (ex : Exception) {
        return null
    }
}

//TODO CHeck that player order is always the same. Or replace list with sorted set or smth

fun GameContext.getUsedFiguresList() : List<Int> {
    val figures = mutableListOf<Int>()
    for(player in this.players) {
        figures.add(player.figure.figureType)
    }
    return figures
}