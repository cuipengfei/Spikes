package bowling.fourth;

public class Frame {
    private final int firstRoll;
    private final int secondRoll;

    public Frame(int firstRoll, int secondRoll) {
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
    }

    public int countScore() {
        return firstRoll + secondRoll;
    }
}
