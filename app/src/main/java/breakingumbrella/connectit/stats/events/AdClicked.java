package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class AdClicked implements IAnalyticsEvent {

	private final String eventName = "ad_clicked";

	@Override
	public String getEventName() {
		return eventName;
	}

	@Override
	public Bundle getBundle() {
		return new Bundle();
	}

}
