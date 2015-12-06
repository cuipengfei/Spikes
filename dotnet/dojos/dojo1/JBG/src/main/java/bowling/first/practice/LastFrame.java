package bowling.first.practice;

public class LastFrame extends Frame {
    private int firstRoll;
    private int secondRoll;
    private int thirdRoll;

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
