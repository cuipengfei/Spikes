package bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    private List<Frame> frames = new ArrayList<>();

    public void playOneFrame(int firstRoll, int secondRoll) {
        Frame frame = new Frame(firstRoll, secondRoll);
        connectFrames(frame);
        frames.add(frame);
    }

    private void connectFrames(Frame frame) {
        if (frames.size() > 0) {
            frames.get(frames.size() - 1).setNextFrame(frame);
        }
    }

    public int totalScore() {
        return frames.stream()
                .map(Frame::countScore)
                .reduce(0, (a, b) -> a + b);
    }

    public void playOneFrame(int firstRoll, int secondRoll, int thirdRoll) {
        LastFrame last = new LastFrame(firstRoll, secondRoll, thirdRoll);
        connectFrames(last);
        frames.add(last);
    }
}
