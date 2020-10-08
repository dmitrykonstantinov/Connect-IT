package breakingumbrella.connectit.drawer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.core.graphics.drawable.DrawableCompat;

import breakingumbrella.connectit.R;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;
import breakingumbrella.connectit.presentation.PresentationUtils;

/**
 * Created by Dmitry Konstantinov on 14.03.2017.
 */

public class GridDrawer implements IGridDrawer {

    @Inject
    GridDrawer() {
    }

    protected int[][] gameFieldCache = new int[10][10];

    public void draw(GameField gameField, List<Figure> playersFigures, List<Figure> crossedFigures,
                     HashMap<Integer, Integer> currentScore, Activity activity,
                     View.OnTouchListener onFigureTouch) {
        final GridLayout grid = activity.findViewById(R.id.field);
        setScore(activity, currentScore, playersFigures);
        grid.removeAllViews();
        grid.setColumnCount(gameField.getSizeX());
        grid.setRowCount(gameField.getSizeY());
        for (int i = 0; i < gameField.getSizeX(); i++) {
            for (int j = 0; j < gameField.getSizeY(); j++) {
                final ImageView figureVisual = new ImageView(activity);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                Figure figure = gameField.getFigureAtPosition(i, j);

                params.topMargin = displayPointToPixelConverter(6, activity);
                params.rightMargin = displayPointToPixelConverter(6, activity);
                params.bottomMargin = displayPointToPixelConverter(6, activity);
                params.leftMargin = displayPointToPixelConverter(6, activity);

                figureVisual.setLayoutParams(params);
                figureVisual.setScaleType(ImageView.ScaleType.FIT_CENTER);

                setFigureVisual(figureVisual, figure, activity);
                figureVisual.setId(PresentationUtils.linearize(i, j, gameField.getSizeY()));
                figureVisual.setOnTouchListener(onFigureTouch);
                grid.addView(figureVisual);
                grid.invalidate();
            }
        }

        for (Figure figure : crossedFigures) {
            if (figure.isFigureCrossed()) {
                figure.crossFigure(); //If u cross figure second time it will became uncrossed - normal
            }
            ImageView figureVisual = (ImageView) grid.getChildAt(PresentationUtils.linearize(figure.getPositionX(), figure.getPositionY(), gameField.getSizeY()));
            setFigureVisual(figureVisual, figure, activity);
            animateCrossFigures(activity, figureVisual);

            figure.crossFigure();
        }

    }

    protected void setFigureVisual(ImageView figureVisual, Figure figure, Activity activity) {
        switch (figure.getFigureType()) {
            case 0: {
                figureVisual.setBackground(activity.getResources().getDrawable(R.drawable.default_circle));
                break;
            }
            case 1: {
                int oldValue = gameFieldCache[figure.getPositionX()][figure.getPositionY()];
                if (oldValue == 0) {
                    if (figure.getAbilityType() == AbilityType.none || figure.getAbilityType() == AbilityType.secondChance) {
                        drawFigure(activity, figureVisual, R.color.mainYellow);
                    } else {
                        animatePutAbility(activity, figureVisual, figure, R.color.mainYellow, null);
                    }
                } else {
                    if (figure.getAbilityType() == AbilityType.none || figure.getAbilityType() == AbilityType.secondChance) {
                        drawFigure(activity, figureVisual, R.color.mainYellow);
                    } else {
                        drawAbilityFigure(activity, figureVisual, figure, R.color.mainYellow, null);
                    }
                }
                break;
            }
            case 2: {
                int oldValue = gameFieldCache[figure.getPositionX()][figure.getPositionY()];
                if (oldValue == 0) {
                    if (figure.getAbilityType() == AbilityType.none) {
                        drawFigure(activity, figureVisual, R.color.mainBlack);
                    } else {
                        animatePutAbility(activity, figureVisual, figure, R.color.mainBlack, Color.WHITE);
                    }
                } else {
                    if (figure.getAbilityType() == AbilityType.none) {
                        drawFigure(activity, figureVisual, R.color.mainBlack);
                    } else {
                        drawAbilityFigure(activity, figureVisual, figure, R.color.mainBlack, Color.WHITE);
                    }
                }
                break;
            }
            case -1: {
                figureVisual.setBackground(activity.getResources().getDrawable(R.drawable.transparent_circle));
                break;
            }
            case -2: {
                figureVisual.setBackground(activity.getResources().getDrawable(R.drawable.transparent_circle));
                break;
            }
        }
    }

