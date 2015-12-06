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

    private int countBonus() {
        int bonus = 0;
        boolean isStrike = firstBall == 10;
        boolean isSpare = ownScore == 10;
        if (isStrike) {
            bonus = nextFrame.firstBall + nextFrame.secondBall;
        } else if (isSpare) {
            bonus = nextFrame.firstBall;
        }
        return bonus;
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
