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
        return ownScore + countBonus();
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }

    private int countBonus() {
        int bonus = 0;
        if (isStrike()) {
            bonus = nextBall() + nextNextBall();
        } else if (isSpare()) {
            bonus = nextBall();
        }
        return bonus;
    }

    private boolean isSpare() {
        return ownScore == 10;
    }

    private boolean isStrike() {
        return firstBall == 10;
    }

    private int nextNextBall() {
        if (nextFrame.isStrike() && nextFrame.isNotLast()) {
            return nextFrame.nextBall();
        }
        return nextFrame.secondBall;
    }

    private boolean isNotLast() {
        return nextFrame != null;
    }

    private int nextBall() {
        return nextFrame.firstBall;
    }
}
