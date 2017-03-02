package bowling2017.livedemo;

public class Frame {
  private final int roll1;
  private final int roll2;
  private final int ownScore;

  private Frame next;

  private final boolean isSpare;

  public Frame(int roll1, int roll2) {
    this.roll1 = roll1;
    this.roll2 = roll2;

    ownScore = roll1 + roll2;

    isSpare = ownScore == 10;
  }

  public int countScore() {
    if (isSpare) {
      return ownScore + next.roll1;
    } else {
      return ownScore;
    }
  }

  public void setNext(Frame next) {
    this.next = next;
  }
}
