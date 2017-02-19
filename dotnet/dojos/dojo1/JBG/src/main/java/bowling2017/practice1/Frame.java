package bowling2017.practice1;

public class Frame {
  private final int roll1;
  private final int roll2;

  public Frame(int roll1, int roll2) {

    this.roll1 = roll1;
    this.roll2 = roll2;
  }

  public int countScore() {
    return roll1 + roll2;
  }
}
