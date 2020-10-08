package breakingumbrella.connectit;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;

import breakingumbrella.connectit.presentation.main.MainActivity;

/**
 * Created by dem3n on 25.02.2017.
 */

public abstract class CustomServiceActivity extends MvpAppCompatActivity implements IServiceActivity {

//	private final Handler handler = new Handler();
//	private final static int DEFAULT_ACTIVITY_TRANSITION_DELAY = 100;

//	private void startActivityWithDelay(final Intent intent, final Bundle bundle) {
//		CustomServiceActivity.this.runOnUiThread(() -> handler.postDelayed(() -> {
//			if (bundle == null) {
//				startActivity(intent);
//			} else {
//				startActivity(intent, bundle);
//			}
//		}, DEFAULT_ACTIVITY_TRANSITION_DELAY));
//	}

	private void startActivityWithoutDelay(final Intent intent, final Bundle bundle) {
		runOnUiThread(() -> {
			if (bundle == null) {
				startActivity(intent);
			} else {
				startActivity(intent, bundle);
			}
		});

	}

	@Override
	public void transitionToActivity(Intent intent, Context context) {
		runOnUiThread(() -> {
			ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context);
			startActivityWithoutDelay(intent, options.toBundle());
		});


	}

	@Override
	public void moveToMainScreen() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(CustomServiceActivity.this, MainActivity.class);
				transitionToActivity(intent, CustomServiceActivity.this);
			}
		});

	}
}