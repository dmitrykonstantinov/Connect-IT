package breakingumbrella.connectit.presentation.splitscreenmode;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.entity.gamelogic.helpers.GameTypes;
import breakingumbrella.connectit.presentation.ClassicGameBasePresentation;
import breakingumbrella.connectit.presentation.ClassicGameBasePresenter;
import breakingumbrella.connectit.stats.UserPropertiesFirebase;

/**
 * Created by Dmitry Konstantinov on 18.03.2017.
 */

public class SplitScreenGameActivity extends ClassicGameBasePresentation implements ISplitScreenGameView {

	@Inject
	@InjectPresenter
	SplitScreenGamePresenter presenter;

	@ProvidePresenter
	public SplitScreenGamePresenter providePresenter() {
		return presenter;
	}

	@Override
	protected ClassicGameBasePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected GameTypes getGameType() {
		return GameTypes.SplitScreen;
	}

	@Override
	protected boolean isAnimationShouldSwitch() {
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ApplicationExtender.getComponentInjector().getSplitScreenSubComponent(this).inject(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_split_screen_game);
		presenter.createGame();
		analyticsHandler.logUserProperty(UserPropertiesFirebase.USER_PLAYED_SS_GAME, "true");
	}

//	@Override
//	public void updateTimerState(float progress, int secondsRemain) {
//		//Do nothing, cause there are no timer in SS game
//	}

}
