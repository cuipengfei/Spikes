package bowling.sixth;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    private List<Frame> frames = new ArrayList<Frame>();

    public void play(int first, int second) {
        chainFrames(new Frame(first, second));
    }

    public void play(int first, int second, int third) {
        chainFrames(new LastFrame(first, second, third));
    }

    public int countTotal() {
        return frames.stream()
                .map(Frame::countScore)
                .reduce(0, (acc, f) -> acc + f);
    }

    private void chainFrames(Frame frame) {
        if (!frames.isEmpty()) {
            frames.get(frames.size() - 1).setNext(frame);
        }
        frames.add(frame);
    }
}
