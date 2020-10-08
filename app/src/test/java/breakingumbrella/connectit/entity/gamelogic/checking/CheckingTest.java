package breakingumbrella.connectit.entity.gamelogic.checking;

import org.junit.Test;

import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.entity.gamelogic.boundaries.IChecking;
import breakingumbrella.connectit.entity.gameobjects.FieldCheckResult;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

import static org.junit.Assert.*;

public class CheckingTest {

	@Test
	public void rightDiagonal_OXXX() {

		IChecking checking = new Checking();
		GameField gameField = new GameField(8, 8);

		Figure figure1 = new Figure(FigureTypes.circle);
		figure1.setPosition(5, 1);
		Figure figure2 = new Figure(FigureTypes.cross);
		figure2.setPosition(4, 2);
		Figure figure3 = new Figure(FigureTypes.cross);
		figure3.setPosition(3, 3);
		Figure figure4 = new Figure(FigureTypes.cross);
		figure4.setPosition(2, 4);
		gameField.addFigure(figure1);
		gameField.addFigure(figure2);
		gameField.addFigure(figure3);
		gameField.addFigure(figure4);

		FieldCheckResult fieldCheckResult = checking.check(gameField);
		assertEquals(FigureTypes.cross, fieldCheckResult.figureType);
		assertEquals(4, fieldCheckResult.score);
		assertTrue(gameField.getFigureAtPosition(3, 3).isFigureCrossed());
		assertTrue(gameField.getFigureAtPosition(2, 4).isFigureCrossed());
	}
}