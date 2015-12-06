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
        boolean isSpare = firstRoll + secondRoll == 10;
        if (isSpare) {
            return firstRoll + secondRoll + next.firstRoll;
        }
        return firstRoll + secondRoll;
    }

    public void setNext(Frame next) {
        this.next = next;
    }
}
