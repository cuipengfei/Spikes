package bowling2017.practice1;

public class Frame {
  private final int roll1;
  private final int roll2;

  private final boolean isStrike;
  private final boolean isSpare;

  private Frame nextFrame;

  protected final int ownScore;

  public Frame(int roll1, int roll2) {
    this.roll1 = roll1;
    this.roll2 = roll2;

    ownScore = roll1 + roll2;

    isStrike = roll1 == 10;
    isSpare = (ownScore == 10) && !isStrike;
  }

  public int countScore() {
    if (isStrike) {
      return ownScore + nextRoll() + nextNextRoll();
    }
    if (isSpare) {
      return ownScore + nextRoll();
    } else {
      return ownScore;
    }
  }

  public void setNext(Frame nextFrame) {
    this.nextFrame = nextFrame;
  }

  private int nextRoll() {
    return nextFrame.roll1;
  }

  private int nextNextRoll() {
    if (nextFrame.isStrike && nextFrame.hasNextFrame()) {
      return nextFrame.nextRoll();
    } else {
      return nextFrame.roll2;
    }
  }

  private boolean hasNextFrame() {
    return nextFrame != null;
  }
}
