package bowling.second.practice;

public class Frame {
    private int firstBall;
    private int secondBall;

    public Frame(int firstBall, int secondBall) {
        this.firstBall = firstBall;
        this.secondBall = secondBall;
    }

    public int countScore() {
        return firstBall + secondBall;
    }
}
