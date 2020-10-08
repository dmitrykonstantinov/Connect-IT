package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.entity.gamelogic.helpers.GameTypes;
import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class GameAborted implements IAnalyticsEvent {

	private final String eventName = "abort_game";
	private Integer turnsCount;

	public GameAborted(int turnsCount) {
		this.turnsCount = turnsCount;
	}

	@Override
	public String getEventName() {
		return eventName;
	}

	@Override
	public Bundle getBundle() {
		Bundle bundle = new Bundle();

		bundle.putInt("turns_count", turnsCount);

		return bundle;
	}

}
