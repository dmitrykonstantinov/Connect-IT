package breakingumbrella.connectit.presentation.registration;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import breakingumbrella.connectit.ApplicationExtender;
import breakingumbrella.connectit.CustomServiceActivity;
import breakingumbrella.connectit.IViewBase;
import breakingumbrella.connectit.R;
import breakingumbrella.connectit.data.repositories.profile.IProfileRepository;
import breakingumbrella.connectit.drawer.error.EmailIsAlreadyUsedDialog;
import breakingumbrella.connectit.drawer.nickname.ChooseNickNameDialog;
import breakingumbrella.connectit.error.AppError;

public class RegistrationActivity extends CustomServiceActivity implements IViewBase {

	private FirebaseAuth auth;

	@Inject
	IProfileRepository profileRepository;

	ViewGroup transitionsContainer;

	/*
	 * 1. Start e-mail registration
	 * 2. Waiting for e-mail verified
	 * 3. Choose nickname
	 * */

	private enum ViewState {
		startEmailRegistration,
		waitingForEmailVerified,
		chooseNickName
	}

	private void setViewState(ViewState viewState) {
		switch (viewState) {
			case startEmailRegistration:
				switchToStartEmailRegistration();
				break;
			case waitingForEmailVerified:
				switchToWaitingForEmailVerified();
				break;
			case chooseNickName:
				switchToChooseNickName();
				break;
		}
	}

//	GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//			.requestIdToken(getString(R.string.default_web_client_id))
//			.requestEmail()
//			.build();

	private static final int RC_SIGN_IN = 56;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ApplicationExtender.getComponentInjector().getRegistrationSubComponent().inject(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		auth = FirebaseAuth.getInstance();
		transitionsContainer = findViewById(R.id.activity_main);
		findViewById(R.id.register_btn).setOnClickListener(v -> registerWithEmailLogin());
		findViewById(R.id.register_later).setOnClickListener(v -> handleAnonymousLogin());
		findViewById(R.id.login_btn).setOnClickListener(v -> loginWithEmail());
		findViewById(R.id.regByGoogleBtn).setOnClickListener(v -> handleGoogleLogin());
		findViewById(R.id.regByPhoneBtn).setOnClickListener(v -> handlePhoneLogin());
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


		if (currentUser != null) {
			for (UserInfo user : currentUser.getProviderData()) {
				if (user.getProviderId().equals("phone")) {
					System.out.println("User is signed in with phone");
					switchToChooseNickName();
				}
			}
			for (UserInfo user : currentUser.getProviderData()) {
				if (user.getProviderId().equals("password")) {
					setViewState(ViewState.waitingForEmailVerified);
					checkUserIsVerified();
				} else {
					setViewState(ViewState.startEmailRegistration);
				}
			}
		}
	}

