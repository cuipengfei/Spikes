package bowling.sixth;

public class LastFrame extends Frame {
    private final int third;
    private final int first;
    private final int second;

    public LastFrame(int first, int second, int third) {
        super(first, second);

        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public int countScore() {
        return first + second + third;
    }
}
