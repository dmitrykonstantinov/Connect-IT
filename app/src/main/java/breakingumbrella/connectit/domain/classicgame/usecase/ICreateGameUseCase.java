package breakingumbrella.connectit.domain.classicgame.usecase;

import breakingumbrella.connectit.domain.IUseCase;
import breakingumbrella.connectit.domain.classicgame.presentation.IBaseCreateGamePresentation;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public interface ICreateGameUseCase extends IUseCase {
	void createGame(IBaseCreateGamePresentation createGamePresentation, IAppErrorHandler appErrorHandler, IApiErrorHandler apiErrorHandler);
}
