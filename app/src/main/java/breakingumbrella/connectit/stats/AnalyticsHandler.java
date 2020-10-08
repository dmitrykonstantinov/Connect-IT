package breakingumbrella.connectit.stats;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

public class AnalyticsHandler {

	private FirebaseAnalytics firebaseAnalytics;

	@Inject
	AnalyticsHandler(FirebaseAnalytics firebaseAnalytics) {
		this.firebaseAnalytics = firebaseAnalytics;
	}

	public void logEvent(IAnalyticsEvent event) {
		firebaseAnalytics.logEvent(event.getEventName(), event.getBundle());
	}

	public void logUserProperty(String userProperty, String value) {
		firebaseAnalytics.setUserProperty(userProperty, value);
	}
}
