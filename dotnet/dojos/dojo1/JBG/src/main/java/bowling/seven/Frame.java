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

    public void setNext(Frame nextFrame) {
        this.nextFrame = nextFrame;
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
        if (nextFrame.isStrike() && nextFrame.isNotLast()) {
            return nextFrame.nextFrame.firstBall;
        } else {
            return nextFrame.secondBall;
        }
    }

    private boolean isNotLast() {
        return nextFrame != null;
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
}
