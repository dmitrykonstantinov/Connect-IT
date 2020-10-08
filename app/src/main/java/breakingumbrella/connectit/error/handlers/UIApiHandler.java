package breakingumbrella.connectit.error.handlers;

import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import breakingumbrella.connectit.IServiceActivity;
import breakingumbrella.connectit.error.ApiError;
import breakingumbrella.connectit.error.ErrorHolder;

public class UIApiHandler implements IApiErrorHandler {

	private WeakReference<IServiceActivity> contextWeakReference;
	private ErrorHolder errorHolder;

	@Inject
	public UIApiHandler(IServiceActivity serviceActivity, ErrorHolder errorHolder) {
		contextWeakReference = new WeakReference<>(serviceActivity);
		this.errorHolder = errorHolder;
	}

	@Override
	public void handleApiError(ApiError apiError) {
		IServiceActivity serviceActivity = contextWeakReference.get();
		if(serviceActivity == null) {
			throw new Error("Can't resolve error cause of missing Android Context missing WeakReference");
		}
		logError(apiError);
		serviceActivity.moveToMainScreen();
	}

	private void logError(ApiError apiError) {
		FirebaseCrashlytics.getInstance().recordException(apiError);
		Log.e("AppError", apiError.getErrorMessage(), apiError);
		errorHolder.addError(apiError);
	}
}
