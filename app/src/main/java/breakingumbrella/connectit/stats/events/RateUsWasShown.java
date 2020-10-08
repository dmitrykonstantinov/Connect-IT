package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class RateUsWasShown implements IAnalyticsEvent {

    private final String eventName = "rate_us_was_shown";

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public Bundle getBundle() {
        return new Bundle();
    }

}

