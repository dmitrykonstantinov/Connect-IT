package breakingumbrella.connectit.domain;

import breakingumbrella.connectit.entity.context.GameContext;
import breakingumbrella.connectit.entity.profile.GCProfile;

public interface IGameContextFactory {

    GameContext create(GCProfile gcProfile);

}
