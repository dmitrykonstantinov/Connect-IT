package breakingumbrella.connectit.drawer.complete;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import javax.inject.Inject;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.domain.gameobjects.MatchRatingResult;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by dem3n on 06.05.2017.
 */

public class SplitScreenGameComplete extends BlurDialogFragment implements IDrawer {

	private static IDrawer.DrawerCallBack callBack;
	private static int gameResult;
	private static final int win = 1;
	private static final int loose = 2;
	private static final int tie = 3;

	@Inject
	public SplitScreenGameComplete() {}

	static SplitScreenGameComplete newInstance(IDrawer.DrawerCallBack call_Back) {
		if(call_Back == null)
			throw new Error();
		SplitScreenGameComplete f = new SplitScreenGameComplete();
		callBack = call_Back;
		return f;
	}

	@Override
	protected float getDownScaleFactor() {
		return 3f;
	}

	@Override
	protected int getBlurRadius() {
		return 5;
	}

	@Override
	protected boolean isActionBarBlurred() {
		return true;
	}

	@Override
	protected boolean isDimmingEnable() {
		return true;
	}

	@Override
	protected boolean isRenderScriptEnable() {
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_complete, container, false);
		getDialog().setCancelable(true);
		Button button = v.findViewById(R.id.ok_button);
		ImageButton buttonClose = v.findViewById(R.id.close_button);
		button.setOnClickListener(v1 -> callBack.onRestart());
		buttonClose.setOnClickListener(v12 -> callBack.onSurrender());
		switch (gameResult) {
			case win: {
				((TextView) v.findViewById(R.id.lbTitle)).setText(getResources().getString(R.string.win_title));
				((TextView) v.findViewById(R.id.lbContent)).setText(" ");
			}
			break;
			case loose: {
				((TextView) v.findViewById(R.id.lbTitle)).setText(getResources().getString(R.string.loose_title));
				((TextView) v.findViewById(R.id.lbContent)).setText(" ");
			}
			break;
			case tie: {
				((TextView) v.findViewById(R.id.lbTitle)).setText(getResources().getString(R.string.tie_title));
				((TextView) v.findViewById(R.id.lbContent)).setText(" ");
			}
			break;
		}
		return v;
	}

	@Override
	public void onDismiss(final DialogInterface dialog) {
		super.onDismiss(dialog);
		if(getShowsDialog()) {
			dismiss();
			callBack.onSurrender();
		}
	}

	@Override
	public void drawWin(Activity activity, DrawerCallBack callBack, MatchRatingResult elo) {
		gameResult = win;
		showFragmentDialog(activity, callBack);
	}

	@Override
	public void drawLose(Activity activity, DrawerCallBack callBack, MatchRatingResult elo) {
		gameResult = loose;
		showFragmentDialog(activity, callBack);
	}

	@Override
	public void drawTie(Activity activity, DrawerCallBack callBack, MatchRatingResult elo) {
		gameResult = tie;
		showFragmentDialog(activity, callBack);
	}

	private void showFragmentDialog(Activity activity, DrawerCallBack call_Back) {
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		Fragment prev = activity.getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		android.app.DialogFragment newFragment = SplitScreenGameComplete.newInstance(call_Back);
		newFragment.show(ft, "dialog");
	}
}
