package breakingumbrella.connectit.entity.entities;

public class DeleteTimer {

	private long timeInQueueToDelete;
	private long lastUpdateTime;

	public void updateTimeData() {
		long currentMillis = System.currentTimeMillis();
		if (lastUpdateTime == 0) {
			lastUpdateTime = currentMillis;
			return;
		}
		timeInQueueToDelete += currentMillis - lastUpdateTime;
		lastUpdateTime = currentMillis;
	}

	public long getTimeInQueueInSeconds() {
		return timeInQueueToDelete / 1000L;
	}

	public long getTimeSinceLastConnectionInSec() {
		return (System.currentTimeMillis() - lastUpdateTime) / 1000L;
	}


	//<============== Only for serialisation=============>

	public long getTimeInQueueToDelete() {
		return timeInQueueToDelete;
	}

	public void setTimeInQueueToDelete(long timeInQueueToDelete) {
		this.timeInQueueToDelete = timeInQueueToDelete;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
