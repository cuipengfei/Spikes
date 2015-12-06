package bowling.fifth;

public class Frame {
    private final int firstBall;
    private final int secondBall;
    private Frame next;

    public Frame(int firstBall, int secondBall) {
        this.firstBall = firstBall;
        this.secondBall = secondBall;
    }

    public int countScore() {
        if (isStrike()) {
            return ownScore() + next.firstBall + next.secondBall;
        } else if (isSpare()) {
            return ownScore() + next.firstBall;
        }
        return ownScore();
    }

    private boolean isStrike() {
        return firstBall == 10;
    }

    private int ownScore() {
        return firstBall + secondBall;
    }

    private boolean isSpare() {
        return firstBall + secondBall == 10;
    }

    public void setNext(Frame next) {
        this.next = next;
    }
}
