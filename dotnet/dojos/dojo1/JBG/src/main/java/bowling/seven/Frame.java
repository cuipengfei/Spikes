package bowling.seven;

public class Frame {
    private final int firstBall;
    private final int secondBall;
    private Frame nextFrame;

    public Frame(int firstBall, int secondBall) {
        this.firstBall = firstBall;
        this.secondBall = secondBall;
    }

    public int countScore() {
        if (firstBall + secondBall == 10) {
            return firstBall + secondBall + nextFrame.firstBall;
        }
        return firstBall + secondBall;
    }

    public void setNext(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
