package breakingumbrella.connectit.entity.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.entity.campaign.Level;
import breakingumbrella.connectit.entity.campaign.Trip;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;

public class CampaignLvls implements Serializable {

    public static final int defaultTripsCount = 14;
    public static final int defaultLvlCount = 3;
    public static final int maxLvlToCreate = 7;
    private List<Trip> trips;

    @Inject
    public CampaignLvls() {
        createFromScratch();
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public CampaignPosition setGameIsCompleted(CampaignPosition campaignPosition, AbilityUnlockedEvent abilityUnlockedEvent) {
        int trip = campaignPosition.getTrip();
        int lvl = campaignPosition.getLvl();
        trips.get(trip).getLvls().get(lvl).setCompleted(true);
        if (trips.get(trip).getLvls().size() == lvl + 1) {
            abilityUnlockedEvent.onAbilityUnlocked(trips.get(trip).getRewardType(), trips.get(trip).getRewardCode(), new CampaignPosition(trip + 1, 0));
            lvl = 0;
            trip++;
            return new CampaignPosition(trip, lvl);
        } else {
            lvl++;
            return new CampaignPosition(trip, lvl);
        }

    }

    public CampaignPosition setGameFailed(CampaignPosition campaignPosition) {
        int trip = campaignPosition.getTrip();
        int lvl = campaignPosition.getLvl();
        List<Level> lvls = trips.get(trip).getLvls();
        for (int i = 0; i < lvls.size(); i++) {
            lvls.get(0).setCompleted(false);
        }
        return new CampaignPosition(trip, 0);
    }

    public void setPosition(CampaignPosition campaignPosition) {
        for (int i = 0; i < campaignPosition.getTrip() + 1; i++) {
            if (i != campaignPosition.getTrip()) {
                for (int j = 0; j < trips.get(i).getLvls().size(); j++) {
                    trips.get(i).getLvls().get(j).setCompleted(true);
                }
            } else {
                for (int j = 0; j < campaignPosition.getLvl(); j++) {
                    trips.get(i).getLvls().get(j).setCompleted(true);
                }
            }
        }
    }

    private CampaignPosition createFromScratch() {
        trips = new ArrayList<>();
        for (int tripNumber = 0; tripNumber < defaultTripsCount; tripNumber++) {
            List<Level> lvls = new ArrayList<>();

            int lvsToCreateCount = (defaultLvlCount + tripNumber) / 2;
            if (lvsToCreateCount < 3) {
                lvsToCreateCount = 3;
            }
            if (lvsToCreateCount > maxLvlToCreate) {
                lvsToCreateCount = maxLvlToCreate;
            }

            for (int lvlNumber = 0; lvlNumber < lvsToCreateCount; lvlNumber++) {
                lvls.add(new Level(false));
            }
            trips.add(createTrip(lvls, tripNumber));
        }
        return new CampaignPosition(0, 0);
    }

    public static Trip createTrip(List<Level> lvls, int tripNumber) {
        Trip trip;
        switch (tripNumber) {
            case 0:
                trip = new Trip(
                        lvls,
                        15,
                        AbilityType.durableFigure
                );
                break;
            case 1:
                trip = new Trip(
                        lvls,
                        16,
                        AbilityType.invisibleFigure
                );
                break;
            case 2:
                trip = new Trip(
                        lvls,
                        15,
                        AbilityType.durableFigure
                );
                break;
            case 3:
                trip = new Trip(
                        lvls,
                        16,
                        AbilityType.invisibleFigure
                );
                break;
            case 4:
                trip = new Trip(
                        lvls,
                        17,
                        AbilityType.destroyFigure
                );
                break;
            case 5:
                trip = new Trip(
                        lvls,
                        15,
                        AbilityType.durableFigure
                );
                break;
            case 6:
                trip = new Trip(
                        lvls,
                        16,
                        AbilityType.invisibleFigure
                );
                break;
            case 7:
                trip = new Trip(
                        lvls,
                        18,
                        AbilityType.secondChance
                );
                break;
            case 8:
                trip = new Trip(
                        lvls,
                        17,
                        AbilityType.destroyFigure
                );
                break;
            case 9:
                trip = new Trip(
                        lvls,
                        16,
                        AbilityType.invisibleFigure
                );
                break;
            case 10:
                trip = new Trip(
                        lvls,
                        17,
                        AbilityType.destroyFigure
                );
                break;
            case 11:
                trip = new Trip(
                        lvls,
                        18,
                        AbilityType.secondChance
                );
                break;
            case 12:
                trip = new Trip(
                        lvls,
                        17,
                        AbilityType.destroyFigure
                );
                break;
            case 13:
                trip = new Trip(
                        lvls,
                        18,
                        AbilityType.secondChance
                );
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tripNumber);
        }
        return trip;
    }
}
