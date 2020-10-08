package breakingumbrella.connectit.data.repositories.profile;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import breakingumbrella.connectit.data.repositories.DataPaths;
import breakingumbrella.connectit.data.repositories.boundaries.OnFailure;
import breakingumbrella.connectit.data.repositories.boundaries.OnSuccess;
import breakingumbrella.connectit.entity.profile.Profile;

public class ProfileRepository implements IProfileRepository {

    private static final String PROFILES_COLLECTION_PATH = "profiles";
    private FirebaseFirestore db;
    private DataPaths dataPaths;

    @Inject
    ProfileRepository(DataPaths dataPaths) {
        db = FirebaseFirestore.getInstance();
        this.dataPaths = dataPaths;
    }

    @Override
    public void isProfileExist(String profileId, OnSuccess<Boolean> onSuccess, OnFailure onFailure) {
        db.collection(PROFILES_COLLECTION_PATH).document(profileId).get()
                .addOnSuccessListener(documentSnapshot -> onSuccess.success(documentSnapshot.exists()))
                .addOnFailureListener(onFailure::fail);
    }

    @Override
    public void updateProfile(Profile profile, OnSuccess<Void> onSuccess, OnFailure onFailure) {
        db.collection(PROFILES_COLLECTION_PATH).document(profile.getId()).set(profile)
                .addOnSuccessListener(aVoid -> onSuccess.success(null))
                .addOnFailureListener(onFailure::fail);
    }

    @Override
    public void getProfile(String profileId, OnSuccess<Profile> onSuccess, OnFailure onFailure) {
        db.collection(PROFILES_COLLECTION_PATH).document(profileId).get()
                .addOnSuccessListener(documentSnapshot -> onSuccess.success(documentSnapshot.toObject(Profile.class)))
                .addOnFailureListener(onFailure::fail);
    }

}
