package bowling;

/**
 * Created by pfcui on 11/24/15.
 */
public class Frame {
    private int firstRoll;
    private int secondRoll;

    public Frame(int firstRoll, int secondRoll) {
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
    }

    public int countScore() {
        return firstRoll+secondRoll;
    }
}
