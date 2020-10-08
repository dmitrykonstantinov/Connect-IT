package breakingumbrella.connectit.entity.entities;

import java.io.Serializable;

//Class for timer wrapper with some app specific logic
public class FindGameTimer implements Serializable {

	private long timeInQueue;
	private long lastUpdateTime;

	public void updateTimeData() {
		long currentMillis = System.currentTimeMillis();
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentMillis;
			return;
		}
		timeInQueue += currentMillis - lastUpdateTime;
		lastUpdateTime = currentMillis;
	}

	public long getTimeInQueueInSeconds() {
		return timeInQueue / 1000L;
	}

	public long getTimeSinceLastConnectionInSec() {
		return (System.currentTimeMillis() - lastUpdateTime) / 1000L;
	}


	//<============== Only for serialisation=============>

	public long getTimeInQueue() {
		return timeInQueue;
	}

	public void setTimeInQueue(long timeInQueue) {
		this.timeInQueue = timeInQueue;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
