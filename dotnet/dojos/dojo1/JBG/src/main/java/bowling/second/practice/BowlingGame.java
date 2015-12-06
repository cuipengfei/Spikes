package bowling.second.practice;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
    private List<Frame> frames = new ArrayList<>();

    public void play(int firstBall, int secondBall) throws Exception {
        Frame frame = new Frame(firstBall, secondBall);
        connectFrames(frame);
    }

    public void play(int firstBall, int secondBall, int thirdBall) throws Exception {
        Frame frame = new LastFrame(firstBall, secondBall, thirdBall);
        connectFrames(frame);
    }

    public int totalScore() {
        return frames.stream()
                .map(Frame::countScore)
                .reduce(0, (acc, next) -> acc + next);
    }

    private void connectFrames(Frame frame) throws Exception {
        if (frames.size() > 0) {
            frames.get(frames.size() - 1).setNextFrame(frame);
        }
        frames.add(frame);
    }
}
