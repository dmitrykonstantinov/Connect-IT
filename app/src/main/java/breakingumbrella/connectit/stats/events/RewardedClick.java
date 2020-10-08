package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class RewardedClick implements IAnalyticsEvent {

	private final String eventName = "rewarded_click";

	@Override
	public String getEventName() {
		return eventName;
	}

	@Override
	public Bundle getBundle() {
		return new Bundle();
	}
}
