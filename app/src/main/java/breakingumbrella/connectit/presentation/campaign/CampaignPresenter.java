package breakingumbrella.connectit.presentation.campaign;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.classicgame.usecase.ICreateGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IFinishGameUseCase;
import breakingumbrella.connectit.domain.classicgame.usecase.IPutFigureUseCase;
import breakingumbrella.connectit.domain.gameobjects.TurnResult;
import breakingumbrella.connectit.presentation.ClassicGameBasePresenter;

@InjectViewState
public class CampaignPresenter extends ClassicGameBasePresenter<ICampaignView> {

	@Inject
	public CampaignPresenter(ICreateGameUseCase createGameUseCase,
							 IPutFigureUseCase putFigureUseCase,
							 IFinishGameUseCase finishGameUseCase) {
		super(createGameUseCase, putFigureUseCase, finishGameUseCase);
	}

	public void forceFieldCheck() {
		TurnResult fieldCheckResult = putFigureUseCase.forceFieldCheck();
		getViewState().drawFieldChange(fieldCheckResult);
	}


	@Override
	public void showEnemyAbility(int ability) {
		getViewState().showEnemyUsedAbility(ability);
	}


}
