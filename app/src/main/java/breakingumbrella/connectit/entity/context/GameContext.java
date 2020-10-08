package breakingumbrella.connectit.entity.context;

import java.util.List;

import breakingumbrella.connectit.entity.entities.EloContainer;
import breakingumbrella.connectit.entity.entities.GameState;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;
import breakingumbrella.connectit.entity.gameobjects.GameSettings;
import breakingumbrella.connectit.entity.profile.GCProfile;

/**
 * Game Context Client
 * GameContext provide some addition data and methods to GCS
 */


public class GameContext {

	public GameContext() {}

	public GameContext(GameSettings gameSettings,
					   GameState gameState,
					   List<GCProfile> players,
					   EloContainer eloContainer) {
		this.gameSettings = gameSettings;
		this.gameState = gameState;
		this.players = players;
		this.eloContainer = eloContainer;
	}

	private String id;

	private EloContainer eloContainer;

	private List<GCProfile> players;

	private GameState gameState;

	private GameSettings gameSettings;

	private GameField gameField;

	private List<Figure> turnHistory;

	private int turnNumber;




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GCProfile> getPlayers() {
		return players;
	}

	public void setPlayers(List<GCProfile> players) {
		this.players = players;
	}

	public void addPlayers(List<GCProfile> players) {
		this.players.addAll(players);
	}

	public EloContainer getEloContainer() {
		return eloContainer;
	}

	public void setEloContainer(EloContainer eloContainer) {
		this.eloContainer = eloContainer;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public GameSettings getGameSettings() {
		return gameSettings;
	}

	public void setGameSettings(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}

	public GameField getGameField() {
		return gameField;
	}

	public void setGameField(GameField gameField) {
		this.gameField = gameField;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public void incrementTurnNumber() {
		this.turnNumber++;
	}

	public List<Figure> getTurnHistory() {
		return turnHistory;
	}

	public void setTurnHistory(List<Figure> turnHistory) {
		this.turnHistory = turnHistory;
	}

}
