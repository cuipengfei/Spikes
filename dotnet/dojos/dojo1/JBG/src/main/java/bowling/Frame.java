package bowling;

public class Frame {
    private int firstRoll;
    private int secondRoll;
    private Frame nextFrame;
    private int ownScore;

    public Frame(int firstRoll, int secondRoll) {
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
        ownScore = firstRoll + secondRoll;
    }

    public int countScore() {
        return ownScore + getBonus();
    }

    private int getBonus() {
        return getNextRoll() + getNextNextRoll();
    }

    private int getNextRoll() {
        if (nextFrame != null) {
            return nextFrame.firstRoll;
        }
        return 0;
    }

    private int getNextNextRoll() {
        int nextNextRoll = 0;

        if (isStrike() && nextFrame != null) {
            if (nextFrame.isStrike()) {
                nextNextRoll = nextFrame.getNextRoll();
            } else {
                nextNextRoll = nextFrame.secondRoll;
            }
        }
        return nextNextRoll;
    }

    private boolean isStrike() {
        return firstRoll == 10;
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
