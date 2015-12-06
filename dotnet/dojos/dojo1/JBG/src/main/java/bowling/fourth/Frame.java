package bowling.fourth;

public class Frame {
    private final int firstRoll;
    private final int secondRoll;
    private Frame next;

    public Frame(int firstRoll, int secondRoll) {
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
    }

    public int countScore() {
        return ownScore() + countBonus();
    }

    private int countBonus() {
        int bonus = 0;
        if (isStrike()) {
            bonus = next.firstRoll + next.secondRoll;
        } else if (isSpare()) {
            bonus = next.firstRoll;
        }
        return bonus;
    }

    private int ownScore() {
        return firstRoll + secondRoll;
    }

    private boolean isStrike() {
        return firstRoll == 10;
    }

    private boolean isSpare() {
        return firstRoll + secondRoll == 10;
    }

    public void setNext(Frame next) {
        this.next = next;
    }
}
