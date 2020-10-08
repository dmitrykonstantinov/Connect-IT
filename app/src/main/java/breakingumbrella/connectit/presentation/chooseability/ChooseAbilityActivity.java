//package breakingumbrella.tictactocmobileapp.presentation.chooseability;
//
//import android.content.Intent;
//import android.graphics.PorterDuff;
//import android.os.Bundle;
//import android.transition.TransitionManager;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.inject.Inject;
//
//import androidx.core.content.ContextCompat;
//import breakingumbrella.tictactocmobileapp.ApplicationExtender;
//import breakingumbrella.tictactocmobileapp.CustomServiceActivity;
//import breakingumbrella.tictactocmobileapp.R;
//import breakingumbrella.tictactocmobileapp.configuration.GlobalConfig;
//import breakingumbrella.tictactocmobileapp.data.repositories.profilegc.IGCProfileRepository;
//import breakingumbrella.tictactocmobileapp.entity.gameobjects.AbilityType;
//import breakingumbrella.tictactocmobileapp.presentation.aigamemode.AiGameActivity;
//import breakingumbrella.tictactocmobileapp.presentation.campaign.CampaignActivity;
//
//public class ChooseAbilityActivity extends CustomServiceActivity {
//
//	@Inject
//	GlobalConfig globalConfig;
//
//	@Inject
//	IGCProfileRepository gcProfileRepository;
//
//	View abilityHolder;
//
//	ViewGroup transitionsContainer;
//
//	List<Integer> abilitiesChose;
//
//	int abilityChoseCount;
//
//	HashMap<String, Integer> abilities;
//
//	int activityToLaunch;
//	int totalAbilitiesAvailable;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		ApplicationExtender.getComponentInjector().getChooseAbilitySubComponent().inject(this);
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.dialog_choose_ability);
//		activityToLaunch = getIntent().getExtras().getInt("activityToLaunch");
//		abilitiesChose = new ArrayList<>();
//		abilityHolder = findViewById(R.id.abilityHolder);
//		transitionsContainer = findViewById(R.id.activity_test_menu);
//		abilities = new HashMap<>(globalConfig.getProfile().getProfileFeatures().getAbilities());
//
//		for (Map.Entry<String, Integer> entry : abilities.entrySet()) {
//			totalAbilitiesAvailable += entry.getValue();
//		}
//
//		setupAbilities(abilities);
//
//		abilityHolder.setOnClickListener(v -> {
//			TransitionManager.beginDelayedTransition(transitionsContainer);
//			if (abilityChoseCount >= 0) {
//
//				addAbilityToAvailableList();
//
//				getLastSelectedAbilityImg().setImageDrawable(getResources().getDrawable(R.drawable.ic_empty_ability));
//
//				abilityChoseCount--;
//			}
//		});
//
//	}
//
//	private void addAbilityToAvailableList() {
//		int abilityToDelete = abilitiesChose.get(abilityChoseCount - 1);
//		abilitiesChose.remove(abilityChoseCount - 1);
//		int currentAmount = abilities.get(String.valueOf(abilityToDelete));
//		currentAmount++;
//		abilities.put(String.valueOf(abilityToDelete), currentAmount);
//
//		//To Redraw
//		setupAbilities(abilities);
//	}
//
//	private void setupAbilities(HashMap<String, Integer> abilities) {
//		for (Map.Entry<String, Integer> entry : abilities.entrySet()) {
//			switch (Integer.parseInt(entry.getKey())) {
//				case AbilityType.durableFigure: {
//					handleDurableAbility(entry);
//					break;
//				}
//				case AbilityType.invisibleFigure: {
//					handleInvisibleAbility(entry);
//					break;
//				}
//				case AbilityType.destroyFigure: {
//					handleDestroyAbility(entry);
//					break;
//				}
//				case AbilityType.secondChance: {
//					handleSecondChanceAbility(entry);
//					break;
//				}
//			}
//		}
//	}
//
//	private void handleDurableAbility(Map.Entry<String, Integer> rawAbilityInfo) {
//		TextView abilityAmount = findViewById(R.id.durable_amount_txt);
//		((TextView) findViewById(R.id.durable_amount_txt)).setText(rawAbilityInfo.getValue().toString());
//		if (rawAbilityInfo.getValue() > 0) {
//			setAbilityActive(findViewById(R.id.durable_img));
//		} else {
//			setAbilityInactive(findViewById(R.id.durable_img));
//		}
//		findViewById(R.id.durable_img).setOnClickListener
//				(
//						v -> onAbilityClick(abilityAmount, rawAbilityInfo, R.id.durable_img, R.drawable.ic_durable_rounded, AbilityType.durableFigure)
//				);
//	}
//
//	private void handleInvisibleAbility(Map.Entry<String, Integer> rawAbilityInfo) {
//		TextView abilityAmount = findViewById(R.id.invisible_amount_txt);
//		((TextView) findViewById(R.id.invisible_amount_txt)).setText(rawAbilityInfo.getValue().toString());
//		if (rawAbilityInfo.getValue() > 0) {
//			setAbilityActive(findViewById(R.id.invisible_img));
//		} else {
//			setAbilityInactive(findViewById(R.id.invisible_img));
//		}
//		findViewById(R.id.invisible_img).setOnClickListener
//				(
//						v -> onAbilityClick(abilityAmount, rawAbilityInfo, R.id.invisible_img, R.drawable.ic_invisible_rounded, AbilityType.invisibleFigure)
//				);
//	}
//
//	private void handleDestroyAbility(Map.Entry<String, Integer> rawAbilityInfo) {
//		TextView abilityAmount = findViewById(R.id.destroy_amount_txt);
//		((TextView) findViewById(R.id.destroy_amount_txt)).setText(rawAbilityInfo.getValue().toString());
//		if (rawAbilityInfo.getValue() > 0) {
//			setAbilityActive(findViewById(R.id.destroy_img));
//		} else {
//			setAbilityInactive(findViewById(R.id.destroy_img));
//		}
//		findViewById(R.id.destroy_img).setOnClickListener
//				(
//						v -> onAbilityClick(abilityAmount, rawAbilityInfo, R.id.destroy_img, R.drawable.ic_destroy_rounded, AbilityType.destroyFigure)
//				);
//	}
//
//	private void handleSecondChanceAbility(Map.Entry<String, Integer> rawAbilityInfo) {
//		TextView abilityAmount = findViewById(R.id.second_chance_amount_txt);
//		((TextView) findViewById(R.id.second_chance_amount_txt)).setText(rawAbilityInfo.getValue().toString());
//		if (rawAbilityInfo.getValue() > 0) {
//			setAbilityActive(findViewById(R.id.second_chance_img));
//		} else {
//			setAbilityInactive(findViewById(R.id.second_chance_img));
//		}
//		findViewById(R.id.second_chance_img).setOnClickListener
//				(
//						v -> onAbilityClick(abilityAmount, rawAbilityInfo, R.id.second_chance_img, R.drawable.ic_secondchance_rounded, AbilityType.secondChance)
//				);
//	}
//
//	private void onAbilityClick(TextView abilityAmount, Map.Entry<String, Integer> abilityEntry, int imgViewId, int drawable, int abilityType) {
//		TransitionManager.beginDelayedTransition(transitionsContainer);
//
//		int newAmount = abilityEntry.getValue() - 1;
//		if (newAmount < 0) {
//			return;
//		} else if (newAmount == 0) {
//			setAbilityInactive(findViewById(imgViewId));
//		}
//		abilities.put(abilityEntry.getKey(), newAmount);
//		abilityAmount.setText(String.valueOf(newAmount));
//
//		if (abilityChoseCount < 4) {
//			abilitiesChose.add(abilityType);
//			getUnselectedAbilityImg().setImageDrawable(getResources().getDrawable(drawable));
//			abilityChoseCount++;
//		}
//
//		if (abilityChoseCount >= 4 || abilityChoseCount >= totalAbilitiesAvailable) {
//			startGame();
//		}
//
//	}
//
//	private void startGame() {
//		gcProfileRepository.saveChoseAbilities("1", abilitiesChose,
//				aVoid -> {
//					if (activityToLaunch == 1) {
//						Intent intent = new Intent(this, CampaignActivity.class);
//						transitionActivity(intent, this);
//					} else if (activityToLaunch == 2) {
//						Intent intent = new Intent(this, AiGameActivity.class);
//						transitionActivity(intent, this);
//					}
//				}, exc -> {
//					//TODO
//				});
//	}
//
//	private void setAbilityInactive(ImageView abilityImg) {
//		abilityImg.setColorFilter(ContextCompat.getColor(this, R.color.mainWhiteHalfTransparent), PorterDuff.Mode.SRC_OVER);
//	}
//
//	private void setAbilityActive(ImageView abilityImg) {
//		abilityImg.clearColorFilter();
//	}
//
//	private ImageView getLastSelectedAbilityImg() {
//		switch (abilityChoseCount) {
//			case 1:
//				return abilityHolder.findViewById(R.id.abilityImg1);
//			case 2:
//				return abilityHolder.findViewById(R.id.abilityImg2);
//			case 3:
//				return abilityHolder.findViewById(R.id.abilityImg3);
//			case 4:
//				return abilityHolder.findViewById(R.id.abilityImg4);
//		}
//		return null;
//	}
//
//	private ImageView getUnselectedAbilityImg() {
//		switch (abilityChoseCount) {
//			case 0:
//				return abilityHolder.findViewById(R.id.abilityImg1);
//			case 1:
//				return abilityHolder.findViewById(R.id.abilityImg2);
//			case 2:
//				return abilityHolder.findViewById(R.id.abilityImg3);
//			case 3:
//				return abilityHolder.findViewById(R.id.abilityImg4);
//		}
//		return null;
//	}
//
//}
