package breakingumbrella.connectit;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

import breakingumbrella.connectit.configuration.GlobalConfig;
import breakingumbrella.connectit.entity.profile.Profile;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

	private final Context context;

	public ApplicationModule(Context context){
		this.context = context;
	}

	/***
	 * Application Context
	 * @return
	 */
	@Provides
	public Context provideContext() {
		return context;
	}

	@Provides
	public FirebaseAnalytics provideFirebaseAnalytics() {
		return FirebaseAnalytics.getInstance(context);
	}

	@Provides
	public Profile provideProfile(GlobalConfig globalConfig) {
		return globalConfig.getProfile();
	}

}
