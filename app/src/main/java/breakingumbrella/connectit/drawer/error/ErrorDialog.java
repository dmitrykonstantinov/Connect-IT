package breakingumbrella.connectit.drawer.error;

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

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.error.IError;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by dem3n on 28.05.2017.
 */

public class ErrorDialog extends BlurDialogFragment {

	private IErrorDialogCallback callBack;
	private IError error;

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
		View v = inflater.inflate(R.layout.dialog_error, container, false);
		getDialog().setCancelable(true);
		Button button = v.findViewById(R.id.ok_button);
		ImageButton buttonClose = v.findViewById(R.id.close_button);
		button.setOnClickListener(v1 -> {
			dismiss();
			callBack.onDialogDismiss();
		});
		buttonClose.setOnClickListener(v12 -> {
			dismiss();
			callBack.onDialogDismiss();
		});
		((TextView)v.findViewById(R.id.lbTitle)).setText("ERROR:" + " " + error.getErrorTitle());
		((TextView)v.findViewById(R.id.lbErrorMessage)).setText("Request: "+  "\n"+ error.getErrorMessage());
		((TextView)v.findViewById(R.id.lbErrorCauseBy)).setText("Header: "+  "\n" + error.getCauseBy());
		return v;
	}

	@Override
	public void onDismiss(final DialogInterface dialog) {
		super.onDismiss(dialog);
		if(getShowsDialog()) {
			callBack.onDialogDismiss();
		}
	}

	public void showFragmentDialog(Activity activity, IErrorDialogCallback callBack, IError error) {
		this.error = error;
		this.callBack = callBack;
		createDialog(activity);
	}

	private void createDialog(Activity activity) {
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		Fragment prev = activity.getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		android.app.DialogFragment newFragment = this;
		newFragment.show(ft, "dialog");
	}
}
