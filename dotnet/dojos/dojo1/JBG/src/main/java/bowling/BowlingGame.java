package bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    private List<Frame> frames = new ArrayList<>();

    public void playOneFrame(int firstRoll, int secondRoll) {
        Frame frame = new Frame(firstRoll, secondRoll);
        save(frame);
    }

    public void playOneFrame(int firstRoll, int secondRoll, int thirdRoll) {
        LastFrame last = new LastFrame(firstRoll, secondRoll, thirdRoll);
        save(last);
    }

    public int totalScore() {
        return frames.stream()
                .map(Frame::countScore)
                .reduce(0, (a, b) -> a + b);
    }

    private void save(Frame frame) {
        if (frames.size() > 0) {
            frames.get(frames.size() - 1).setNextFrame(frame);
        }
        frames.add(frame);
    }

}
