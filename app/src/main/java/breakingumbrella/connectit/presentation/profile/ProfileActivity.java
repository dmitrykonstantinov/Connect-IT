package breakingumbrella.connectit.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.IViewBase;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.entity.profile.CampaignPosition;
import breakingumbrella.connectit.presentation.launcher.LauncherActivity;

public class ProfileActivity extends CustomServiceActivity implements IViewBase {

	@Inject
	GlobalConfig globalConfig;

	@Inject
	IProfileRepository profileRepository;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ApplicationExtender.getComponentInjector().getProfileSubComponent().inject(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		((TextView) findViewById(R.id.nickname_txt)).setText(((TextView) findViewById(R.id.nickname_txt)).getText() + globalConfig.getProfile().getNickName());
		((TextView) findViewById(R.id.is_verified_txt)).setText(((TextView) findViewById(R.id.is_verified_txt)).getText() + globalConfig.getProfile().getVerified().toString());
		findViewById(R.id.btnLogout).setOnClickListener(v -> {
			FirebaseAuth.getInstance().signOut();
			restartApp();
		});
		findViewById(R.id.apply_test_btn).setOnClickListener(v -> {
			int trip = Integer.parseInt(((EditText) findViewById(R.id.trip_et)).getText().toString());
			int lvl = Integer.parseInt(((EditText) findViewById(R.id.lvl_et)).getText().toString());

			globalConfig.getProfile().setCampaignPosition(new CampaignPosition(trip, lvl));
			globalConfig.getProfile().getProfileFeatures().unlockAbilities(globalConfig.getProfile().getCampaignPosition());

			profileRepository.updateProfile(globalConfig.getProfile(), aVoid -> {
				Toast.makeText(this, "Set success", Toast.LENGTH_LONG).show();
			}, exc -> {
				Toast.makeText(this, exc.getMessage(), Toast.LENGTH_LONG).show();
			});
		});
	}

	private void restartApp() {
		//It's not actually restart app. Just start user flow from start
		Intent intent = new Intent(this, LauncherActivity.class);
		transitionToActivity(intent, this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.animator.left_to_right_slide, R.animator.right_to_left_slide);
	}

}
