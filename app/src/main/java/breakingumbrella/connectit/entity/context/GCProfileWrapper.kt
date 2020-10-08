package breakingumbrella.connectit.entity.context

import breakingumbrella.connectit.domain.gameobjects.MatchRatingResult
import breakingumbrella.connectit.entity.gameobjects.MatchResult
import breakingumbrella.connectit.entity.profile.EloCalculator
import breakingumbrella.connectit.entity.profile.GCProfile
import java.lang.Exception

fun GameContext.getMe(profileId : String) : GCProfile {
    try {
        return this.players.single { gcProfile -> gcProfile.id.equals(profileId) }
    }
    catch (ex : Exception) {
        throw Error()
    }
}

fun GameContext.getEnemy(profileId: String) : GCProfile {
    return this.players.single { gcProfile -> gcProfile.id.equals(profileId) == false }
}

fun GameContext.getNewRating(profileId: String) : MatchRatingResult {
    var eloCurrent = this.getMe(profileId).elo
    val eloCalculator = EloCalculator()
    var eloNew = 0
    when(this.getMatchResult(profileId)) {
        MatchResult.Win -> eloNew = eloCalculator.getWinRating(this.getMe(profileId).elo, this.getEnemy(profileId).elo)
        MatchResult.Tie -> eloNew = eloCalculator.getWinRating(this.getMe(profileId).elo, this.getEnemy(profileId).elo)
        MatchResult.Lose -> eloNew = eloCalculator.getWinRating(this.getMe(profileId).elo, this.getEnemy(profileId).elo)
    }
    val matchRatingResult = MatchRatingResult(eloNew, eloNew - eloCurrent, this.turnNumber)
    return matchRatingResult
}