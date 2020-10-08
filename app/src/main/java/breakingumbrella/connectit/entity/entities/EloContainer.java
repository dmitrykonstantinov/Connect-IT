package breakingumbrella.connectit.entity.entities;

public class EloContainer {

    private int elo;
    private int lowerBound;
    private int upperBound;
    private int number; //Number of times bounds was expanded
    private int expandAmount; //How much bounds expands every time

    public EloContainer(){}

    public EloContainer(int elo, int expandAmount) {
        this.elo = elo;
        this.expandAmount = expandAmount;
        this.lowerBound = elo - expandAmount;
        this.upperBound = elo + expandAmount;
    }

    public void updateBounds() {
        this.lowerBound = elo - expandAmount * number;
        this.upperBound = elo + expandAmount * number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getExpandAmount() {
        return expandAmount;
    }

    public void setExpandAmount(int expandAmount) {
        this.expandAmount = expandAmount;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}
