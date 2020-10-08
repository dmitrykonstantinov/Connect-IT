package breakingumbrella.connectit.data.repositories.profile;


import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.entity.profile.Profile;

public interface IProfileRepository {

    void isProfileExist(String profileId, OnSuccess<Boolean> onSuccess, OnFailure onFailure);

    void updateProfile(Profile profile, OnSuccess<Void> onSuccess, OnFailure onFailure);

    void getProfile(String profileId, OnSuccess<Profile> onSuccess, OnFailure onFailure);
}