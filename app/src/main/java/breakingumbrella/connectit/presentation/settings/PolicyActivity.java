package breakingumbrella.connectit.presentation.settings;

import android.os.Bundle;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.IViewBase;
import breakingumbrella.connectit.R;

public class PolicyActivity extends CustomServiceActivity implements IViewBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationExtender.getComponentInjector().getSettingsSubComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right_slide, R.animator.right_to_left_slide);
    }

}
