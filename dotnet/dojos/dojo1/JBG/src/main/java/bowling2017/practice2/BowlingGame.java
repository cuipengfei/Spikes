package bowling2017.practice2;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
  private List<Frame> frames = new ArrayList<>();
  private Frame previousFrame;

  public void play(int roll1, int roll2) {
    connectFrames(new Frame(roll1, roll2));
  }

  public void play(int roll1, int roll2, int roll3) {
    connectFrames(new LastFrame(roll1, roll2, roll3));
  }

  private void connectFrames(Frame frame) {
    frames.add(frame);
    if (previousFrame != null) {
      previousFrame.setNext(frame);
    }
    previousFrame = frame;
  }

  public int countScore() {
    return frames.stream().mapToInt(Frame::countScore).sum();
  }
}
