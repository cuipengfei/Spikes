package bowling.seven;

public class LastFrame extends Frame {
    private final int firstBall;
    private final int secondBall;
    private final int thirdBall;

    public LastFrame(int firstBall, int secondBall, int thirdBall) {
        super(firstBall, secondBall);
        this.firstBall = firstBall;
        this.secondBall = secondBall;
        this.thirdBall = thirdBall;
    }

    @Override
    public int countScore() {
        return firstBall + secondBall + thirdBall;
    }
}
