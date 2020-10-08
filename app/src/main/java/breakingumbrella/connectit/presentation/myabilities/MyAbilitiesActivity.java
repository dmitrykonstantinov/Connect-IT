package breakingumbrella.connectit.presentation.myabilities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.IViewBase;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;

public class MyAbilitiesActivity extends CustomServiceActivity implements IViewBase {

    @Inject
    GlobalConfig globalConfig;

    @Inject
    IProfileRepository profileRepository;

    int totalAbilitiesAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationExtender.getComponentInjector().getAbilitiesSubComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myabilities);
        HashMap<String, Integer> abilities = globalConfig.getProfile().getProfileFeatures().getAbilities();

        for (Map.Entry<String, Integer> entry : abilities.entrySet()) {
            totalAbilitiesAvailable += entry.getValue();
        }
        setupAllDefault();
        setupAbilities(abilities);
    }

    private void setupAllDefault() {
        ((TextView) this.findViewById(R.id.durable_amount_txt)).setVisibility(View.INVISIBLE);
        ((TextView) this.findViewById(R.id.invisible_amount_txt)).setVisibility(View.INVISIBLE);
        ((TextView) this.findViewById(R.id.destroy_amount_txt)).setVisibility(View.INVISIBLE);
        ((TextView) this.findViewById(R.id.second_chance_amount_txt)).setVisibility(View.INVISIBLE);


        setAbilityInactive(this.findViewById(R.id.durable_img));
        setAbilityInactive(this.findViewById(R.id.invisible_img));
        setAbilityInactive(this.findViewById(R.id.destroy_img));
        setAbilityInactive(this.findViewById(R.id.second_chance_img));
        //TODO SET ALL ABILITIES UNKNOWN AND ALL COUNTS TO ZERO
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
        ((TextView) this.findViewById(R.id.durable_amount_txt)).setVisibility(View.VISIBLE);
        ((TextView) this.findViewById(R.id.durable_amount_txt)).setText(rawAbilityInfo.getValue().toString());
        if (rawAbilityInfo.getValue() > 0) {
            ((TextView) findViewById(R.id.durable_desc_text)).setText(getResources().getString(R.string.durabaleAbilityDesc));
            ((ImageView)this.findViewById(R.id.durable_img)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_durable_rounded));
            setAbilityActive(this.findViewById(R.id.durable_img));
        } else {
            ((TextView) this.findViewById(R.id.durable_desc_text)).setText(getResources().getString(R.string.openSoon));
            setAbilityInactive(this.findViewById(R.id.durable_img));
        }
    }

    private void handleInvisibleAbility(Map.Entry<String, Integer> rawAbilityInfo) {
        ((TextView) this.findViewById(R.id.invisible_amount_txt)).setVisibility(View.VISIBLE);
        ((TextView) this.findViewById(R.id.invisible_amount_txt)).setText(rawAbilityInfo.getValue().toString());
        if (rawAbilityInfo.getValue() > 0) {
            ((TextView) findViewById(R.id.invisible_desc_text)).setText(getResources().getString(R.string.invisibleAbilityDesc));
            ((ImageView)this.findViewById(R.id.invisible_img)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_invisible_rounded));
            setAbilityActive(this.findViewById(R.id.invisible_img));
        } else {
            ((TextView) this.findViewById(R.id.invisible_desc_text)).setText(getResources().getString(R.string.openSoon));
            setAbilityInactive(this.findViewById(R.id.invisible_img));
        }

    }

    private void handleDestroyAbility(Map.Entry<String, Integer> rawAbilityInfo) {
        ((TextView) this.findViewById(R.id.destroy_amount_txt)).setVisibility(View.VISIBLE);
        ((TextView) this.findViewById(R.id.destroy_amount_txt)).setText(rawAbilityInfo.getValue().toString());
        if (rawAbilityInfo.getValue() > 0) {
            ((TextView) findViewById(R.id.destroy_desc_text)).setText(getResources().getString(R.string.destroyAbilityDesc));
            ((ImageView)this.findViewById(R.id.destroy_img)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_destroy_rounded));
            setAbilityActive(this.findViewById(R.id.destroy_img));
        } else {
            ((TextView) this.findViewById(R.id.destroy_desc_text)).setText(getResources().getString(R.string.openSoon));
            setAbilityInactive(this.findViewById(R.id.destroy_img));
        }

    }

    private void handleSecondChanceAbility(Map.Entry<String, Integer> rawAbilityInfo) {
        ((TextView) this.findViewById(R.id.second_chance_amount_txt)).setVisibility(View.VISIBLE);
        ((TextView) this.findViewById(R.id.second_chance_amount_txt)).setText(rawAbilityInfo.getValue().toString());
        if (rawAbilityInfo.getValue() > 0) {
            ((TextView) findViewById(R.id.second_chance_desc_text)).setText(getResources().getString(R.string.secondChanceAbilityDesc));
            ((ImageView)this.findViewById(R.id.second_chance_img)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_secondchance_rounded));
            setAbilityActive(this.findViewById(R.id.second_chance_img));
        } else {
            ((TextView) this.findViewById(R.id.second_chance_desc_text)).setText(getResources().getString(R.string.openSoon));
            setAbilityInactive(this.findViewById(R.id.second_chance_img));
        }

    }

    private void setAbilityInactive(ImageView abilityImg) {
        abilityImg.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_unknown_ability));
    }

    private void setAbilityActive(ImageView abilityImg) {
        abilityImg.clearColorFilter();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.animator.left_to_right_slide, R.animator.right_to_left_slide);
    }

}
