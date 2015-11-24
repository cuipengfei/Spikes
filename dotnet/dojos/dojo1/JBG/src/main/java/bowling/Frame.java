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
        if (isSpare()) {
            return ownScore + nextFrame.firstRoll;
        }
        return ownScore;
    }

    private boolean isSpare() {
        return ownScore == 10;
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
