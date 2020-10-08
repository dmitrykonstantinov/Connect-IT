package breakingumbrella.connectit.domain.gameobjects;

import java.util.HashMap;
import java.util.List;

import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public class TurnResult {
	public TurnResult() {
		isEmpty = true;
	}

	public GameField gameField;
	public HashMap<Integer, Integer> score;
	public Figure figure;
	public List<Figure> positions;
	public boolean isEmpty;
	public boolean isFieldOnly;
}
