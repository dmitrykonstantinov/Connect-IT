package breakingumbrella.connectit.domain.gameobjects;

public class RatingItem {

	private String nickname;
	private int place;
	private int rating;

	private boolean isItMe;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	public boolean isItMe() {
		return isItMe;
	}

	public void setItMe(boolean itMe) {
		isItMe = itMe;
	}

}
