package breakingumbrella.connectit.drawer.newability;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class NewAbilityDialog extends BlurDialogFragment {

	private NewAbilityDialogCallback callBack;

	private Activity activity;
	private View abilityView;


	//Dialog utility code
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
		getDialog().setCanceledOnTouchOutside(false);
		View v = inflater.inflate(R.layout.dialog_new_ability, container, false);
		getDialog().setCancelable(true);
		if (getDialog() != null && getDialog().getWindow() != null) {
			getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		}
		abilityView = v;
		additionalOnCreate();
		v.findViewById(R.id.ok_button).setOnClickListener(v1 -> {
			getDialog().dismiss();
			callBack.onUserOk();
		}); //It's temporary
		return v;
	}

	@Override
	public void onDismiss(final DialogInterface dialog) {
		super.onDismiss(dialog);
		if (getShowsDialog()) {
			dismiss();
		}
	}

	public void showFragmentDialog(Activity activity, Integer ability, NewAbilityDialogCallback callBack) {
		this.callBack = callBack;
		this.activity = activity;
		this.ability = ability;
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
		newFragment.setCancelable(false);
		newFragment.show(ft, "dialog");
	}
	//Dialog utility code

	private Integer ability;

	private void additionalOnCreate() {
		switch (ability) {
			case AbilityType.durableFigure:
				showForDurable();
				break;
			case AbilityType.invisibleFigure:
				showForInvisible();
				break;
			case AbilityType.destroyFigure:
				showForDestroy();
				break;
			case AbilityType.secondChance:
				showForSecondChance();
				break;
		}
	}

	private void showForDurable() {
		((ImageView) abilityView.findViewById(R.id.abilityImg)).setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_durable_rounded));
		((TextView) abilityView.findViewById(R.id.abilityDescTxt)).setText(R.string.durabaleAbilityDesc);
	}

	private void showForInvisible() {
		((ImageView) abilityView.findViewById(R.id.abilityImg)).setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_invisible_rounded));
		((TextView) abilityView.findViewById(R.id.abilityDescTxt)).setText(R.string.invisibleAbilityDesc);
	}

	private void showForDestroy() {
		((ImageView) abilityView.findViewById(R.id.abilityImg)).setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_destroy_rounded));
		((TextView) abilityView.findViewById(R.id.abilityDescTxt)).setText(R.string.destroyAbilityDesc);
	}

	private void showForSecondChance() {
		((ImageView) abilityView.findViewById(R.id.abilityImg)).setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_secondchance_rounded));
		((TextView) abilityView.findViewById(R.id.abilityDescTxt)).setText(R.string.secondChanceAbilityDesc);
	}


}
