package breakingumbrella.connectit.entity.entities;

import java.io.Serializable;

public class GameSettingsServer implements Serializable {

	private int playersCount;
	private int boardSizeX;
	private int boardSizeY;
	private int boardLength;
	/*
	It's var that determine how much will be rating bounds increase if no games found in certain amount of time
	 */
	private int searchingRule;
	private int defaultRatingBound;

	public static GameSettingsServer provideDefault() {
		GameSettingsServer gameSettingsServer = new GameSettingsServer();
		gameSettingsServer.playersCount = 2;
		gameSettingsServer.boardSizeX = 10;
		gameSettingsServer.boardSizeY = 10;
		gameSettingsServer.boardLength = gameSettingsServer.boardSizeX * gameSettingsServer.boardSizeY;
		gameSettingsServer.defaultRatingBound = 50;
		gameSettingsServer.searchingRule = 40;
		return gameSettingsServer;
	}

	public int getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}

	public int getBoardSizeX() {
		return boardSizeX;
	}

	public void setBoardSizeX(int boardSizeX) {
		this.boardSizeX = boardSizeX;
	}

	public int getBoardSizeY() {
		return boardSizeY;
	}

	public void setBoardSizeY(int boardSizeY) {
		this.boardSizeY = boardSizeY;
	}

	public int getBoardLength() {
		return boardLength;
	}

	public void setBoardLength(int boardLength) {
		this.boardLength = boardLength;
	}

	public boolean isPlayerCountMatchTarget(int playersCount) {
		return this.playersCount == playersCount;
	}

	public boolean isGameSettingsMatch(GameSettingsServer gameSettingsServer) {
		boolean isMatch = true;
		isMatch &= this.getPlayersCount() == gameSettingsServer.getPlayersCount();
		isMatch &= this.getBoardLength() == gameSettingsServer.getBoardLength();
		isMatch &= this.getBoardSizeX() == gameSettingsServer.getBoardSizeX();
		isMatch &= this.getBoardSizeY() == gameSettingsServer.getBoardSizeY();
		return isMatch;
	}

	public int getSearchingRule() {
		return searchingRule;
	}

	public void setSearchingRule(int searchingRule) {
		this.searchingRule = searchingRule;
	}

	public int getDefaultRatingBound() {
		return defaultRatingBound;
	}

	public void setDefaultRatingBound(int defaultRatingBound) {
		this.defaultRatingBound = defaultRatingBound;
	}

}
