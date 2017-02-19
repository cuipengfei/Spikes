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
      return ownScore + nextFrame.roll1 + nextFrame.roll2;
    }
    if (isSpare) {
      return ownScore + nextFrame.roll1;
    } else {
      return ownScore;
    }
  }

  public void setNext(Frame nextFrame) {
    this.nextFrame = nextFrame;
  }
}
