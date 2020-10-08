package breakingumbrella.connectit.domain.classicgame.middleware;

import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.data.repositories.gamecontext.IGameContextRepository;
import breakingumbrella.connectit.domain.IGameContextFactory;
import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.profile.GCProfile;

public class GetGameContext {

	public void get(GCProfile gcProfile, IGameContextRepository gameContextRepository, IGameContextFactory gameContextFactory,
					OnSuccess<GameContext> onSuccess, OnFailure<Exception> onFailure) {
		gameContextRepository.createGCS(
				gameContextFactory.create(gcProfile),
				onSuccess::success,
				onFailure::fail);
	}

}
