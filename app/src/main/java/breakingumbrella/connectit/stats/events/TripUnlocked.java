package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class TripUnlocked implements IAnalyticsEvent {

    private final String eventName = "trip_unlocked";

    private int tripNumber;

    public TripUnlocked(int tripNumber) {
        this.tripNumber = tripNumber;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();

        bundle.putString("trip_unlocked", String.valueOf(tripNumber));

        return bundle;
    }
}
