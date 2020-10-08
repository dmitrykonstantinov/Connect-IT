package breakingumbrella.connectit.stats;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.presentation.main.MainActivity;

public class RateUsHandler {

	public static final String preferenceKey = "ConnectItTheGame";
	public static final String rateUsShownNumberKey = "rateUsShownNumberKey";

	@Inject
	RateUsHandler() { }

	public boolean shouldShow(MainActivity mainActivity, GlobalConfig globalConfig) {
		SharedPreferences sharedPref = mainActivity.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
		boolean isShown = sharedPref.getBoolean(rateUsShownNumberKey, false);
		if (isShown) {
			return false;
		}
		return globalConfig.getProfile().getCampaignPosition().getTrip() != 0;
	}

	public void setShown(MainActivity mainActivity) {
		SharedPreferences sharedPref = mainActivity.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean(rateUsShownNumberKey, true);
		editor.apply();
	}

}
