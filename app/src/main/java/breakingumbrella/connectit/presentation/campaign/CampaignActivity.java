package breakingumbrella.connectit.presentation.campaign;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.abilities.AbilitiesViewController;
import breakingumbrella.connectit.abilities.IOnAbilityChose;
import breakingumbrella.connectit.abilities.IOnAbilityPutted;
import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.domain.campaign.IUpdateProfileUseCase;
import breakingumbrella.connectit.drawer.abilitiesdialog.ChooseAbilitiesDialog;
import breakingumbrella.connectit.drawer.canloose.CanLooseDialog;
import breakingumbrella.connectit.drawer.complete.IDrawer;
import breakingumbrella.connectit.drawer.surrender.SurrenderDialogDrawer;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.gamelogic.helpers.GameTypes;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.entity.gameobjects.MatchResult;
import breakingumbrella.connectit.entity.profile.CampaignLvls;
import breakingumbrella.connectit.presentation.ClassicGameBasePresentation;
import breakingumbrella.connectit.presentation.ClassicGameBasePresenter;
import breakingumbrella.connectit.presentation.PresentationUtils;
import breakingumbrella.connectit.stats.UserPropertiesFirebase;
import breakingumbrella.connectit.stats.events.GameAborted;
import breakingumbrella.connectit.stats.events.LooseProgress;
import breakingumbrella.connectit.stats.events.TripUnlocked;

public class CampaignActivity extends ClassicGameBasePresentation implements ICampaignView, IOnAbilityChose {

    @Inject
    GlobalConfig globalConfig;

    @Inject
    IUpdateProfileUseCase updateProfileUseCase;

    @Inject
    @InjectPresenter
    CampaignPresenter presenter;

    @Inject
    CampaignLvls campaignLvls;

    @ProvidePresenter
    public CampaignPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected ClassicGameBasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected GameTypes getGameType() {
        return GameTypes.Campaign;
    }

    @Override
    protected boolean isAnimationShouldSwitch() {
        return false;
    }

    private AbilitiesViewController abilitiesViewController;
    private IOnAbilityPutted onAbilityPutted;

    private List<Integer> abilities;
    private int currentAbility = AbilityType.none;
    private int enemyUsedAbilityCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationExtender.getComponentInjector().getCampaignSubComponent(this).inject(this);
        setContentView(R.layout.activity_ai_game_mode);
        super.onCreate(savedInstanceState);

        abilitiesViewController = new AbilitiesViewController(this);

        presenter.createGame();
//		presenter.getChosenAbilities();

        analyticsHandler.logUserProperty(UserPropertiesFirebase.USER_PLAYED_CAMPAIGN_GAME, "true");

        ChooseAbilitiesDialog chooseAbilities = new ChooseAbilitiesDialog();

        chooseAbilities.showFragmentDialog(this, new HashMap<>(globalConfig.getProfile().getProfileFeatures().getAbilities()), abilities -> {
            CampaignActivity.this.abilities = abilities;
            abilitiesViewController.showAbilities(abilities, CampaignActivity.this::onAbilityChose);
        });
    }

    @Override
    protected void putFigure(int position) {
        //TODO Check if run_on_ui_thread is needed
        runOnUiThread(() -> {
            sessionStatistics.incrementTurnsPlayed(CampaignActivity.this);
            int x = PresentationUtils.deLinearize(position, gameField.getSizeY())[0];
            int y = PresentationUtils.deLinearize(position, gameField.getSizeY())[1];
            Figure figure = new Figure();
            figure.setFigure(getPresenter().getCurrentFigure().getFigureType());
            figure.setPosition(x, y);
            figure.setAbilityType(currentAbility);
            if (currentAbility != AbilityType.none) {
                onAbilityPutted.onAbilityPutted();
            }
            currentAbility = AbilityType.none;
            getPresenter().putFigure(figure);
        });

    }

    @Override
    public void onAbilityChose(int ability, IOnAbilityPutted onAbilityPutted) {
        TransitionManager.beginDelayedTransition(transitionsContainer);
        this.currentAbility = ability;
        this.onAbilityPutted = onAbilityPutted;
    }


    @Override
    public void showEnemyUsedAbility(int abilityType) {
        enemyUsedAbilityCount++;
        //TODO Think about it
    }

    @Override
    public void setGameInfoVisual(GameContext gameContext) {
        super.setGameInfoVisual(gameContext);
        presenter.forceFieldCheck();
    }

    @Override
    public void drawWin() {
        globalConfig.getProfile().setCampaignPosition(campaignLvls.setGameIsCompleted(globalConfig.getProfile().getCampaignPosition(),
                (abilityType, rewardCode, newCampaignPosition) -> {
                    globalConfig.setNewAbilityUnlocked(true);
                    globalConfig.setAbilityType(abilityType);
                    analyticsHandler.logEvent(new TripUnlocked(newCampaignPosition.getTrip()));
                }));
        globalConfig.getProfile().getProfileFeatures().unlockAbilities(globalConfig.getProfile().getCampaignPosition());
        updateProfileUseCase.updateProfile(globalConfig.getProfile());
        super.drawWin();
    }

    @Override
    public void drawTie() {
        super.drawTie();
    }

    @Override
    public void drawLose() {
        CanLooseDialog canLooseDialog = new CanLooseDialog();
        canLooseDialog.showFragmentDialog(this, willWatchAd -> {
            if (willWatchAd) {
                sessionStatistics.setWantReward(true);
                finishGame();
            } else {
                globalConfig.getProfile().setCampaignPosition(campaignLvls.setGameFailed(globalConfig.getProfile().getCampaignPosition()));
                updateProfileUseCase.updateProfile(globalConfig.getProfile());
                analyticsHandler.logEvent(new LooseProgress());
                finishGame();
            }
        });
    }

    private void setAbilityUsed(ImageView imageView, int abilityType) {
        switch (abilityType) {
            case AbilityType.durableFigure:
                setAbilityImage(imageView, getResources().getDrawable(R.drawable.ic_durable));
                break;
            case AbilityType.invisibleFigure:
                setAbilityImage(imageView, getResources().getDrawable(R.drawable.ic_invisible_rounded));
                break;
            case AbilityType.destroyFigure:
                setAbilityImage(imageView, getResources().getDrawable(R.drawable.ic_destroy));
                break;
            case AbilityType.secondChance:
                setAbilityImage(imageView, getResources().getDrawable(R.drawable.ic_second_chance));
                break;
        }
    }


    private void areYouSureSurrender() {
        SurrenderDialogDrawer surrenderDialogDrawer = new SurrenderDialogDrawer();
        surrenderDialogDrawer.showFragmentDialog(this, new IDrawer.DrawerCallBack() {
            @Override
            public void onSurrender() {
                analyticsHandler.logEvent(new GameAborted(-1));
                drawLose();
            }

            @Override
            public void onOk() {
                //Do nothing
            }

            @Override
            public void onRestart() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        areYouSureSurrender();
    }

    private void setAbilityImage(ImageView imageView, Drawable drawable) {
        runOnUiThread(() -> imageView.setImageDrawable(drawable));
    }

}
