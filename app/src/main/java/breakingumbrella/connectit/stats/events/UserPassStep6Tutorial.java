package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class UserPassStep6Tutorial implements IAnalyticsEvent {

    private final String eventName = "user_pass_step_6";
    private int timeSpend;

    public UserPassStep6Tutorial(int timeSpend) {
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
