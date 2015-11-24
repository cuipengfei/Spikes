package bowling;

public class LastFrame extends Frame {
    private int thirdRoll;

    public LastFrame(int firstRoll, int secondRoll, int thirdRoll) {
        super(firstRoll, secondRoll);
        this.thirdRoll = thirdRoll;
    }

    @Override
    public int countScore() {
        return super.countScore() + thirdRoll;
    }
}
