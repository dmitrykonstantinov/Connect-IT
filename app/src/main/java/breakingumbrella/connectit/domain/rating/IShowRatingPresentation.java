package breakingumbrella.connectit.domain.rating;

import java.util.List;

import breakingumbrella.connectit.domain.gameobjects.RatingItem;
import breakingumbrella.connectit.domain.gameobjects.TopPlayers;

public interface IShowRatingPresentation {

	void viewTopThree(TopPlayers topPlayers);
	void viewRatingTable(List<RatingItem> ratingList);

}
