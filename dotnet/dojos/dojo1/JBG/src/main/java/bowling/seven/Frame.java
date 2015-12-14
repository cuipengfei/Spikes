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
        int bonus = 0;
        if (isStrike()) {
            if (nextFrame.isStrike()) {
                bonus = nextFrame.firstBall + nextFrame.nextFrame.firstBall;
            } else {
                bonus = nextFrame.firstBall + nextFrame.secondBall;
            }
        } else if (isSpare()) {
            bonus = nextFrame.firstBall;
        }
        return bonus;
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
