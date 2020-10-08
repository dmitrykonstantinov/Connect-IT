package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class LooseProgress implements IAnalyticsEvent {

    private final String eventName = "progress_was_loose";

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public Bundle getBundle() {
        return new Bundle();
    }
}
