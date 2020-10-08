package breakingumbrella.connectit.domain.gamestate;

import java.lang.reflect.Type;

public interface IStateChanger {
	void changeState(Type stateType);
}
