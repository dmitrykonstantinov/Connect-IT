package breakingumbrella.connectit.abilities;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;

@SuppressWarnings("ConstantConditions")
public class AbilitiesViewController implements IOnAbilityPutted {

    private int whiteTransparentColor;

    private View abilitiesView;

    private List<Integer> abilities;
    private List<Integer> usedAbilities;

    private IOnAbilityChose onAbilityChosen;

    private int choseImgView;
//	private int chooseAbility;

    private HashMap<Integer, Integer> abilitiesImgIdx = new HashMap<>();


    public AbilitiesViewController(Activity activity) {
        this.abilitiesView = activity.findViewById(R.id.bottom_abilities);
        this.usedAbilities = new ArrayList<>();
        this.whiteTransparentColor = activity.getResources().getColor(R.color.mainWhiteHalfTransparent);
        if (abilitiesView == null) {
            throw new Error("Please provide activity with bottom abilities view included");
        }
    }

    public void showAbilities(List<Integer> abilities, IOnAbilityChose onAbilityChosen) {
        this.abilities = new ArrayList<>(abilities);
        this.onAbilityChosen = onAbilityChosen;

        abilitiesImgIdx.put(0, R.id.abilityImg1);
        abilitiesImgIdx.put(1, R.id.abilityImg2);
        abilitiesImgIdx.put(2, R.id.abilityImg3);
        abilitiesImgIdx.put(3, R.id.abilityImg4);

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            if (i >= abilities.size()) {
                showAbilityBaseOnType(AbilityType.none, abilitiesImgIdx.get(i));
            } else {
                showAbilityBaseOnType(abilities.get(i), abilitiesImgIdx.get(i));
                abilitiesView.findViewById(abilitiesImgIdx.get(i)).setOnClickListener(v -> onAbilityClick(finalI, abilitiesImgIdx.get(finalI)));
            }
        }
    }

    private void onAbilityClick(int imgNumber, int viewImgId) {
        if (usedAbilities.contains(viewImgId)) {
            return;
        }
        onAbilityChosen.onAbilityChose(abilities.get(imgNumber), this);
        //Values below used in callback (onAbilityPutted) that determine the end of figure adding animation
        choseImgView = viewImgId;
        usedAbilities.add(viewImgId);
        //Start abilityClickAnimation
        abilitiesView.findViewById(R.id.abilityBottom).setVisibility(View.GONE);
        for (int i = 0; i < 4; i++) {
            if (abilitiesImgIdx.get(i).equals(viewImgId) == false) {
                abilitiesView.findViewById(abilitiesImgIdx.get(i)).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onAbilityPutted() {
        ((ImageView) abilitiesView.findViewById(choseImgView)).setColorFilter(whiteTransparentColor);
        abilitiesView.findViewById(choseImgView).setOnClickListener(null);

        abilitiesView.findViewById(R.id.abilityBottom).setVisibility(View.VISIBLE);
        for (int i = 0; i < 4; i++) {
            abilitiesView.findViewById(abilitiesImgIdx.get(i)).setVisibility(View.VISIBLE);
        }
        showAbilities(abilities, onAbilityChosen);
    }

    public void showAbilityBaseOnType(int abilityType, int viewImgId) {
        switch (abilityType) {
            case AbilityType.none: {
                ((ImageView) abilitiesView.findViewById(viewImgId)).setImageDrawable(abilitiesView.getResources().getDrawable(R.drawable.ic_not_choosen));
                break;
            }
            case AbilityType.durableFigure: {
                ((ImageView) abilitiesView.findViewById(viewImgId)).setImageDrawable(abilitiesView.getResources().getDrawable(R.drawable.ic_durable_rounded_padded));
                break;
            }
            case AbilityType.invisibleFigure: {
                ((ImageView) abilitiesView.findViewById(viewImgId)).setImageDrawable(abilitiesView.getResources().getDrawable(R.drawable.ic_invisible_rounded_padded));
                break;
            }
            case AbilityType.destroyFigure: {
                ((ImageView) abilitiesView.findViewById(viewImgId)).setImageDrawable(abilitiesView.getResources().getDrawable(R.drawable.ic_destroy_rounded_padded));
                break;
            }
            case AbilityType.secondChance: {
                ((ImageView) abilitiesView.findViewById(viewImgId)).setImageDrawable(abilitiesView.getResources().getDrawable(R.drawable.ic_secondchance_rounded_paddded));
                break;
            }
        }
    }


}
