package breakingumbrella.connectit.stats;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import breakingumbrella.connectit.entity.gameobjects.MatchResult;

@Singleton
public class SessionStatistics {

    private int turnsPlayed;
    private boolean wantReward;

    public static final String preferenceKey = "ConnectItTheGame";
    public static final String turnsNumberKey = "turnsNumberKey";

    @Inject
    public SessionStatistics() {

    }

    public boolean isWantReward() {
        boolean returnValue = wantReward;
        wantReward = false;
        return returnValue;
    }

    public void setWantReward(boolean wantReward) {
        this.wantReward = wantReward;
    }

    public void incrementTurnsPlayed(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        turnsPlayed = sharedPref.getInt(turnsNumberKey, 0);

        turnsPlayed++;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(turnsNumberKey, turnsPlayed);
        editor.apply();
    }

    public void reset(Activity activity) {
        turnsPlayed = 0;
        SharedPreferences sharedPref = activity.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(turnsNumberKey, turnsPlayed);
        editor.apply();
    }

    public int getTurnsPlayed() {
        return turnsPlayed;
    }

}
