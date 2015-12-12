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
        if (first == 10) {
            return ownScore + nextFrame.first + nextFrame.second;
        }
        if (ownScore == 10) {
            return ownScore + nextFrame.first;
        }
        return ownScore;
    }

    public void setNext(Frame nextFrame) {

        this.nextFrame = nextFrame;
    }
}
