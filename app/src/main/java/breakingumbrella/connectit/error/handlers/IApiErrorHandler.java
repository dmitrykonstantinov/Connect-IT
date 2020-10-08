package breakingumbrella.connectit.error.handlers;

import breakingumbrella.connectit.error.ApiError;

public interface IApiErrorHandler {
	void handleApiError(ApiError apiError);
}
