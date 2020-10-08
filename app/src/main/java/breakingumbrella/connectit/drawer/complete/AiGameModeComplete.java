package breakingumbrella.connectit.drawer.complete;

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
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.domain.gameobjects.MatchRatingResult;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by dem3n on 06.05.2017.
 */

public class AiGameModeComplete extends BlurDialogFragment implements IDrawer {

    private IDrawer.DrawerCallBack callBack;
    private int gameResult;
    private final int win = 1;
    private final int loose = 2;
    private final int tie = 3;

    @Inject
    public AiGameModeComplete() {
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
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        getDialog().setCancelable(true);
        Button surrenderButton = v.findViewById(R.id.ok_button);
        surrenderButton.setOnClickListener(v1 -> {
            dismiss();
        });
        switch (gameResult) {
            case win: {
                ((TextView) v.findViewById(R.id.lbTitle)).setText(getResources().getString(R.string.win_title));
                ((Button) v.findViewById(R.id.ok_button)).setText(getResources().getString(R.string.next_lvl));
                ((TextView) v.findViewById(R.id.lbContent)).setText(getResources().getString(R.string.win_text));
            }
            break;
            case loose: {
                ((TextView) v.findViewById(R.id.lbTitle)).setText(getResources().getString(R.string.loose_title));
                ((TextView) v.findViewById(R.id.lbContent)).setText(getResources().getString(R.string.watch_ad_loose));
                ((Button) v.findViewById(R.id.ok_button)).setText(getResources().getString(R.string.watch_ad_loose));
                v.findViewById(R.id.not_ok_btn).setVisibility(View.VISIBLE);
            }
            break;
            case tie: {
                ((TextView) v.findViewById(R.id.lbTitle)).setText(getResources().getString(R.string.tie_title));
                ((TextView) v.findViewById(R.id.lbContent)).setText(getResources().getString(R.string.tie_ai_text));
                ((Button) v.findViewById(R.id.ok_button)).setText(getResources().getString(R.string.play_again));
            }
            break;
        }
        return v;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        callBack.onSurrender();
    }

    @Override
    public void drawWin(Activity activity, DrawerCallBack callBack, MatchRatingResult elo) {
        gameResult = win;
        this.callBack = callBack;
        showFragmentDialog(activity);
    }

    @Override
    public void drawLose(Activity activity, DrawerCallBack callBack, MatchRatingResult elo) {
        gameResult = loose;
        this.callBack = callBack;
        showFragmentDialog(activity);
    }

    @Override
    public void drawTie(Activity activity, DrawerCallBack callBack, MatchRatingResult elo) {
        gameResult = tie;
        this.callBack = callBack;
        showFragmentDialog(activity);
    }

    private void showFragmentDialog(Activity activity) {
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