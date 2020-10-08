package breakingumbrella.connectit.presentation.tutorialmode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.abilities.AbilitiesViewController;
import breakingumbrella.connectit.animator.TutorialPutInCellAnimator;
import breakingumbrella.connectit.animator.TutorialScoreAnimator;
import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.domain.campaign.IUpdateProfileUseCase;
import breakingumbrella.connectit.drawer.tutorial.TutorialDialog;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.FigureTypes;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.domain.tutorial.ITutorialStep;
import breakingumbrella.connectit.drawer.IGridDrawer;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.GameField;
import breakingumbrella.connectit.error.AppError;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;
import breakingumbrella.connectit.presentation.PresentationUtils;
import breakingumbrella.connectit.presentation.main.MainActivity;
import breakingumbrella.connectit.stats.AnalyticsHandler;
import breakingumbrella.connectit.stats.IAnalyticsEvent;
import breakingumbrella.connectit.stats.SessionStatistics;
import breakingumbrella.connectit.stats.UserPropertiesFirebase;
import breakingumbrella.connectit.stats.events.TutorialStarted;
import breakingumbrella.connectit.stats.events.UserPassStep1Tutorial;
import breakingumbrella.connectit.stats.events.UserPassStep2Tutorial;
import breakingumbrella.connectit.stats.events.UserPassStep3Tutorial;
import breakingumbrella.connectit.stats.events.UserPassStep4Tutorial;
import breakingumbrella.connectit.stats.events.UserPassStep5Tutorial;
import breakingumbrella.connectit.stats.events.UserPassStep6Tutorial;

public class TutorialGameActivity extends CustomServiceActivity implements ITutorialGameView {

    @Inject
    IGridDrawer gridDrawer;

    @Inject
    TutorialPutInCellAnimator putInCellAnimator;

    @Inject
    TutorialScoreAnimator tutorialScoreAnimator;

    @Inject
    SessionStatistics sessionStatistics;

    @Inject
    AnalyticsHandler analyticsHandler;

    @Inject
    IAppErrorHandler appErrorHandler;

    @Inject
    GlobalConfig globalConfig;

    @Inject
    @InjectPresenter
    TutorialGamePresenter presenter;

    @Inject
    IUpdateProfileUseCase updateProfileUseCase;

    @ProvidePresenter
    public TutorialGamePresenter providePresenter() {
        return presenter;
    }

    private ITutorialStep tutorialStep;

    private GameField gameField;

    private Figure figure;

    protected HashMap<Integer, Integer> score;

    protected ViewGroup transitionsContainer;

