package bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    private List<Frame> frames = new ArrayList<>();

    public void playOneFrame(int firstRoll, int secondRoll) {
        Frame frame = new Frame(firstRoll, secondRoll);
        if (frames.size() > 0) {
            frames.get(frames.size() - 1).setNextFrame(frame);
        }
        frames.add(frame);
    }

    public int totalScore() {
        return frames.stream()
                .map(Frame::countScore)
                .reduce(0, (a, b) -> a + b);
    }
}
