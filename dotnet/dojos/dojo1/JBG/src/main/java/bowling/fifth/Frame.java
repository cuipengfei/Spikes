package bowling.fifth;

public class Frame {
    private final int firstBall;
    private final int secondBall;

    public Frame(int firstBall, int secondBall) {
        this.firstBall = firstBall;
        this.secondBall = secondBall;
    }

    public int countScore() {
        return firstBall + secondBall;
    }
}
