package bowling2017.practice1;

public class Frame {
  private final int roll1;
  private final int roll2;
  private final boolean isStrike;

  private int ownScore;
  private boolean isSpare;

  private Frame nextFrame;

  public Frame(int roll1, int roll2) {
    this.roll1 = roll1;
    this.roll2 = roll2;

    ownScore = roll1 + roll2;

    isStrike = roll1 == 10;
    isSpare = (ownScore == 10) && !isStrike;
  }

  public int countScore() {
    if (isStrike) {
      return ownScore + nextFrame.roll1 + nextNextRoll();
    }
    if (isSpare) {
      return ownScore + nextFrame.roll1;
    } else {
      return ownScore;
    }
  }

  private int nextNextRoll() {
    if (nextFrame.isStrike) {
      return nextFrame.nextFrame.roll1;
    } else {
      return nextFrame.roll2;
    }
  }

  public void setNext(Frame nextFrame) {
    this.nextFrame = nextFrame;
  }
}
