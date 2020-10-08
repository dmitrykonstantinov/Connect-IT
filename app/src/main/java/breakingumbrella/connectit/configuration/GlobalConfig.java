package breakingumbrella.connectit.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

import javax.inject.Inject;
import javax.inject.Singleton;

import breakingumbrella.connectit.entity.profile.Profile;

/**
 * Global config violate some clean architecture rules, especially android Clean one. Here is reference to context in domain layer. Also
 * Global config used by different layers, so be very careful with that class.
 */
@Singleton
public class GlobalConfig {

	private final static int appApiVersion = 3;

	private WeakReference<Context> context;
	private SharedPreferences sharedPreferences;
	private boolean isOfflineModeOn;
	private OfflineModeReason offlineModeReason;
	private Profile profile;

	private boolean isNewAbilityUnlocked;
	private int abilityType;

	public static class Locales {
		public final static String RU = "ru";
		public final static String EN = "en";
	}

	@Inject
	GlobalConfig(Context context) {
		this.context = new WeakReference<>(context);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public int getAppApiVersion() {
		return appApiVersion;
	}

	public String getLocalizedText(int key) {
		return context.get().getString(key);
	}

	public boolean isTutorialPassed() {
		return sharedPreferences.getBoolean("tutorialPassed", false);
	}

	public void setTutorialPassed() {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("tutorialPassed", true);
		editor.apply();
	}

	public boolean isOfflineModeOn() {
		return isOfflineModeOn;
	}

	public void setOfflineModeOn(boolean offlineModeOn) {
		isOfflineModeOn = offlineModeOn;
	}

	public OfflineModeReason getOfflineModeReason() {
		return offlineModeReason;
	}

	public void setOfflineModeReason(OfflineModeReason offlineModeReason) {
		this.offlineModeReason = offlineModeReason;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public boolean isNewAbilityUnlocked() {
		return isNewAbilityUnlocked;
	}

	public void setNewAbilityUnlocked(boolean newAbilityUnlocked) {
		isNewAbilityUnlocked = newAbilityUnlocked;
	}
	
	public int getAbilityType() {
		return abilityType;
	}

	public void setAbilityType(int abilityType) {
		this.abilityType = abilityType;
	}

}
