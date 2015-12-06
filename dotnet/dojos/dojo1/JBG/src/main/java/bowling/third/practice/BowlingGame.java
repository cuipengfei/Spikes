package bowling.third.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pfcui on 12/6/15.
 */
public class BowlingGame {
    private List<Frame> frames = new ArrayList<>();

    public void play(int first, int second, int third) {
        chainFrames(new LastFrame(first, second, third));
    }

    public void play(int first, int second) {
        chainFrames(new Frame(first, second));
    }

    private void chainFrames(Frame frame) {
        if (frames.size() > 0) {
            frames.get(frames.size() - 1).setNext(frame);
        }
        frames.add(frame);
    }

    public int countTotalScore() {
        return frames.stream()
                .map(Frame::countScore)
                .reduce(0, (acc, next) -> acc + next);
    }
}
