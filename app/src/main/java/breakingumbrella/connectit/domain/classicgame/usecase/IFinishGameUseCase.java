package breakingumbrella.connectit.domain.classicgame.usecase;

import breakingumbrella.connectit.domain.IUseCase;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseGameFinishPresentation;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public interface IFinishGameUseCase extends IUseCase {

	void surrender(IBaseGameFinishPresentation gameFinishPresentation, IAppErrorHandler appErrorHandler, IApiErrorHandler apiErrorHandler);

}
