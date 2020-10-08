package breakingumbrella.connectit.presentation.aigamemode;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.classicgame.usecase.IFinishGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.ICreateGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IPutFigureUseCase;
import breakingumbrella.connectit.presentation.ClassicGameBasePresenter;

/**
 * Created by dem3n on 05.04.2017.
 */

@InjectViewState
public class AiGamePresenter extends ClassicGameBasePresenter<IAiGameView> {


	@Inject
	public AiGamePresenter(ICreateGameUseCase createGameUseCase,
						   IPutFigureUseCase putFigureUseCase,
						   IFinishGameUseCase finishGameUseCase) {
		super(createGameUseCase, putFigureUseCase, finishGameUseCase);
	}

	@Override
	public void showEnemyAbility(int ability) {
		getViewState().showEnemyUsedAbility(ability);
	}
}
