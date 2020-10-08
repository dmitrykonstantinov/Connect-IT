package breakingumbrella.connectit.entity.entities;

import java.io.Serializable;

public class PlayerInGameTimer implements Serializable {

	private long lastUpdateTime;

	public void updateTimeData() {
		lastUpdateTime = System.currentTimeMillis();
	}

	public long getLastUpdateTimeInSec() {
		return (System.currentTimeMillis() - lastUpdateTime) / 1000;
	}


	//<============== Only for serialisation=============>

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
