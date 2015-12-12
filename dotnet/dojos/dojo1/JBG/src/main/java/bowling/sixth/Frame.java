package bowling.sixth;

public class Frame {
    private final int first;
    private final int second;

    public Frame(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int countScore() {
        return first + second;
    }
}
