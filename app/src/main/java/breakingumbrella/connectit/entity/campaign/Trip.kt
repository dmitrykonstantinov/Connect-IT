package breakingumbrella.connectit.entity.campaign

import java.io.Serializable

data class Trip(val lvls: List<Level>, val rewardCode: Int, val rewardType: Int) : Serializable {
    constructor() : this(mutableListOf(), -1, 0)

    fun isTripCompleted(): Boolean {
        for (el in lvls) {
            if (el.isCompleted == false) {
                return false
            }
        }
        return true
    }

}