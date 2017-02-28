package bowling2017.practice2;

public class Frame {
  private final int roll1;
  private final int roll2;

  private final boolean isStrike;
  private final boolean isSpare;

  protected int ownScore;

  private Frame next;

  public Frame(int roll1, int roll2) {
    this.roll1 = roll1;
    this.roll2 = roll2;

    ownScore = roll1 + roll2;

    isStrike = roll1 == 10;
    isSpare = !isStrike && ownScore == 10;
  }

  public void setNext(Frame next) {
    this.next = next;
  }

  public int countScore() {
    return ownScore + countBonus();
  }

  private int countBonus() {
    if (isSpare) {
      return next.roll1;
    } else if (isStrike) {
      return next.roll1 + getNextNextRoll();
    }
    return 0;
  }

  private int getNextNextRoll() {
    if (next.isStrike && next.next != null) {
      return next.next.roll1;
    } else {
      return next.roll2;
    }
  }
}
