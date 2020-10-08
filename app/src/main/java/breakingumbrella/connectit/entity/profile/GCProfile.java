package breakingumbrella.connectit.entity.profile;

import java.util.List;

import breakingumbrella.connectit.entity.gameobjects.Figure;

public class GCProfile extends Profile {

    private int score;
    private Figure figure;
    private List<Integer> myAbilities;

    public GCProfile() {}

    public GCProfile(Profile profile, String gcProfileId) {
        this.setId(gcProfileId);
        this.setNickName(profile.getNickName());
        this.setElo(profile.getElo());
    }

    public List<Integer> getMyAbilities() {
        return myAbilities;
    }

    public void setMyAbilities(List<Integer> myAbilities) {
        this.myAbilities = myAbilities;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

}
