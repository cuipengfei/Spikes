package bowling.sixth;

public class Frame {
    private final int first;
    private final int second;
    private Frame nextFrame;
    private int ownScore;

    public Frame(int first, int second) {
        this.first = first;
        this.second = second;
        ownScore = first + second;
    }

    public int countScore() {
        return ownScore + countBonus();
    }

    private int countBonus() {
        int bonus = 0;
        if (isStrike()) {
            if (nextFrame.isStrike()) {
                bonus = nextFrame.first + nextFrame.nextFrame.first;
            } else {
                bonus = nextFrame.first + nextFrame.second;
            }
        } else if (isSpare()) {
            bonus = nextFrame.first;
        }
        return bonus;
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
}
