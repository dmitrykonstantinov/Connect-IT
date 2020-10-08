package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class TutorialStarted implements IAnalyticsEvent {

	private final String eventName = "tutorial_started";

	@Override
	public String getEventName() {
		return eventName;
	}

	@Override
	public Bundle getBundle() {
		return new Bundle();
	}

}
