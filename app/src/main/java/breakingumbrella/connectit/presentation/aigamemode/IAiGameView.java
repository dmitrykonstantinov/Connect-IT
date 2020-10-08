package breakingumbrella.connectit.presentation.aigamemode;

import java.util.List;

import breakingumbrella.connectit.presentation.IClassicGamePresentation;

/**
 * Created by dem3n on 05.04.2017.
 */

public interface IAiGameView extends IClassicGamePresentation {
	void showChoseAbilities(List<Integer> abilities);
	void showEnemyUsedAbility(int abilityType);
}
