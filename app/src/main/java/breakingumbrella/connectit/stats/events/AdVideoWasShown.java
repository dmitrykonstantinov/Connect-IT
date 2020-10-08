package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.ad.ShowReason;
import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class AdVideoWasShown implements IAnalyticsEvent {

    private final String eventName = "ad_video_shown";

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("show_condition", "Avoid to loose progress");
        return bundle;
    }


}
