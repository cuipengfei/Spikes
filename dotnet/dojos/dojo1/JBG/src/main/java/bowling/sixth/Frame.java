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
        boolean isStrike = first == 10;
        boolean isSpare = ownScore == 10;

        int bonus = 0;
        if (isStrike) {
            bonus = nextFrame.first + nextFrame.second;
        } else if (isSpare) {
            bonus = nextFrame.first;
        }
        return bonus;
    }

    public void setNext(Frame nextFrame) {

        this.nextFrame = nextFrame;
    }
}
