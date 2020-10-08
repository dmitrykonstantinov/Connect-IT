package breakingumbrella.connectit.domain.gamestate;


import javax.inject.Inject;

public class StateHolder implements IStateHolder {

	private IGameState gameState;
	private StateResolver stateResolver;

	@Inject
	public StateHolder(StateResolver stateResolver) {
		this.stateResolver = stateResolver;
	}

	@Override
	public IGameState getGameState() {
		return gameState;
	}

	@Override
	public void setGameState(IGameState gameState) throws Exception {
		synchronized (this) {
			if(this.gameState != null) {
				stateResolver.resolveState(this.gameState.getClass(), gameState.getClass());
				this.gameState.terminate();
			}
			this.gameState = gameState;
		}
	}

}
