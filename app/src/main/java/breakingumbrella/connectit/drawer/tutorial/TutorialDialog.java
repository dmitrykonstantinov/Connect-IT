package breakingumbrella.connectit.drawer.tutorial;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import breakingumbrella.connectit.R;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class TutorialDialog extends BlurDialogFragment {

	private OnDialogDismiss callBack;
	private String text;
	private String title;

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
	public void onStart() {
		getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
		super.onStart();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_step_info, container, false);
		if (getDialog() != null && getDialog().getWindow() != null) {
			getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		}
		((TextView) v.findViewById(R.id.lbContent)).setText(text);
		((TextView) v.findViewById(R.id.lbTitle)).setText(title);

		Button button = v.findViewById(R.id.ok_button);
		button.setOnClickListener(v1 -> {
			TutorialDialog.this.dismiss();
			callBack.onDismiss();
		});
		return v;
	}

	public void showFragmentDialog(Activity activity, OnDialogDismiss call_Back, String text, String title) {
		this.callBack = call_Back;
		this.text = text;
		this.title = title;
		createDialog(activity);
	}

	protected void createDialog(Activity activity) {
		FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
		android.app.DialogFragment newFragment = this;
		newFragment.setCancelable(false);
		newFragment.show(ft, "dialog");
	}

}
