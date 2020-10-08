package breakingumbrella.connectit.presentation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.domain.gameobjects.MatchRatingResult;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.drawer.IGridDrawer;
import breakingumbrella.connectit.drawer.complete.IDrawer;
import breakingumbrella.connectit.entity.context.GCGameWrapperKt;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.gamelogic.helpers.GameTypes;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;
import breakingumbrella.connectit.entity.gameobjects.GameSettings;
import breakingumbrella.connectit.entity.gameobjects.MatchResult;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.AppError;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;
import breakingumbrella.connectit.stats.AnalyticsHandler;
import breakingumbrella.connectit.stats.SessionStatistics;
import breakingumbrella.connectit.stats.events.GameAborted;
import breakingumbrella.connectit.stats.events.GamePlayed;

public abstract class ClassicGameBasePresentation extends CustomServiceActivity implements IClassicGamePresentation {

    @Inject
    protected IDrawer completeScreenDrawer;

    @Inject
    protected IGridDrawer gridDrawer;

    @Inject
    protected IAppErrorHandler appErrorHandler;

    @Inject
    protected SessionStatistics sessionStatistics;

    @Inject
    protected AnalyticsHandler analyticsHandler;

    protected abstract ClassicGameBasePresenter getPresenter();

    protected abstract GameTypes getGameType();

    protected abstract boolean isAnimationShouldSwitch();

    protected ViewGroup transitionsContainer;

    protected GameSettings gameSettings;
    protected GameField gameField;
    protected HashMap<Integer, Integer> score;
    protected int turnNumber;
    protected List<Figure> playersFigure;

    @SuppressLint("ClickableViewAccessibility")
    protected final View.OnTouchListener onFigureTouch = (View v, MotionEvent event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                putFigure(v.getId());
                return true;
            }
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transitionsContainer = findViewById(R.id.activity_main);
        score = new HashMap<>();
        score.put(1, 0);
        score.put(2, 0);
        score.put(3, 0);
        score.put(4, 0);
    }

    protected void putFigure(int position) {
        runOnUiThread(() -> {
            int x = PresentationUtils.deLinearize(position, gameField.getSizeY())[0];
            int y = PresentationUtils.deLinearize(position, gameField.getSizeY())[1];
            Figure figure = new Figure();
            figure.setFigure(getPresenter().getCurrentFigure().getFigureType());
            figure.setPosition(x, y);
            getPresenter().putFigure(figure);
        });
    }

    @Override
    public void drawLose() {
        runOnUiThread(() -> {
            completeScreenDrawer.drawLose(this, onDrawerResult, new MatchRatingResult(0, 0, 0));
        });
    }

    @Override
    public void drawWin() {
        runOnUiThread(() -> {
            logStatistic(turnNumber, MatchResult.Win);
            completeScreenDrawer.drawWin(this, onDrawerResult, new MatchRatingResult(0, 0, 0));
        });
    }

    @Override
    public void drawTie() {
        runOnUiThread(() -> {
            logStatistic(turnNumber, MatchResult.Tie);
            completeScreenDrawer.drawTie(this, onDrawerResult, new MatchRatingResult(0, 0, 0));
        });
    }

    @Override
    public void setGameInfoVisual(GameContext gameContext) {
        runOnUiThread(() -> {
            gameSettings = gameContext.getGameSettings();
            gameField = gameContext.getGameField();
            score = new HashMap<>();
            if (gameContext.getPlayers().size() == 2) {
                ((TextView) findViewById(R.id.Name_Player_1)).setText(gameContext.getPlayers().get(0).getNickName());
//				((TextView) findViewById(R.id.Name_Player_2)).setText(gameContext.getPlayers().get(1).getNickName());
            } else {
                throw new Error("It can't be not 2");
            }
            playersFigure = GCGameWrapperKt.getFiguresList(gameContext);
            score.put(playersFigure.get(0).getFigureType(), 0);
            score.put(playersFigure.get(1).getFigureType(), 0);
            gridDrawer.draw(gameField, playersFigure, new ArrayList<>(), score, this, onFigureTouch);
        });
    }

    private final IDrawer.DrawerCallBack onDrawerResult = new IDrawer.DrawerCallBack() {
        @Override
        public void onSurrender() {
            ClassicGameBasePresentation.this.finishGame();
        }

        @Override
        public void onOk() {
            ClassicGameBasePresentation.this.finishGame();
        }

        @Override
        public void onRestart() {
        }
    };

    @Override
    public void drawTurn(final TurnResult turnResult) {
        runOnUiThread(() -> {
            if (turnResult.isEmpty) {
                return;
            }
            this.gameField = turnResult.gameField;
            this.score = turnResult.score;
            sessionStatistics.incrementTurnsPlayed(this);
            switchPlayersVisual();
            turnNumber++;
            gridDrawer.draw(turnResult.gameField, playersFigure, turnResult.positions, turnResult.score, this,
                    onFigureTouch);
        });
    }

    @Override
    public void drawFieldChange(final TurnResult turnResult) {
        this.gameField = turnResult.gameField;
        this.score = turnResult.score;
        gridDrawer.draw(turnResult.gameField, playersFigure, new ArrayList<>(), turnResult.score, this,
                onFigureTouch);
    }

    protected void switchPlayersVisual() {
        runOnUiThread(() -> {
            if (isAnimationShouldSwitch()) {
                if (turnNumber % 2 == 0) {
                    switchPlayerRightTransition();
                } else {
                    switchPlayerLeftTransition();
                }
            } else {
                if (turnNumber % 2 == 0) {
                    switchPlayerLeftTransition();
                } else {
                    switchPlayerRightTransition();
                }
            }
        });
    }

    protected void switchPlayerRightTransition() {
        TransitionManager.beginDelayedTransition(transitionsContainer);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) transitionsContainer);
        constraintSet.connect(R.id.turnLineImg, ConstraintSet.START, R.id.Name_Player_1, ConstraintSet.START, 0);
        constraintSet.connect(R.id.turnLineImg, ConstraintSet.END, R.id.Name_Player_1, ConstraintSet.END, 0);
        constraintSet.connect(R.id.turnLineImg, ConstraintSet.TOP, R.id.Name_Player_1, ConstraintSet.BOTTOM, 4);
        constraintSet.applyTo((ConstraintLayout) transitionsContainer);
    }

    protected void switchPlayerLeftTransition() {
        TransitionManager.beginDelayedTransition(transitionsContainer);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) transitionsContainer);
        constraintSet.connect(R.id.turnLineImg, ConstraintSet.START, R.id.Name_Player_2, ConstraintSet.START, 0);
        constraintSet.connect(R.id.turnLineImg, ConstraintSet.END, R.id.Name_Player_2, ConstraintSet.END, 0);
        constraintSet.connect(R.id.turnLineImg, ConstraintSet.TOP, R.id.Name_Player_2, ConstraintSet.BOTTOM, 4);
        constraintSet.applyTo((ConstraintLayout) transitionsContainer);
    }

    private void logStatistic(int turnNumber, MatchResult matchResult) {
        GamePlayed gamePlayed = new GamePlayed(
                matchResult,
                turnNumber
        );
        analyticsHandler.logEvent(gamePlayed);
    }

    @Override
    public void finishGame() {
        getPresenter().finish();
        moveToMainScreen();
    }

    @Override
    public void handleAppError(AppError appError) {
        appErrorHandler.handleAppError(appError);
    }

    @Override
    public void handleApiError(ApiError apiError) {

    }

}
