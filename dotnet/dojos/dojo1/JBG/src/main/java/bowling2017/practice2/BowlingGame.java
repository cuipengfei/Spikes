package bowling2017.practice2;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
  private List<Frame> frames = new ArrayList<>();
  private Frame previousFrame;

  public void play(int roll1, int roll2) {
    Frame frame = new Frame(roll1, roll2);
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
