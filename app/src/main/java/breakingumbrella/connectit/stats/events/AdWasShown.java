package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.ad.ShowReason;
import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class AdWasShown implements IAnalyticsEvent {

	private final String eventName = "ad_shown";
	private final String showCondition;

	public AdWasShown(ShowReason showReason) {
		this.showCondition = getStringReason(showReason);
	}

	@Override
	public String getEventName() {
		return eventName;
	}

	@Override
	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putString("show_condition", showCondition);
		return bundle;
	}

	private String getStringReason(ShowReason showReason) {
		String strShowReason;
		switch (showReason) {
			case loose_lvl: strShowReason = "loose_lvl"; break;
			case win_and_turns_played: strShowReason = "win_and_turns_played"; break;
			default: strShowReason = "Undefined"; break;
		}
		return strShowReason;
	}

}
