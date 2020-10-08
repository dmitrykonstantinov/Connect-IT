package breakingumbrella.connectit.drawer.surrender;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.drawer.BaseDialog;
import breakingumbrella.connectit.drawer.complete.IDrawer;

/**
 * Created by dem3n on 07.05.2017.
 */

public class SurrenderDialogDrawer extends BaseDialog {

	private IDrawer.DrawerCallBack callBack;

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
		View v = inflater.inflate(R.layout.dialog_surrender, container, false);

		if (getDialog() != null && getDialog().getWindow() != null) {
			getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		}
		Button button = v.findViewById(R.id.ok_button);
		button.setOnClickListener(v1 -> {
			SurrenderDialogDrawer.this.dismiss();
			callBack.onOk();
		});
		v.findViewById(R.id.not_ok_btn).setOnClickListener(v12 -> {
			SurrenderDialogDrawer.this.dismiss();
			callBack.onSurrender();
		});
		return v;
	}

	public void showFragmentDialog(Activity activity, IDrawer.DrawerCallBack callBack) {
		this.callBack = callBack;
		super.createDialog(activity);
	}

}
