package breakingumbrella.connectit.data.repositories.profilegc;

import javax.inject.Inject;
import javax.inject.Singleton;

import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.entity.profile.GCProfile;

@Singleton
public class GcProfileLocalRepo implements IGCProfileRepository {

    private GCProfile gcProfile;

    @Inject
    GcProfileLocalRepo() {}

    @Override
    public void isGCProfileExist(String gcProfileId, OnSuccess<Boolean> onSuccess, OnFailure onFailure) {
        if(gcProfile == null) {
            onSuccess.success(false);
        }
        else {
            onSuccess.success(true);
        }
    }

    @Override
    public void getGCProfile(String gcProfileId, OnSuccess<GCProfile> onSuccess, OnFailure onFailure) {
        onSuccess.success(gcProfile);
    }

    @Override
    public void updateGcProfile(GCProfile gcProfile, OnSuccess<Void> onSuccess, OnFailure onFailure) {
        this.gcProfile = gcProfile;
        onSuccess.success(null);
    }

    @Override
    public void deleteGcProfile(OnSuccess<Void> onSuccess, OnFailure onFailure) {
        gcProfile = null;
    }


    @Override
    public void flush() {
        gcProfile = null;
    }
}
