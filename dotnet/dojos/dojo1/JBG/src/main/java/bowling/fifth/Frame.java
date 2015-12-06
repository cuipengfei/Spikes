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
        return ownScore() + countBonus();
    }

    private int countBonus() {
        if (isStrike()) {
            return nextBall() + nextNextBall();
        } else if (isSpare()) {
            return nextBall();
        }
        return 0;
    }

    private int nextNextBall() {
        if (next.isStrike() && next.isNotLast()) {
            return next.next.firstBall;
        } else {
            return next.secondBall;
        }
    }

    private boolean isNotLast() {
        return next != null;
    }

    private int nextBall() {
        return next.firstBall;
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
