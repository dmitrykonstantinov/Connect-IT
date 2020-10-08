package breakingumbrella.connectit.entity.campaign

import java.io.Serializable

data class Level(var isCompleted: Boolean) : Serializable {
    constructor() : this(false)
}