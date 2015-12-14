package bowling.seven;

public class Frame {
    private final int firstBall;
    private final int secondBall;
    private Frame nextFrame;
    private int ownScore;

    public Frame(int firstBall, int secondBall) {
        this.firstBall = firstBall;
        this.secondBall = secondBall;
        ownScore = firstBall + secondBall;
    }

    public int countScore() {
        boolean isSpare = ownScore == 10;
        boolean isStrike = firstBall == 10;
        if (isStrike) {
            return ownScore + nextFrame.firstBall + nextFrame.secondBall;
        } else if (isSpare) {
            return ownScore + nextFrame.firstBall;
        }
        return ownScore;
    }

    public void setNext(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
