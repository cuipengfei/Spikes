package bowling2017.practice1;

public class LastFrame extends Frame {
  private final int roll3;

  public LastFrame(int roll1, int roll2, int roll3) {
    super(roll1, roll2);
    this.roll3 = roll3;
  }

  @Override
  public int countScore() {
    return ownScore + roll3;
  }
}
