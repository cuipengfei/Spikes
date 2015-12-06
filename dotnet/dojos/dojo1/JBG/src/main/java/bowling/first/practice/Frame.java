package bowling.first.practice;

public class Frame {
    private int firstRoll;
    private int secondRoll;
    private int ownScore;
    private Frame nextFrame;

    public Frame(int firstRoll, int secondRoll) {
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
        this.ownScore = firstRoll + secondRoll;
    }

    public int countScore() {
        System.out.println(ownScore + getBonus());
        return ownScore + getBonus();
    }

    private int getBonus() {
        if (isSpare()) {
            return getNextRoll();
        }
        if (isStrike()) {
            return getNextRoll() + getNextNextRoll();
        }
        return 0;
    }

    private int getNextRoll() {
        return nextFrame.firstRoll;
    }

    private int getNextNextRoll() {
        boolean isNextLast = nextFrame.nextFrame == null;
        boolean nextAlsoStrike = nextFrame.isStrike();
        if (isNextLast || !nextAlsoStrike) {
            return nextFrame.secondRoll;
        }
        return nextFrame.nextFrame.firstRoll;
    }

    private boolean isStrike() {
        return firstRoll == 10;
    }

    private boolean isSpare() {
        return ownScore == 10 && !isStrike();
    }

    public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
    }
}
