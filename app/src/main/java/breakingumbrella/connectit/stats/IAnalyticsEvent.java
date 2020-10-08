package breakingumbrella.connectit.stats;

import android.os.Bundle;

public interface IAnalyticsEvent {

	String getEventName();

	Bundle getBundle();

}
