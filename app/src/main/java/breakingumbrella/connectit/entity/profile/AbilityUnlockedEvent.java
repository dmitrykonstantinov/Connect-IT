package breakingumbrella.connectit.entity.profile;

public interface AbilityUnlockedEvent {
	public void onAbilityUnlocked(int abilityType, int rewardCode, CampaignPosition newCampaignPosition);
}
