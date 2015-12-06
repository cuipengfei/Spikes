package bowling.third.practice;

public class LastFrame extends Frame {
    private final int firstRoll;
    private final int secondRoll;
    private final int thirdRoll;

    public LastFrame(int firstRoll, int secondRoll, int thirdRoll) {
        super(firstRoll, secondRoll);
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
        this.thirdRoll = thirdRoll;
    }

    @Override
    public int countScore() {
        return firstRoll + secondRoll + thirdRoll;
    }
}
