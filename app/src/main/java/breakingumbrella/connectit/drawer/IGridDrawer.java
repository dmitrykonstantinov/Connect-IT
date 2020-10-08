package breakingumbrella.connectit.drawer;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;

public interface IGridDrawer {
	void draw(GameField gameField,  List<Figure> playersFigures, List<Figure> crossedFigures,
			  HashMap<Integer, Integer> currentScore, Activity activity,
			  View.OnTouchListener onFigureTouch);
}
