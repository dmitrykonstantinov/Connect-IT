package breakingumbrella.connectit.entity.entities;

import java.io.Serializable;

import breakingumbrella.connectit.domain.gameobjects.MatchRatingResult;
import breakingumbrella.connectit.entity.gameobjects.MatchResult;

public class GameResult implements Serializable {

    private MatchResult matchResult;

    private MatchRatingResult matchRatingResult;

    public MatchRatingResult getMatchRatingResult() {
        return matchRatingResult;
    }

    public void setMatchRatingResult(MatchRatingResult matchRatingResult) {
        this.matchRatingResult = matchRatingResult;
    }

    public MatchResult getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(MatchResult matchResult) {
        this.matchResult = matchResult;
    }

}
