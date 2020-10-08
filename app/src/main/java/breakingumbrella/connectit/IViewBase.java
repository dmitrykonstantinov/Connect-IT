package breakingumbrella.connectit;

import com.arellomobile.mvp.MvpView;

/**
 * Created by dem3n on 05.04.2017.
 */

public interface IViewBase extends MvpView {
	void onBackPressed();
	void runOnUiThread(Runnable method);

}
