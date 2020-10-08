package breakingumbrella.connectit.error.handlers;

import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import breakingumbrella.connectit.IServiceActivity;
import breakingumbrella.connectit.error.AppError;
import breakingumbrella.connectit.error.ErrorHolder;

public class UIAppErrorHandler implements IAppErrorHandler {

	private WeakReference<IServiceActivity> contextWeakReference;
	private ErrorHolder errorHolder;

	@Inject
	public UIAppErrorHandler(IServiceActivity serviceActivity, ErrorHolder errorHolder) {
		contextWeakReference = new WeakReference<>(serviceActivity);
		this.errorHolder = errorHolder;
	}

	@Override
	public void handleAppError(AppError appError) {
		IServiceActivity serviceActivity = contextWeakReference.get();
		if(serviceActivity == null) {
			throw new Error("Can't resolve error cause of missing Android Context missing WeakReference");
		}
		logError(appError);
		serviceActivity.moveToMainScreen();
	}

	private void logError(AppError appError) {
		FirebaseCrashlytics.getInstance().recordException(appError);
		Log.e("AppError", appError.getErrorMessage(), appError);
		errorHolder.addError(appError);
	}

}
