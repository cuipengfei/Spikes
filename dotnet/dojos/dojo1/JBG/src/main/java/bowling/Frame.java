package bowling;

/**
 * Created by pfcui on 11/24/15.
 */
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
        int bonus = 0;
        if (isSpare()) {
            bonus = nextFrame.firstRoll;
        }
        if (isStrike()) {
            bonus = nextFrame.firstRoll + nextFrame.secondRoll;
        }
        return bonus;
    }

    private boolean isStrike() {
        return firstRoll == 10;
    }

    private boolean isSpare() {
        return !isStrike() && ownScore == 10;
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
