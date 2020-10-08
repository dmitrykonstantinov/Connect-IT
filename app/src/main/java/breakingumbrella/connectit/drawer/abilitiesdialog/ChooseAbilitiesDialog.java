package breakingumbrella.connectit.drawer.abilitiesdialog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.content.ContextCompat;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

public class ChooseAbilitiesDialog extends BlurDialogFragment {

    private ChooseAbilitiesDialogCallback callBack;

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
        View v = inflater.inflate(R.layout.dialog_choose_ability, container, false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        abilityView = v;
        additionalOnCreate();
        return v;
    }

    public void showFragmentDialog(Activity activity, HashMap<String, Integer> abilities, ChooseAbilitiesDialogCallback callBack) {
        this.callBack = callBack;
        this.activity = activity;
        this.abilities = abilities;
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


//	Ability Logic

    View abilityHolder;

    ViewGroup transitionsContainer;

    List<Integer> abilitiesChose;

    int abilityChoseCount;

    HashMap<String, Integer> abilities;

    int activityToLaunch;
    int totalAbilitiesAvailable;


    void additionalOnCreate() {
        abilitiesChose = new ArrayList<>();
        abilityHolder = abilityView.findViewById(R.id.abilityHolder);
        transitionsContainer = activity.findViewById(R.id.activity_main);

        for (Map.Entry<String, Integer> entry : abilities.entrySet()) {
            totalAbilitiesAvailable += entry.getValue();
        }

        setupAllDefault();
        setupAbilities(abilities);

        abilityHolder.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(transitionsContainer);
            if (abilityChoseCount > 0) {

                addAbilityToAvailableList();

                getLastSelectedAbilityImg().setImageDrawable(getResources().getDrawable(R.drawable.ic_empty_ability));

                abilityChoseCount--;
            }
        });

    }

    private void setupAllDefault() {
        ((TextView) this.abilityView.findViewById(R.id.durable_amount_txt)).setVisibility(View.INVISIBLE);
        ((TextView) this.abilityView.findViewById(R.id.invisible_amount_txt)).setVisibility(View.INVISIBLE);
        ((TextView) this.abilityView.findViewById(R.id.destroy_amount_txt)).setVisibility(View.INVISIBLE);
        ((TextView) this.abilityView.findViewById(R.id.second_chance_amount_txt)).setVisibility(View.INVISIBLE);


        setAbilityInactive(this.abilityView.findViewById(R.id.durable_img));
        setAbilityInactive(this.abilityView.findViewById(R.id.invisible_img));
        setAbilityInactive(this.abilityView.findViewById(R.id.destroy_img));
        setAbilityInactive(this.abilityView.findViewById(R.id.second_chance_img));
        //TODO SET ALL ABILITIES UNKNOWN AND ALL COUNTS TO ZERO
    }

    private void addAbilityToAvailableList() {
        if (abilitiesChose.size() > 0) {
            int abilityToDelete = abilitiesChose.get(abilityChoseCount - 1);
            abilitiesChose.remove(abilityChoseCount - 1);
            int currentAmount = abilities.get(String.valueOf(abilityToDelete));
            currentAmount++;
            abilities.put(String.valueOf(abilityToDelete), currentAmount);

            //To Redraw
            setupAbilities(abilities);
        }
    }

    private void setupAbilities(HashMap<String, Integer> abilities) {
        for (Map.Entry<String, Integer> entry : abilities.entrySet()) {
            switch (Integer.parseInt(entry.getKey())) {
                case AbilityType.durableFigure: {
                    handleDurableAbility(entry);
                    break;
                }
                case AbilityType.invisibleFigure: {
                    handleInvisibleAbility(entry);
                    break;
                }
                case AbilityType.destroyFigure: {
                    handleDestroyAbility(entry);
                    break;
                }
                case AbilityType.secondChance: {
                    handleSecondChanceAbility(entry);
                    break;
                }
            }
        }
    }

    private void handleDurableAbility(Map.Entry<String, Integer> rawAbilityInfo) {
        ((TextView) abilityView.findViewById(R.id.durable_amount_txt)).setVisibility(View.VISIBLE);
        TextView abilityAmount = abilityView.findViewById(R.id.durable_amount_txt);
        ((TextView) abilityView.findViewById(R.id.durable_desc_text)).setText(activity.getResources().getString(R.string.durabaleAbilityDesc));
        ((TextView) abilityView.findViewById(R.id.durable_amount_txt)).setText(rawAbilityInfo.getValue().toString());
        ((ImageView) abilityView.findViewById(R.id.durable_img)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_durable_rounded));
        if (rawAbilityInfo.getValue() > 0) {
            setAbilityActive(abilityView.findViewById(R.id.durable_img));
        } else {
            setAbilityInactive(abilityView.findViewById(R.id.durable_img));
        }
        abilityView.findViewById(R.id.durable_img).setOnClickListener
                (
                        v -> onAbilityClick(abilityAmount, rawAbilityInfo, R.id.durable_img, R.drawable.ic_durable_rounded, AbilityType.durableFigure)
                );
    }

    private void handleInvisibleAbility(Map.Entry<String, Integer> rawAbilityInfo) {
        ((TextView) abilityView.findViewById(R.id.invisible_amount_txt)).setVisibility(View.VISIBLE);
        TextView abilityAmount = abilityView.findViewById(R.id.invisible_amount_txt);
        ((TextView) abilityView.findViewById(R.id.invisible_desc_text)).setText(activity.getResources().getString(R.string.invisibleAbilityDesc));
        ((TextView) abilityView.findViewById(R.id.invisible_amount_txt)).setText(rawAbilityInfo.getValue().toString());
        ((ImageView) abilityView.findViewById(R.id.invisible_img)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_invisible_rounded));
        if (rawAbilityInfo.getValue() > 0) {
            setAbilityActive(abilityView.findViewById(R.id.invisible_img));
        } else {
            setAbilityInactive(abilityView.findViewById(R.id.invisible_img));
        }
        abilityView.findViewById(R.id.invisible_img).setOnClickListener
                (
                        v -> onAbilityClick(abilityAmount, rawAbilityInfo, R.id.invisible_img, R.drawable.ic_invisible_rounded, AbilityType.invisibleFigure)
                );
    }

    private void handleDestroyAbility(Map.Entry<String, Integer> rawAbilityInfo) {
        ((TextView) abilityView.findViewById(R.id.destroy_amount_txt)).setVisibility(View.VISIBLE);
        TextView abilityAmount = abilityView.findViewById(R.id.destroy_amount_txt);
        ((TextView) abilityView.findViewById(R.id.destroy_desc_text)).setText(activity.getResources().getString(R.string.destroyAbilityDesc));
        ((TextView) abilityView.findViewById(R.id.destroy_amount_txt)).setText(rawAbilityInfo.getValue().toString());
        ((ImageView) abilityView.findViewById(R.id.destroy_img)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_destroy_rounded));
        if (rawAbilityInfo.getValue() > 0) {
            setAbilityActive(abilityView.findViewById(R.id.destroy_img));
        } else {
            setAbilityInactive(abilityView.findViewById(R.id.destroy_img));
        }
        abilityView.findViewById(R.id.destroy_img).setOnClickListener
                (
                        v -> onAbilityClick(abilityAmount, rawAbilityInfo, R.id.destroy_img, R.drawable.ic_destroy_rounded, AbilityType.destroyFigure)
                );
    }

    private void handleSecondChanceAbility(Map.Entry<String, Integer> rawAbilityInfo) {
        ((TextView) abilityView.findViewById(R.id.second_chance_amount_txt)).setVisibility(View.VISIBLE);
        TextView abilityAmount = abilityView.findViewById(R.id.second_chance_amount_txt);
        ((TextView) abilityView.findViewById(R.id.second_chance_desc_text)).setText(activity.getResources().getString(R.string.secondChanceAbilityDesc));
        ((TextView) abilityView.findViewById(R.id.second_chance_amount_txt)).setText(rawAbilityInfo.getValue().toString());
        ((ImageView) abilityView.findViewById(R.id.second_chance_img)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_secondchance_rounded));
        if (rawAbilityInfo.getValue() > 0) {
            setAbilityActive(abilityView.findViewById(R.id.second_chance_img));
        } else {
            setAbilityInactive(abilityView.findViewById(R.id.second_chance_img));
        }
        abilityView.findViewById(R.id.second_chance_img).setOnClickListener
                (
                        v -> onAbilityClick(abilityAmount, rawAbilityInfo, R.id.second_chance_img, R.drawable.ic_secondchance_rounded, AbilityType.secondChance)
                );
    }

    private void onAbilityClick(TextView abilityAmount, Map.Entry<String, Integer> abilityEntry, int imgViewId, int drawable, int abilityType) {
        TransitionManager.beginDelayedTransition(transitionsContainer);

        int newAmount = abilityEntry.getValue() - 1;
        if (newAmount < 0) {
            return;
        } else if (newAmount == 0) {
            setAbilityInactive(abilityView.findViewById(imgViewId));
        }
        abilities.put(abilityEntry.getKey(), newAmount);
        abilityAmount.setText(String.valueOf(newAmount));

        if (abilityChoseCount < 4) {
            abilitiesChose.add(abilityType);
            getUnselectedAbilityImg().setImageDrawable(getResources().getDrawable(drawable));
            abilityChoseCount++;
        }

        if (abilityChoseCount >= 4 || abilityChoseCount >= totalAbilitiesAvailable) {
            startGame();
        }

    }

    private void startGame() {
        callBack.onAbilitiesChose(abilitiesChose);
        this.dismiss();
    }

    private void setAbilityInactive(ImageView abilityImg) {
        abilityImg.setColorFilter(ContextCompat.getColor(activity, R.color.mainWhiteHalfTransparent), PorterDuff.Mode.SRC_OVER);
    }

    private void setAbilityActive(ImageView abilityImg) {
        abilityImg.clearColorFilter();
    }

    private ImageView getLastSelectedAbilityImg() {
        switch (abilityChoseCount) {
            case 1:
                return abilityHolder.findViewById(R.id.abilityImg1);
            case 2:
                return abilityHolder.findViewById(R.id.abilityImg2);
            case 3:
                return abilityHolder.findViewById(R.id.abilityImg3);
            case 4:
                return abilityHolder.findViewById(R.id.abilityImg4);
        }
        return null;
    }

    private ImageView getUnselectedAbilityImg() {
        switch (abilityChoseCount) {
            case 0:
                return abilityHolder.findViewById(R.id.abilityImg1);
            case 1:
                return abilityHolder.findViewById(R.id.abilityImg2);
            case 2:
                return abilityHolder.findViewById(R.id.abilityImg3);
            case 3:
                return abilityHolder.findViewById(R.id.abilityImg4);
        }
        return null;
    }


}