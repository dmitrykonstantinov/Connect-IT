package breakingumbrella.connectit.presentation.aigamemode;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.abilities.AbilitiesViewController;
import breakingumbrella.connectit.abilities.IOnAbilityChose;
import breakingumbrella.connectit.abilities.IOnAbilityPutted;
import breakingumbrella.connectit.entity.gamelogic.helpers.GameTypes;
import breakingumbrella.connectit.entity.gameobjects.AbilityType;
import breakingumbrella.connectit.entity.gameobjects.Figure;
import breakingumbrella.connectit.presentation.ClassicGameBasePresentation;
import breakingumbrella.connectit.presentation.ClassicGameBasePresenter;
import breakingumbrella.connectit.presentation.PresentationUtils;
import breakingumbrella.connectit.stats.UserPropertiesFirebase;

/**
 * Created by dem3n on 25.02.2017.
 */

public class AiGameActivity extends ClassicGameBasePresentation implements IAiGameView, IOnAbilityChose {

	@Inject
	@InjectPresenter
	AiGamePresenter presenter;

	@ProvidePresenter
	public AiGamePresenter providePresenter() {
		return presenter;
	}

	private String aiName = "HAL 9000";
	private String myName = "Игрок";

	protected ClassicGameBasePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected GameTypes getGameType() {
		return GameTypes.Single;
	}

	@Override
	protected boolean isAnimationShouldSwitch() {
		return false;
	}

	private AbilitiesViewController abilitiesViewController;

	private IOnAbilityPutted onAbilityPutted;

	private int currentAbility = AbilityType.none;
	private int enemyUsedAbilityCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ApplicationExtender.getComponentInjector().getAiGameSubComponent(this).inject(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ai_game_mode);

		((TextView) findViewById(R.id.Name_Player_1)).setText(myName);
		((TextView) findViewById(R.id.Name_Player_2)).setText(aiName);

		abilitiesViewController = new AbilitiesViewController(this);

		presenter.createGame();
//		presenter.getChosenAbilities();

		super.analyticsHandler.logUserProperty(UserPropertiesFirebase.USER_PLAYED_AI_GAME, "true");
	}

	@Override
	protected void putFigure(int position) {
		//TODO Check if run_on_ui_thread is needed
		runOnUiThread(() -> {
			sessionStatistics.incrementTurnsPlayed(this);
//				putFigureAnimator.animate(position, this, false);
			int x = PresentationUtils.deLinearize(position, gameField.getSizeY())[0];
			int y = PresentationUtils.deLinearize(position, gameField.getSizeY())[1];
			Figure figure = new Figure();
			figure.setFigure(getPresenter().getCurrentFigure().getFigureType());
			figure.setPosition(x, y);
			figure.setAbilityType(currentAbility);
			if(currentAbility != AbilityType.none) {
				onAbilityPutted.onAbilityPutted();
			}
			currentAbility = AbilityType.none;
			getPresenter().putFigure(figure);
		});
	}

	@Override
	public void showChoseAbilities(List<Integer> abilities) {
		abilitiesViewController.showAbilities(abilities, this::onAbilityChose);
	}

	@Override
	public void onAbilityChose(int ability, IOnAbilityPutted onAbilityPutted) {
		this.currentAbility = ability;
		this.onAbilityPutted = onAbilityPutted;
	}

	@Override
	public void showEnemyUsedAbility(int abilityType) {
		enemyUsedAbilityCount++;
		//TODO handle it latter
//		switch (enemyUsedAbilityCount) {
//			case 1:
//				setAbilityUsed(findViewById(R.id.top_content_right).findViewById(R.id.imgAbility1), abilityType);
//				break;
//			case 2:
//				setAbilityUsed(findViewById(R.id.top_content_right).findViewById(R.id.imgAbility2), abilityType);
//				break;
//			case 3:
//				setAbilityUsed(findViewById(R.id.top_content_right).findViewById(R.id.imgAbility3), abilityType);
//				break;
//			case 4:
//				setAbilityUsed(findViewById(R.id.top_content_right).findViewById(R.id.imgAbility4), abilityType);
//				break;
//		}
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

	private void setAbilityImage(ImageView imageView, Drawable drawable) {
		runOnUiThread(() -> imageView.setImageDrawable(drawable));
	}
}
