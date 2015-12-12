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
        if (ownScore == 10) {
            return first + second + nextFrame.first;
        }
        return first + second;
    }

    public void setNext(Frame nextFrame) {

        this.nextFrame = nextFrame;
    }
}
