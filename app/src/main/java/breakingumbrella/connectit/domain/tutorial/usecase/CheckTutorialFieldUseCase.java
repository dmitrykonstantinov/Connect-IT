package breakingumbrella.connectit.domain.tutorial.usecase;

import java.util.HashMap;

import javax.inject.Inject;

import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.domain.tutorial.presentation.IOnFieldCheckResult;
import breakingumbrella.connectit.entity.gamelogic.boundaries.IChecking;
import breakingumbrella.connectit.entity.gamelogic.checking.Checking;
import breakingumbrella.connectit.entity.gameobjects.FieldCheckResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public class CheckTutorialFieldUseCase implements ICheckTutorialFieldUseCase {

	@Inject
	CheckTutorialFieldUseCase() {
	}

	@Override
	public void checkField(Figure playerFigure, GameField gameField, IOnFieldCheckResult onFieldCheckResult) {
		IChecking checking = new Checking();
		Figure enemyFigure = new Figure();
		enemyFigure.setFigure(FigureTypes.circle);
		FieldCheckResult fieldCheckResult = checking.check(gameField);

		TurnResult turnResult = new TurnResult();
		turnResult.gameField = gameField;
		turnResult.score = new HashMap<>();
		turnResult.score.put(fieldCheckResult.figureType, fieldCheckResult.score);
		turnResult.figure = playerFigure;
		turnResult.positions = fieldCheckResult.fieldChanges;
		turnResult.isEmpty = false;

		onFieldCheckResult.onFieldCheckResult(turnResult);
	}

}
