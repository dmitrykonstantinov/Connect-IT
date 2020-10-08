package breakingumbrella.connectit.domain.gamestate;

import java.lang.reflect.Type;

import javax.inject.Inject;

public class StateResolver {

	@Inject
	public StateResolver(){}

	public void resolveState(Type currentState, Type newState) throws Exception {
		if(newState == currentState) {
			throw new Exception("New state can not be equal current one");
		}
		if (newState != WaitingForEnemyTurn.class && newState != WaitingForPlayerTurn.class) {
			throw new Exception("Wrong state!");
		}
	}
}
