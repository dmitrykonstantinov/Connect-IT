package breakingumbrella.connectit.domain.campaign;

import breakingumbrella.connectit.error.handlers.IApiErrorHandler;

public interface IGetCampaignInfoUseCase {

	void getCampaign(IGetCampaignLevelsPresentation presentation);

	void getCampaignPosition(IGetCampaignPosition presentation, IApiErrorHandler apiErrorHandler);

}
