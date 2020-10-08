package breakingumbrella.connectit.data.repositories.profilegc;

import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.entity.profile.GCProfile;

public interface IGCProfileRepository {

    //GCProfile

    void isGCProfileExist(String gcProfileId, OnSuccess<Boolean> onSuccess, OnFailure onFailure);

    void getGCProfile(String gcProfileId, OnSuccess<GCProfile> onSuccess, OnFailure onFailure);

    void updateGcProfile(GCProfile gcProfile, OnSuccess<Void> onSuccess, OnFailure onFailure);

    void deleteGcProfile(OnSuccess<Void> onSuccess, OnFailure onFailure);

    void flush();

}
