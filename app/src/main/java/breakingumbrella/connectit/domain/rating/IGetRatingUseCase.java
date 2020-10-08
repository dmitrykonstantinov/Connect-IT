package breakingumbrella.connectit.domain.rating;

import breakingumbrella.connectit.error.handlers.IApiErrorHandler;

public interface IGetRatingUseCase {

	void getRating(IShowRatingPresentation showRatingPresentation, IApiErrorHandler apiErrorHandler);

}
