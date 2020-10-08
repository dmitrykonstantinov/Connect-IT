package breakingumbrella.connectit.drawer.complete;

import android.app.Activity;

import breakingumbrella.connectit.domain.gameobjects.MatchRatingResult;

/**
 * Created by dem3n on 06.05.2017.
 */

public interface IDrawer {

	void drawWin(Activity activity, DrawerCallBack callBack, MatchRatingResult elo);

	void drawLose(Activity activity, DrawerCallBack callBack, MatchRatingResult elo);

	void drawTie(Activity activity, DrawerCallBack callBack, MatchRatingResult elo);

	interface DrawerCallBack {
		void onSurrender();
		void onOk();
		void onRestart();
	}
}