    private void animateCrossFigures(Activity activity, ImageView figureVisual) {
        ValueAnimator anim = ValueAnimator.ofFloat(1, 1.35f);
        anim.setDuration(300);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(animation -> {
            figureVisual.setScaleX((float) animation.getAnimatedValue());
            figureVisual.setScaleY((float) animation.getAnimatedValue());
            figureVisual.invalidate();
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                figureVisual.setBackground(activity.getResources().getDrawable(R.drawable.transparent_circle));
            }
        });
        anim.start();
    }


    private void drawFigure(Activity activity, ImageView figureVisual, int mainColor) {
        GradientDrawable drawable = (GradientDrawable) activity.getResources().getDrawable(R.drawable.default_circle).mutate();
        drawable.setStroke(0, activity.getResources().getColor(R.color.fui_transparent));
        drawable.setColor(activity.getResources().getColor(mainColor));
        figureVisual.setBackground(drawable);
    }

    private void drawAbilityFigure(Activity activity, ImageView figureVisual, Figure figure, int mainColor, Integer drawableColor) {
        LayerDrawable layerDrawable = loadAbilityDrawable(activity, figure);

        GradientDrawable circleDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.circle);
        VectorDrawable abilityDrawable = (VectorDrawable) layerDrawable.findDrawableByLayerId(R.id.ability);

        circleDrawable.setStroke(0, activity.getResources().getColor(R.color.fui_transparent));
        circleDrawable.setColor(activity.getResources().getColor(mainColor));
        if (drawableColor != null) {
            paintDrawable(abilityDrawable, drawableColor);
        }

        figureVisual.setBackground(layerDrawable);
    }

    private void animatePutAbility(Activity activity, ImageView figureVisual, Figure figure, Integer mainColor, Integer drawableColor) {
        LayerDrawable layerDrawable = loadAbilityDrawable(activity, figure);

        GradientDrawable circleDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.circle);
        VectorDrawable abilityDrawable = (VectorDrawable) layerDrawable.findDrawableByLayerId(R.id.ability);

        circleDrawable.setColor(activity.getResources().getColor(mainColor));
        if (drawableColor != null) {
            paintDrawable(abilityDrawable, drawableColor);
        }

        circleDrawable.setStroke(0, activity.getResources().getColor(R.color.fui_transparent));

        gameFieldCache[figure.getPositionX()][figure.getPositionY()] = 1;
        figureVisual.setBackground(layerDrawable);
    }

    private void paintDrawable(Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
    }

    private LayerDrawable loadAbilityDrawable(Activity activity, Figure figure) {
        if (figure.getAbilityType() == AbilityType.durableFigure) {
            return (LayerDrawable) activity.getResources().getDrawable(R.drawable.field_ability_durable).mutate();
        } else if (figure.getAbilityType() == AbilityType.invisibleFigure) {
            return (LayerDrawable) activity.getResources().getDrawable(R.drawable.field_ability_invisible).mutate();
        }
        return null;
    }

    protected int displayPointToPixelConverter(int dp, Activity activity) {
        final float scale = activity.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected void setScore(Activity activity, HashMap<Integer, Integer> currentScore, List<Figure> playersFigures) {
        if (playersFigures.size() == 1) { //Only on tutorial
            ((TextView) activity.findViewById(R.id.Score_Player_1)).setText(String.valueOf(currentScore.get(playersFigures.get(0).getFigureType())));
        }
        if (playersFigures.size() == 2) {
            ((TextView) activity.findViewById(R.id.Score_Player_1)).setText(String.valueOf(currentScore.get(playersFigures.get(0).getFigureType())));
            ((TextView) activity.findViewById(R.id.Score_Player_2)).setText(String.valueOf(currentScore.get(playersFigures.get(1).getFigureType())));
        }

    }

}
