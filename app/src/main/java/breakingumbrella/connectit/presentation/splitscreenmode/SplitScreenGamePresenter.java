package breakingumbrella.connectit.presentation.splitscreenmode;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.classicgame.usecase.ICreateGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IFinishGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IPutFigureUseCase;
import breakingumbrella.connectit.presentation.ClassicGameBasePresenter;

/**
 * Created by dem3n on 27.04.2017.
 */

@InjectViewState
public class SplitScreenGamePresenter extends ClassicGameBasePresenter<ISplitScreenGameView> {

	@Inject
	public SplitScreenGamePresenter(ICreateGameUseCase createGameUseCase,
									IPutFigureUseCase putFigureUseCase,
									IFinishGameUseCase finishGameUseCase) {
		super(createGameUseCase, putFigureUseCase, finishGameUseCase);
	}

	@Override
	public void showEnemyAbility(int ability) {

	}
}
