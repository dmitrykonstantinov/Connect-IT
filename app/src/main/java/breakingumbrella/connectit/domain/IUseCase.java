package breakingumbrella.connectit.domain;

import breakingumbrella.connectit.error.handlers.IAppErrorHandler;

public interface IUseCase {
	void terminate(IAppErrorHandler appErrorHandler);
}
