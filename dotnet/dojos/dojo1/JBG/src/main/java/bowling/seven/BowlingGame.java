package bowling.seven;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    private List<Frame> frames = new ArrayList<>();

    public void play(int first, int second) {
        chainFrames(new Frame(first, second));
    }

    public void play(int first, int second, int third) {
        chainFrames(new LastFrame(first, second, third));
    }

    public int countTotal() {
        return frames.stream()
                .map(Frame::countScore)
                .reduce(0, (accumulator, singleFrameScore) -> accumulator + singleFrameScore);
    }

    private void chainFrames(Frame frame) {
        if (!frames.isEmpty()) {
            frames.get(frames.size() - 1).setNext(frame);
        }
        frames.add(frame);
    }
}
