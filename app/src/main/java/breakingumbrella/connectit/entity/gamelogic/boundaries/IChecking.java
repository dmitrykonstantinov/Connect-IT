package breakingumbrella.connectit.entity.gamelogic.boundaries;

import androidx.annotation.NonNull;

import breakingumbrella.connectit.entity.gameobjects.FieldCheckResult;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public interface IChecking {

	@NonNull
	FieldCheckResult check(GameField gameField);

}
