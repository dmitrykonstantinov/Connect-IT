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

import breakingumbrella.connectit.R;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class MenuTutorialDialog extends BlurDialogFragment {

    private OnDialogDismiss callBack;

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
        View v = inflater.inflate(R.layout.dialog_menu_tutorial, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        Button button = v.findViewById(R.id.ok_button);
        button.setOnClickListener(v1 -> {
            MenuTutorialDialog.this.dismiss();
            callBack.onDismiss();
        });
        return v;
    }

    public void showFragmentDialog(Activity activity, OnDialogDismiss callBack) {
        this.callBack = callBack;
        createDialog(activity);
    }

    protected void createDialog(Activity activity) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        android.app.DialogFragment newFragment = this;
        newFragment.setCancelable(false);
        newFragment.show(ft, "dialog");
    }

}
