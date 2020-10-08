package breakingumbrella.connectit.domain.rating;

import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.domain.gameobjects.RatingItem;
import breakingumbrella.connectit.domain.gameobjects.TopPlayers;
import breakingumbrella.connectit.entity.profile.Profile;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;

public class GetRatingUseCase implements IGetRatingUseCase {

//	private IRatingRepository ratingRepository;
	private Profile profile;
	private TopPlayers topPlayers;
	private List<RatingItem> ratingList;
	private IShowRatingPresentation showRatingPresentation;

	@Inject
	GetRatingUseCase(Profile profile) {
		this.profile = profile;
	}

	@Override
	public void getRating(IShowRatingPresentation showRatingPresentation, IApiErrorHandler apiErrorHandler) {
		this.showRatingPresentation = showRatingPresentation;
		if(topPlayers == null || ratingList == null) {
//			ratingRepository.getRating(profile.getGcProfileId(), this::onRatingReceived, apiErrorHandler);
		}
	}

//	@Override
//	public void onRatingReceived(TopPlayers topPlayers, List<RatingItem> ratingTable) {
//		this.topPlayers = topPlayers;
//		this.ratingList = ratingTable;
//		showRatingPresentation.viewTopThree(topPlayers);
//		showRatingPresentation.viewRatingTable(ratingList);
//	}

}
