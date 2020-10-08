package breakingumbrella.connectit.entity.profile;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import breakingumbrella.connectit.entity.gameobjects.AbilityType;

@SuppressWarnings("ConstantConditions")
public class ProfileFeatures implements Serializable {

    public final static int totalTripCount = 16; //Should be the same in CampaignLvls

    private HashMap<String, Integer> abilities;

    public HashMap<String, Integer> getAbilities() {
        return abilities;
    }

    public void setAbilities(HashMap<String, Integer> abilities) {
        this.abilities = abilities;
    }

    @Exclude
    public void unlockAbilities(CampaignPosition campaignPosition) {
        cleanAbilities();
        abilities.put(String.valueOf(AbilityType.durableFigure), 1);
        int tripNumber = campaignPosition.getTrip() - 1;
        if (tripNumber < 0) {
            return;
        }
        if (tripNumber == totalTripCount && campaignPosition.getLvl() == 7) {
            String abilityType = String.valueOf(CampaignLvls.createTrip(new ArrayList<>(), campaignPosition.getTrip()).getRewardType());
            abilities.put(abilityType, (abilities.get(abilityType) + 1));
            return;
        }
        for (int i = 0; i < campaignPosition.getTrip(); i++) {
            String abilityType = String.valueOf(CampaignLvls.createTrip(new ArrayList<>(), i).getRewardType());
            abilities.put(abilityType, (abilities.get(abilityType) + 1));
        }
    }

    @Exclude
    private void cleanAbilities() {
        abilities = new HashMap<>();
        abilities.put(String.valueOf(AbilityType.durableFigure), 0);
        abilities.put(String.valueOf(AbilityType.invisibleFigure), 0);
        abilities.put(String.valueOf(AbilityType.destroyFigure), 0);
        abilities.put(String.valueOf(AbilityType.secondChance), 0);
    }

}
