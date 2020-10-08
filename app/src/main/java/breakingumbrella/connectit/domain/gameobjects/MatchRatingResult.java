package breakingumbrella.connectit.domain.gameobjects;

/**
 * Created by dmitryk on 13.09.2017.
 */

public class MatchRatingResult {

	private int myElo;
	private int deltaElo;
	private int turnsCount;

	public MatchRatingResult(int myElo, int deltaElo, int turnsCount) {
		this.myElo = myElo;
		this.deltaElo = deltaElo;
		this.turnsCount = turnsCount;
	}

	public int getRatingDelta() {
		return deltaElo;
	}

	public int getMyElo() {
		return myElo;
	}

	public int getTurnsCount() {
		return turnsCount;
	}


}
