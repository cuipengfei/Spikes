package bowling2017.practice1;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
  private Frame previousFrame = null;
  private List<Frame> frames = new ArrayList<>();

  public void play(int roll1, int roll2) {
    Frame frame = new Frame(roll1, roll2);

    if (previousFrame != null) {
      previousFrame.setNext(frame);
    }
    previousFrame = frame;
    frames.add(frame);
  }

  public int countScore() {
    return frames.stream().mapToInt(Frame::countScore).sum();
  }
}
