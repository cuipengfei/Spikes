package bowling.fifth;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    private List<Frame> frames = new ArrayList<>();

    public void play(int first, int second) {
        chain(new Frame(first, second));
    }

    public void play(int first, int second, int third) {
        chain(new LastFrame(first, second, third));
    }

    public int countTotalScore() {
        return frames.stream()
                .map(Frame::countScore)
                .reduce(0, (acc, next) -> acc + next);
    }

    private void chain(Frame frame) {
        if (!frames.isEmpty()) {
            frames.get(frames.size() - 1).setNext(frame);
        }
        frames.add(frame);
    }
}
