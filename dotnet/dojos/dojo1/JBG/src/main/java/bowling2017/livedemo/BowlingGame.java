package bowling2017.livedemo;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {
  private List<Frame> frames = new ArrayList<>();

  public void play(int roll1, int roll2) {
    connectFrames(new Frame(roll1, roll2));
  }

  public void play(int roll1, int roll2, int roll3) {
    connectFrames(new LastFrame(roll1, roll2, roll3));
  }

  private void connectFrames(Frame frame) {
    if (!frames.isEmpty()) {
      frames.get(frames.size() - 1).setNext(frame);
    }
    frames.add(frame);
  }

  public int countTotal() {
    return frames.stream().mapToInt(Frame::countScore).sum();
  }
}
