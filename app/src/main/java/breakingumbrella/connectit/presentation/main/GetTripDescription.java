package breakingumbrella.connectit.presentation.main;

import android.app.Activity;

import breakingumbrella.connectit.R;

public class GetTripDescription {

	public TripDescription getDesc(Activity activity, int rewardCode) {
		switch (rewardCode) {
			case 15:
				return new TripDescription("Beginner", activity.getString(R.string.durable_figure_desc));
			case 16:
				return new TripDescription("Intermediate", activity.getString(R.string.invisible_figure_desc));
			case 17:
				return new TripDescription("Advanced", activity.getString(R.string.destroy_figure_desc));
			case 18:
				return new TripDescription("Pro", activity.getString(R.string.second_chance_figure_desc));
			default:
				throw new Error("Can't get Trip description");
		}
	}

}
