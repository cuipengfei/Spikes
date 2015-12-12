package bowling.sixth;

public class Frame {
    private final int first;
    private final int second;
    private Frame nextFrame;
    protected int ownScore;

    public Frame(int first, int second) {
        this.first = first;
        this.second = second;
        ownScore = first + second;
    }

    public int countScore() {
        return ownScore + countBonus();
    }

    private int countBonus() {
        if (isStrike()) {
            return nextRoll() + nextNextRoll();
        } else if (isSpare()) {
            return nextRoll();
        }
        return 0;
    }

    private int nextNextRoll() {
        if (nextFrame.isStrike() && nextFrame.isNotLast()) {
            return nextFrame.nextFrame.first;
        } else {
            return nextFrame.second;
        }
    }

    private int nextRoll() {
        return nextFrame.first;
    }

    private boolean isSpare() {
        return ownScore == 10;
    }

    private boolean isStrike() {
        return first == 10;
    }

    public void setNext(Frame nextFrame) {

        this.nextFrame = nextFrame;
    }

    private boolean isNotLast() {
        return this.nextFrame != null;
    }
}
