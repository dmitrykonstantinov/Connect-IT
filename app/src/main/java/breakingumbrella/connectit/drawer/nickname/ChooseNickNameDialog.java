package breakingumbrella.connectit.drawer.nickname;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.drawer.BaseDialog;

public class ChooseNickNameDialog extends BaseDialog {

    private IOnDialogCompeleted onDialogCompleted;
    private IProfileRepository profileRepository;

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
        View v = inflater.inflate(R.layout.dialog_choose_nickname, container, false);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        v.findViewById(R.id.ok_button).setOnClickListener(this::checkNickName);
        return v;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        //Block dismiss
    }

    private void checkNickName(View v) {
        super.getView().findViewById(R.id.errorTxt).setVisibility(View.GONE);
        super.getView().findViewById(R.id.error_img).setVisibility(View.GONE);
        String nickName = ((EditText)super.getView().findViewById(R.id.nicknameTb)).getText().toString().trim();
        onDialogCompleted.onComplete(nickName);
    }

    public void showFragmentDialog(Activity activity, IOnDialogCompeleted onDialogCompleted, IProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        this.onDialogCompleted = onDialogCompleted;
        super.createDialog(activity);
    }



}
