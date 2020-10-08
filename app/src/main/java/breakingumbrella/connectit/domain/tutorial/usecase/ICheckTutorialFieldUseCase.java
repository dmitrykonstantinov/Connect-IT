package breakingumbrella.connectit.domain.tutorial.usecase;

import breakingumbrella.connectit.domain.tutorial.presentation.IOnFieldCheckResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public interface ICheckTutorialFieldUseCase {

	void checkField(Figure figure, GameField gameField, IOnFieldCheckResult onFieldCheckResult);

}