    private AbilitiesViewController abilitiesViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationExtender.getComponentInjector().getTutorialSubComponent(this, this).inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        abilitiesViewController = new AbilitiesViewController(this);
        transitionsContainer = findViewById(R.id.activity_main);
        presenter.beginTutorial();
        analyticsHandler.logEvent(new TutorialStarted());
    }


    @Override
    public void showNewStep(ITutorialStep tutorialStep) {
        if (tutorialStep == null) {
            finishTutorial();
            return;
        }
        this.tutorialStep = tutorialStep;
        this.gameField = tutorialStep.getField();
        //Probably postDelay
        presenter.nextAction();
        score = new HashMap<>();
        score.put(1, 0);
    }

    @Override
    public void drawField(TurnResult turnResult) {
        if (turnResult.isEmpty) {
            return;
        }
        runOnUiThread(() -> {
            ArrayList<Figure> figures = new ArrayList<>();
            figures.add(new Figure(1));
            score = turnResult.score;
            gridDrawer.draw(turnResult.gameField,
                    figures,
                    turnResult.positions,
                    turnResult.score,
                    TutorialGameActivity.this,
                    onFigureTouch);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener onFigureTouch = (v, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                putFigure(v.getId());
                return true;
            }
        }
        return false;
    };

    private void putFigure(int position) {
        if (position != PresentationUtils.linearize(tutorialStep.getFigure().getPositionX(),
                tutorialStep.getFigure().getPositionY(), gameField.getSizeY())) {
            return;
        }
        Figure figure = new Figure();
        figure.setFigure(this.figure.getFigureType());
        int x = PresentationUtils.deLinearize(position, gameField.getSizeY())[0];
        int y = PresentationUtils.deLinearize(position, gameField.getSizeY())[1];
        figure.setPosition(x, y);
        gameField.addFigure(figure);
        presenter.checkFigure(figure, gameField);
        new Handler().postDelayed(() -> presenter.nextAction(), 100);
    }

    @Override
    public void onStepCompleted(ITutorialStep tutorialStep) {
        if (tutorialStep == null) {
            finishTutorial();
        } else {
            analyticsHandler.logEvent(getEventBaseOnStepNumber(tutorialStep));
        }
    }

    @Override
    public void showText(String text) {
        TutorialDialog dialog = new TutorialDialog();
        dialog.showFragmentDialog(this, this::onDialogDismiss, text, tutorialStep.getStepName());
    }

    private void onDialogDismiss() {
        presenter.nextAction();
    }

    @Override
    public void showScore(int score) {
        tutorialScoreAnimator.animate(this);
        new Handler().postDelayed(() -> presenter.nextAction(), 1000);
        //Show score animation
    }

    @Override
    public void showAbility() {
        List<Integer> abilities = new ArrayList<>();
        abilities.add(1);
        abilitiesViewController.showAbilities(abilities, (ability, onAbilityPutted) -> TransitionManager.beginDelayedTransition(transitionsContainer));
        ((ImageView) this.findViewById(R.id.abilityImg1)).setImageDrawable(this.getResources().getDrawable(R.drawable.ic_durable_rounded));
        putInCellAnimator.animateAbilityHighlighting(this, this.findViewById(R.id.abilityImg1));
        presenter.nextAction();
    }

    @Override
    public void putFigure(Figure figure) {
        this.figure = figure;
        ArrayList<Figure> figures = new ArrayList<>();
        figures.add(new Figure(FigureTypes.cross));
        gridDrawer.draw(tutorialStep.getField(), figures, new ArrayList<>(), score, TutorialGameActivity.this, onFigureTouch);
        focusOnCell(figure);
    }

    @Override
    public void handleAppError(AppError appError) {
        appErrorHandler.handleAppError(appError);
    }


    private void focusOnCell(Figure figure) {
        putInCellAnimator.animate(this, figure, gameField.getSizeY());
    }

    private void finishTutorial() {
        analyticsHandler.logUserProperty(UserPropertiesFirebase.USER_PASS_TUTORIAL, "true");
        globalConfig.setTutorialPassed();
        unlockDurableAbility();
        updateProfileUseCase.updateProfile(globalConfig.getProfile());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ShowTutorial", true);
        transitionToActivity(intent, this);
    }

    private void unlockDurableAbility() {
        HashMap<String, Integer> abilities = new HashMap<>();
        abilities.put(String.valueOf(AbilityType.durableFigure), 1);
        globalConfig.getProfile().getProfileFeatures().setAbilities(abilities);
        globalConfig.setNewAbilityUnlocked(true);
        globalConfig.setAbilityType(AbilityType.durableFigure);
    }

    private IAnalyticsEvent getEventBaseOnStepNumber(ITutorialStep tutorialStep) {
        IAnalyticsEvent event;
        switch (tutorialStep.getTutorialNumber()) {
            case 0:
                event = new TutorialStarted();
                break;
            case 1:
                event = new UserPassStep1Tutorial(0);
                break;
            case 2:
                event = new UserPassStep2Tutorial(0);
                break;
            case 3:
                event = new UserPassStep3Tutorial(0);
                break;
            case 4:
                event = new UserPassStep4Tutorial(0);
                break;
            case 5:
                event = new UserPassStep5Tutorial(0);
                break;
            case 6:
                event = new UserPassStep6Tutorial(0);
                break;
            default:
                event = new TutorialStarted();
                break;
        }
        return event;
    }


}
