package bowling2017.livedemo;

public class Frame {
  private final int roll1;
  private final int roll2;

  private Frame next;

  private final boolean isSpare;

  public Frame(int roll1, int roll2) {
    this.roll1 = roll1;
    this.roll2 = roll2;

    isSpare = roll1 + roll2 == 10;
  }

  public int countScore() {
    if (isSpare) {
      return roll1 + roll2 + next.roll1;
    } else {
      return roll1 + roll2;
    }
  }

  public void setNext(Frame next) {
    this.next = next;
  }
}
