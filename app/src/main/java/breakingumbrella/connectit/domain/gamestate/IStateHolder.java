package breakingumbrella.connectit.domain.gamestate;

public interface IStateHolder {

    IGameState getGameState();

    void setGameState(IGameState gameState) throws Exception;

}
