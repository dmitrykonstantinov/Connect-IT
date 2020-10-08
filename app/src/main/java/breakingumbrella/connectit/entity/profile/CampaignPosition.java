package breakingumbrella.connectit.entity.profile;

import java.io.Serializable;

public class CampaignPosition implements Serializable {

	public CampaignPosition() { }

	public CampaignPosition(int trip, int lvl) {
		this.trip = trip;
		this.lvl = lvl;
	}

	int trip;
	int lvl;

	public void setTrip(int trip) {
		this.trip = trip;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getTrip() {
		return trip;
	}

	public int getLvl() {
		return lvl;
	}
}