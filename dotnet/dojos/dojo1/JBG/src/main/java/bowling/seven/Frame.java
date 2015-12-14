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
        boolean isSpare = ownScore == 10;
        boolean isStrike = firstBall == 10;

        int bonus = 0;
        if (isStrike) {
            bonus = nextFrame.firstBall + nextFrame.secondBall;
        } else if (isSpare) {
            bonus = nextFrame.firstBall;
        }
        return bonus;
    }

    public void setNext(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
