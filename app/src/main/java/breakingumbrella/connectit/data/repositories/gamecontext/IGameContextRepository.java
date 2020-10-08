package breakingumbrella.connectit.data.repositories.gamecontext;

import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.entity.context.GameContext;


public interface IGameContextRepository {

	void createGCS(GameContext gcs, OnSuccess<GameContext> onSuccess, OnFailure onFailure);

	void getGCSById(String gcsId, OnSuccess<GameContext> onSuccess, OnFailure onFailure);

	void updateGCS(GameContext gcs, OnSuccess<Void> onSuccess, OnFailure onFailure);

}