	private void switchToStartEmailRegistration() {
		TransitionManager.beginDelayedTransition(transitionsContainer);
		findViewById(R.id.register_btn).setVisibility(View.VISIBLE);
		findViewById(R.id.email).setVisibility(View.VISIBLE);
		findViewById(R.id.password).setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.registrationContent)).setText(R.string.registration_content);
	}

	private void switchToWaitingForEmailVerified() {
		TransitionManager.beginDelayedTransition(transitionsContainer);
		findViewById(R.id.register_btn).setVisibility(View.GONE);
		findViewById(R.id.email).setVisibility(View.GONE);
		findViewById(R.id.password).setVisibility(View.GONE);
	}

	private void switchToChooseNickName() {
		//TODO
		showChooseNickNameDialog();
	}

	private void handlePhoneLogin() {
		List<AuthUI.IdpConfig> providers = Arrays.asList(
				new AuthUI.IdpConfig.PhoneBuilder().build());
		startActivityForResult(
				AuthUI.getInstance()
						.createSignInIntentBuilder()
						.setAvailableProviders(providers)
						.setTheme(R.style.AppTheme)
						.build(),
				RC_SIGN_IN);
	}

	private void handleGoogleLogin() {
//		Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//		startActivityForResult(signInIntent, RC_SIGN_IN);
	}

	private void handleAnonymousLogin() {
		auth.signInAnonymously()
				.addOnCompleteListener(this, task -> {
					if (task.isSuccessful()) {
						showChooseNickNameDialog();
					} else {
						Log.w("RegActivity", "signInAnonymously:failure", task.getException());
						Toast.makeText(RegistrationActivity.this, "Authentication failed.",
								Toast.LENGTH_SHORT).show();
						//TODO Add ApiErrorHandling
					}
				});
	}


	private void loginWithEmail() {
		String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
		if (isEmailValid(email) == false) {
			Toast.makeText(RegistrationActivity.this, "E-MAIL is not valid!",
					Toast.LENGTH_SHORT).show();
		}
		String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();

		auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
			if (task.isSuccessful()) {
				setViewState(ViewState.waitingForEmailVerified);
				checkUserIsVerified();
			} else {
				if (task.getException() instanceof FirebaseAuthUserCollisionException) {
					EmailIsAlreadyUsedDialog errorDialog = new EmailIsAlreadyUsedDialog();
					errorDialog.showFragmentDialog(this, () -> setViewState(ViewState.startEmailRegistration), new AppError(task.getException()));
				} else {
					Log.w("RegActivity", "createUserWithEmail:failure", task.getException());
					Toast.makeText(RegistrationActivity.this, "Authentication failed." + " " + task.getException(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void registerWithEmailLogin() {
		String email = ((EditText) findViewById(R.id.email)).getText().toString().trim();
		if (isEmailValid(email) == false) {
			Toast.makeText(RegistrationActivity.this, "E-MAIL is not valid!",
					Toast.LENGTH_SHORT).show();
		}
		String password = ((EditText) findViewById(R.id.password)).getText().toString().trim();
		//TODO Check for whitespaces
		auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
			if (task.isSuccessful()) {
				setViewState(ViewState.waitingForEmailVerified);
				checkUserIsVerified();
			} else {
				if (task.getException() instanceof FirebaseAuthUserCollisionException) {
					EmailIsAlreadyUsedDialog errorDialog = new EmailIsAlreadyUsedDialog();
					errorDialog.showFragmentDialog(this, () -> setViewState(ViewState.startEmailRegistration), new AppError(task.getException()));
				} else {
					Log.w("RegActivity", "createUserWithEmail:failure", task.getException());
					Toast.makeText(RegistrationActivity.this, "Authentication failed." + " " + task.getException(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void checkUserIsVerified() {
		auth.getCurrentUser().reload().addOnCompleteListener(task -> {
			if (auth.getCurrentUser().isEmailVerified()) {
				switchToChooseNickName();
			} else {
				sendEmailConfirmation(auth.getCurrentUser());
			}
		});
	}

	private void sendEmailConfirmation(FirebaseUser firebaseUser) {
		firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
			// Re-enable button
			if (task.isSuccessful()) {
				//E-MAIL send successfully
				TransitionManager.beginDelayedTransition(transitionsContainer);
				((TextView) findViewById(R.id.registrationContent)).setText("We send you link to e-mail address. Please follow click on it to verify your e-mail");
				((Button) findViewById(R.id.register_later)).setText("I followed the link and verified my e-mail");
				findViewById(R.id.register_later).setOnClickListener(view -> checkUserIsVerified());
			} else {
				if (task.getException() instanceof FirebaseTooManyRequestsException) {
					setViewState(ViewState.waitingForEmailVerified);
				} else {
					Log.e("Email Conformation", "sendEmailVerification", task.getException());
					Toast.makeText(this,
							"Failed to send verification email.",
							Toast.LENGTH_SHORT).show();
					setViewState(ViewState.startEmailRegistration);
				}
			}
		});
	}

	private void showChooseNickNameDialog() {
		ChooseNickNameDialog chooseNickNameDialog = new ChooseNickNameDialog();
		chooseNickNameDialog.showFragmentDialog(this,
				nickname -> {
					UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
					builder.setDisplayName(nickname);
					auth.getCurrentUser().updateProfile(builder.build()).addOnSuccessListener(aVoid -> {
						finishActivity();
					}).addOnFailureListener(e -> {
						//TODO
					});
				},
				profileRepository);
	}

	private void finishActivity() {
		setResult(RESULT_OK);
		finish();
	}

	boolean isEmailValid(CharSequence email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
		if (requestCode == RC_SIGN_IN) {
			// Successfully signed in
			if (resultCode == RESULT_OK) {
				switchToChooseNickName();
			} else {
				switchToStartEmailRegistration();
			}
		}
	}

}

