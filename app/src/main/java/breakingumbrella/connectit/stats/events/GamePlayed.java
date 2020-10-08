package breakingumbrella.connectit.stats.events;

import android.os.Bundle;

import javax.annotation.Nullable;

import breakingumbrella.connectit.entity.gamelogic.helpers.GameTypes;
import breakingumbrella.connectit.entity.gameobjects.MatchResult;
import breakingumbrella.connectit.stats.IAnalyticsEvent;

public class GamePlayed implements IAnalyticsEvent {

    private final String eventName = "game_played";

    private String gameResult;
    private Integer turnsCount;

    public GamePlayed(MatchResult matchResult, int turnsCount) {
        this.gameResult = getStringGameResult(matchResult);
        this.turnsCount = turnsCount;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();

        bundle.putString("game_result", gameResult);
        bundle.putInt("turns_count", turnsCount);

        return bundle;
    }

    private String getStringGameResult(MatchResult matchResult) {
        String strMatchResult;
        switch (matchResult) {
            case Win:
                strMatchResult = "win";
                break;
            case Tie:
                strMatchResult = "tie";
                break;
            case Lose:
                strMatchResult = "lose";
                break;
            default:
                strMatchResult = "Undefined";
                break;
        }
        return strMatchResult;
    }

}
