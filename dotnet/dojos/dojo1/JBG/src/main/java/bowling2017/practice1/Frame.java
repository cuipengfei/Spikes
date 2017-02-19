package bowling2017.practice1;

public class Frame {
  private final int roll1;
  private final int roll2;

  private int ownScore;
  private boolean isSpare;

  private Frame nextFrame;

  public Frame(int roll1, int roll2) {
    this.roll1 = roll1;
    this.roll2 = roll2;

    ownScore = roll1 + roll2;

    isSpare = (ownScore == 10) && roll1 != 10;
  }

  public int countScore() {
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
