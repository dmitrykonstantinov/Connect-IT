package breakingumbrella.connectit.drawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public abstract class BaseDialog extends BlurDialogFragment {

	protected void createDialog(Activity activity) {
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

}
