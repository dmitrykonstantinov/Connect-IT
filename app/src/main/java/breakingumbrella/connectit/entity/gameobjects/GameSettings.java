package breakingumbrella.connectit.entity.gameobjects;

import breakingumbrella.connectit.entity.profile.CampaignPosition;

public class GameSettings {

	private GameMode gameMode;
	private int playersCount;
	private int targetTurnsNumber;
	private CampaignPosition campaignPosition;


	public CampaignPosition getCampaignPosition() {
		return campaignPosition;
	}

	public void setCampaignPosition(CampaignPosition campaignPosition) {
		this.campaignPosition = campaignPosition;
	}

	public int getTargetTurnsNumber() {
		return targetTurnsNumber;
	}

	public void setTargetTurnsNumber(int targetTurnsNumber) {
		this.targetTurnsNumber = targetTurnsNumber;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}

	public int getPlayersCount() {
		return playersCount;
	}

}
