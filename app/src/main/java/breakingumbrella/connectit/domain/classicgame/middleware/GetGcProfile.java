package breakingumbrella.connectit.domain.classicgame.middleware;

import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.data.repositories.profilegc.IGCProfileRepository;
import breakingumbrella.connectit.entity.profile.GCProfile;
import breakingumbrella.connectit.entity.profile.Profile;

public class GetGcProfile {

    public void getGcProfile(Profile profile, IGCProfileRepository gcProfileRepository,
                              OnSuccess<GCProfile> onSuccess, OnFailure<Exception> onFailure) {
        gcProfileRepository.isGCProfileExist(profile.getId(), isExist -> {
            if (isExist) {
                gcProfileRepository.getGCProfile(profile.getId(),
                        onSuccess::success,
                        onFailure::fail);
            } else {
                createGcProfile(profile.getId(), profile, gcProfileRepository, onSuccess, onFailure);
            }
        }, onFailure::fail);
    }

    private void createGcProfile(String gcProfileId, Profile profile, IGCProfileRepository gcProfileRepository,
                                 OnSuccess<GCProfile> onSuccess, OnFailure<Exception> onFailure) {
        GCProfile gcProfile = new GCProfile(profile, gcProfileId);
        gcProfileRepository.updateGcProfile(gcProfile,
                aVoid -> onSuccess.success(gcProfile),
                onFailure::fail);
    }

}
