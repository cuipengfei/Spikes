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
        return ownScore + countBonus();
    }

    private int countBonus() {
        if (isStrike()) {
            return nextBall() + nextNextBall();
        } else if (isSpare()) {
            return nextBall();
        }
        return 0;
    }

    private int nextNextBall() {
        int nextNextBall = 0;
        if (nextFrame.isStrike()) {
            nextNextBall = nextFrame.nextFrame.firstBall;
        } else {
            nextNextBall = nextFrame.secondBall;
        }
        return nextNextBall;
    }

    private int nextBall() {
        return nextFrame.firstBall;
    }

    private boolean isSpare() {
        return ownScore == 10;
    }

    private boolean isStrike() {
        return firstBall == 10;
    }

    public void setNext(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
