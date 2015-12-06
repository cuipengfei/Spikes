package bowling.third.practice;

public class Frame {
    private final int firstRoll;
    private final int secondRoll;
    private Frame next;

    public Frame(int firstRoll, int secondRoll) {
        this.firstRoll = firstRoll;
        this.secondRoll = secondRoll;
    }

    public int countScore() {
        if (isSpare()) {
            return firstRoll + secondRoll + next.firstRoll;
        }
        return firstRoll + secondRoll;
    }

    private boolean isSpare() {
        return firstRoll + secondRoll == 10;
    }

    public void setNext(Frame next) {
        this.next = next;
    }
}
