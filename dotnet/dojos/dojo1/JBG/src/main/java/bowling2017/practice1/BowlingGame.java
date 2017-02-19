package bowling2017.practice1;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
  private Frame previousFrame = null;
  private List<Frame> frames = new ArrayList<>();

  public void play(int roll1, int roll2) {
    addToFrameList(new Frame(roll1, roll2));
  }

  public void play(int roll1, int roll2, int roll3) {
    addToFrameList(new LastFrame(roll1, roll2, roll3));
  }

  private void addToFrameList(Frame frame) {
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
