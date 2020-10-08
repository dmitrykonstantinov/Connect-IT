package breakingumbrella.connectit.ad;

import android.app.Activity;

import javax.inject.Inject;
import javax.inject.Singleton;

import breakingumbrella.connectit.entity.gameobjects.MatchResult;
import breakingumbrella.connectit.stats.SessionStatistics;

/**
 * For now i will show AD to users only after win or after 100 turns played to not be noisy with ADs
 */

@Singleton
public class AdHandler {

    private SessionStatistics sessionStatistics;
    private ShowReason showReason;

    @Inject
    AdHandler(SessionStatistics sessionStatistics) {
        this.sessionStatistics = sessionStatistics;
    }


    public ShowReason getShowReason() {
        return showReason;
    }

    //Add will be showed when move to main screen
    public ShowType requestAd() {
        ShowType showType = ShowType.none;
        if (sessionStatistics.isWantReward()) {
            showType = ShowType.rewardedVideo;
            showReason = ShowReason.loose_lvl;
        } else if (sessionStatistics.getTurnsPlayed() > 40) {
            showType = ShowType.interstitial;
            showReason = ShowReason.win_and_turns_played;
        }
        return showType;
    }

    public void adWasShown(Activity activity) {
        sessionStatistics.reset(activity);
        showReason = null;
    }

}

