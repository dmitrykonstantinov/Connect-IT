package breakingumbrella.connectit.presentation.testmenu;

import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.IViewBase;
import breakingumbrella.connectit.presentation.splitscreenmode.SplitScreenGameActivity;
import breakingumbrella.connectit.presentation.tutorialmode.TutorialGameActivity;


public class TestMenuActivity extends CustomServiceActivity implements IViewBase {

    private Timer timer = new Timer();
    private final int timerInitialDelay = 500;
    private final int timerTickTime = 5000;

    @Inject
    GlobalConfig globalConfig;

    boolean consentValue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationExtender.getComponentInjector().getMainSubComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);
        setAllOnClickListeners();
    }

    private void setTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    try {
                        Button button = findViewById(R.id.btnNetGame);
                        StateListDrawable drawable = (StateListDrawable) button.getBackground();
                        TransitionDrawable transition = (TransitionDrawable) drawable.getCurrent();
                        transition.startTransition(750);
                    } catch (Exception ex) {
                        turnOffTimer();
                    }
                });
            }
        }, timerInitialDelay, timerTickTime);
    }

    private void turnOffTimer() {
        timer.cancel();
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setTimer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        turnOffTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        turnOffTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        turnOffTimer();
    }


    private void setAllOnClickListeners() {
        (findViewById(R.id.btnSplitScreenGame)).setOnClickListener(this::onSplitGameBtnClick);
        (findViewById(R.id.btnTutorial)).setOnClickListener(this::onTutorialBtnClick);
    }

    private void onSplitGameBtnClick(View v) {
        Intent intent = new Intent(v.getContext(), SplitScreenGameActivity.class);
        transitionToActivity(intent, v.getContext());
    }

    private void onTutorialBtnClick(View v) {
        Intent intent = new Intent(v.getContext(), TutorialGameActivity.class);
        transitionToActivity(intent, v.getContext());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right_slide, R.animator.right_to_left_slide);
    }


}
