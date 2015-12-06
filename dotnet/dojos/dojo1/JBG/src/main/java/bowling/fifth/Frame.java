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
        if (firstBall + secondBall == 10) {
            return firstBall + secondBall + next.firstBall;
        }
        return firstBall + secondBall;
    }

    public void setNext(Frame next) {
        this.next = next;
    }
}
