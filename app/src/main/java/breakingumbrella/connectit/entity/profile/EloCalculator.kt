package breakingumbrella.connectit.entity.profile

class EloCalculator {
    private fun getExpectation(ratingA: Int, ratingB: Int): Double {
        return 1.0 / (1.0 + Math.pow(10.0, (ratingB - ratingA) / 400.0))
    }

    private fun getCoefficient(rating: Int): Int {
        val coefficient: Int

        if (rating < 1000)
            coefficient = 45
        else if (rating < 1400)
            coefficient = 40
        else if (rating < 1600)
            coefficient = 35
        else if (rating < 1800)
            coefficient = 30
        else if (rating < 2000)
            coefficient = 25
        else if (rating < 2200)
            coefficient = 20
        else if (rating < 2400)
            coefficient = 15
        else if (rating < 2600)
            coefficient = 10
        else
            coefficient = 5

        return coefficient
    }

    fun getWinRating(ratingA: Int, ratingB: Int): Int {
        val expectation = getExpectation(ratingA, ratingB)
        return Math.round(ratingA + getCoefficient(ratingA) * (1.0 - expectation)).toInt()
    }

    fun getTieRating(ratingA: Int, ratingB: Int): Int {
        val expectation = getExpectation(ratingA, ratingB)
        return Math.round(ratingA + getCoefficient(ratingA) * (0.5 - expectation)).toInt()
    }

    fun getLoseRating(ratingA: Int, ratingB: Int): Int {
        val expectation = getExpectation(ratingA, ratingB)
        return Math.round(ratingA + getCoefficient(ratingA) * (0.0 - expectation)).toInt()
    }
}