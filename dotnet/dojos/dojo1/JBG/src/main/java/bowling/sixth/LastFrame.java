package bowling.sixth;

public class LastFrame extends Frame {
    private final int third;

    public LastFrame(int first, int second, int third) {
        super(first, second);
        this.third = third;
    }

    @Override
    public int countScore() {
        return super.ownScore + third;
    }
}
