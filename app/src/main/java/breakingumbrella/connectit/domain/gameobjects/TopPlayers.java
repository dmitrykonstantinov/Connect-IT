package breakingumbrella.connectit.domain.gameobjects;

public class TopPlayers {

	private RatingItem firstPlace;
	private RatingItem secondPlace;
	private RatingItem thirdPlace;


	public RatingItem getFirstPlace() {
		return firstPlace;
	}

	public void setFirstPlace(RatingItem firstPlace) {
		this.firstPlace = firstPlace;
	}

	public RatingItem getSecondPlace() {
		return secondPlace;
	}

	public void setSecondPlace(RatingItem secondPlace) {
		this.secondPlace = secondPlace;
	}

	public RatingItem getThirdPlace() {
		return thirdPlace;
	}

	public void setThirdPlace(RatingItem thirdPlace) {
		this.thirdPlace = thirdPlace;
	}

}
