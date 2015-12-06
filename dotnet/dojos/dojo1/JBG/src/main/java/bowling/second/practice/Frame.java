package bowling.second.practice;

public class Frame {
    private int firstBall;
    private int secondBall;
    private Frame nextFrame;
    private int ownScore;

    public Frame(int firstBall, int secondBall) {
        this.firstBall = firstBall;
        this.secondBall = secondBall;
        this.ownScore = firstBall + secondBall;

    }

    public int countScore() {
        boolean isSpare = ownScore == 10;
        if (isSpare) {
            return ownScore + nextFrame.firstBall;
        }
        return ownScore;
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
