package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class UserPassStep3Tutorial implements IAnalyticsEvent {

	private final String eventName = "user_pass_step_3";
	private int timeSpend;

	public UserPassStep3Tutorial(int timeSpend) {
		this.timeSpend = timeSpend;
	}

	@Override
	public String getEventName() {
		return eventName;
	}

	@Override
	public Bundle getBundle() {
		Bundle bundle = new Bundle();

		bundle.putInt("time_spend", timeSpend);

		return bundle;
	}

}
