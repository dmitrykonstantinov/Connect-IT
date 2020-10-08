package breakingumbrella.connectit.presentation.launcher;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.appodeal.ads.Appodeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.crashlytics.FirebaseCrashlytics;


import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.domain.createprofile.ICreateProfilePresentation;
import breakingumbrella.connectit.domain.createprofile.ICreateProfileUseCase;
import breakingumbrella.connectit.IViewBase;
import breakingumbrella.connectit.entity.profile.Profile;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.handlers.IApiErrorHandler;
import breakingumbrella.connectit.presentation.main.MainActivity;
import breakingumbrella.connectit.presentation.signin.FirebaseUserWrapperKt;
import breakingumbrella.connectit.presentation.tutorialmode.TutorialGameActivity;

public class LauncherActivity extends CustomServiceActivity implements
        IViewBase,
        ICreateProfilePresentation,
        IApiErrorHandler {


//    private ProgressBar mProgress;
    private Handler mHandler = new Handler();

//	private Profile profile;

    @Inject
    ICreateProfileUseCase createProfileUseCase;

    @Inject
    GlobalConfig globalConfig;

    @Inject
    IProfileRepository profileRepository;

//	private void useFirebaseUiForTest() {
//		List<AuthUI.IdpConfig> providers = Arrays.asList(
//				new AuthUI.IdpConfig.PhoneBuilder().build());
////				new AuthUI.IdpConfig.GoogleBuilder().build());
//		startActivityForResult(
//				AuthUI.getInstance()
//						.createSignInIntentBuilder()
//						.setAvailableProviders(providers)
//						.setTheme(R.style.AppTheme)
//						.build(),
//				56);
//	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationExtender.getComponentInjector().getLauncherSubComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
//        mProgress = findViewById(R.id.loadingBar);
        setLoadingBarStatus(25);

        handleAnonymousLogin();
    }


    private void handleAnonymousLogin() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInAnonymously().addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                        builder.setDisplayName("You");
                        auth.getCurrentUser().updateProfile(builder.build()).addOnSuccessListener(aVoid -> {
                            Profile profile = FirebaseUserWrapperKt.createProfile(auth.getCurrentUser());
                            profile.setNickName("You");
                            handleProfile(profile);
                        }).addOnFailureListener(e -> {
                            handleApiError(new ApiError(e));
                        });
                    } else {
                        Log.w("RegActivity", "signInAnonymously:failure", task.getException());
                        handleApiError(new ApiError(task.getException() == null ?
                                new Exception("Anonymous login error. Exception is null") :
                                task.getException()));
                    }
                }
        );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void handleProfile(Profile profile) {
        setLoadingBarStatus(75);
        if (globalConfig.isOfflineModeOn()) {
            //TODO Handle No Internet properly
            createProfileUseCase.createLocalProfile(this::onProfileCreated);
        } else {
            createProfileUseCase.createProfile(profile, this::onProfileCreated, this::handleApiError);
        }
    }

    @Override
    public void onProfileCreated() {
        initAdMediator();
        if (globalConfig.isTutorialPassed()) {
            startMainActivity();
        } else {
            startTutorialActivity();
        }
    }

    private void initAdMediator() {
        String appKey = "f9b3c0bab7ec28a8ebbced865d5250008bdc5591d313b0e2";
        //True only for russia
        int adTypes = Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO;
        Appodeal.initialize(this, appKey, adTypes, true);
    }

    private void startTutorialActivity() {
        setLoadingBarStatus(100);
        Intent intent = new Intent(this, TutorialGameActivity.class);
        transitionToActivity(intent, this);
    }

    private void startMainActivity() {
//		startRegistrationActivity();
        setLoadingBarStatus(100);
        Intent intent = new Intent(this, MainActivity.class);
        transitionToActivity(intent, this);
    }

    private void setLoadingBarStatus(final int progress) {
//        mHandler.post(() -> mProgress.setProgress(progress));
    }

    private void showErrorDialog(String message) {
        FirebaseCrashlytics.getInstance().recordException(new Exception(message));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", (dialog, id) -> finish());
        builder.setMessage(message)
                .setTitle(R.string.error_title_txt);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void handleApiError(ApiError apiError) {
        //Cannot use UIApiErrorHandler because it always route to main screen if error occurs.
        //But when you in Launcher Activity some params that necessary for normal Main screen work can be not init
        //So use custom error handler for Launcher activity
        //TODO Add offline mode
        showErrorDialog(apiError.getErrorMessage());
    }

    private void fail(Exception exc11) {
        handleApiError(new ApiError(exc11));
    }
}