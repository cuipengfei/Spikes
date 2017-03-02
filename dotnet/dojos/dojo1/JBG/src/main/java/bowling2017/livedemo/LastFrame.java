package bowling2017.livedemo;

public class LastFrame extends Frame {
  private final int roll1;
  private final int roll2;
  private final int roll3;

  public LastFrame(int roll1, int roll2, int roll3) {
    super(roll1, roll2);
    this.roll1 = roll1;
    this.roll2 = roll2;
    this.roll3 = roll3;
  }

  @Override
  public int countScore() {
    return roll1 + roll2 + roll3;
  }
}
