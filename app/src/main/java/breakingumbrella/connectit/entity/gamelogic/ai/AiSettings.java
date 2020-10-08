package breakingumbrella.connectit.entity.gamelogic.ai;

public class AiSettings {

    int targetDepth;
    int probabilityToRandomMove;

    public AiSettings(int currentTrip) {
        targetDepth = 2;
        int tripSector = getSectorBaseOnTrip(currentTrip);
        probabilityToRandomMove = 100 / (tripSector + 1);

        if (currentTrip == 0) {
            probabilityToRandomMove = 100;
        }
        if (tripSector > 2) {
            targetDepth = 3;
        }
        if (tripSector == 4) {
            probabilityToRandomMove = 0;
        }

    }

    private int getSectorBaseOnTrip(int tripLvl) {
        if (tripLvl < 4) {
            return 1;
        } else if (tripLvl < 8) {
            return 2;
        } else if (tripLvl < 12) {
            return 3;
        } else return 4;
    }


    public int getProbabilityToRandomMove() {
        return probabilityToRandomMove;
    }

    public void setProbabilityToRandomMove(int probabilityToRandomMove) {
        this.probabilityToRandomMove = probabilityToRandomMove;
    }

    public int getTargetDepth() {
        return targetDepth;
    }

    public void setTargetDepth(int targetDepth) {
        this.targetDepth = targetDepth;
    }
}
