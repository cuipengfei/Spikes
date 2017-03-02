package bowling2017.livedemo;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BowlingGameTest {
  @Test
  public void shouldCalculateScoreOfAllFrames() throws Exception {
    BowlingGame bowlingGame = new BowlingGame();

    bowlingGame.play(1, 2);
    bowlingGame.play(2, 3);
    bowlingGame.play(4, 5);
    bowlingGame.play(4, 6);
    bowlingGame.play(1, 9);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(2, 3);
    bowlingGame.play(3, 7);
    bowlingGame.play(10, 10, 10);

    int total = bowlingGame.countTotal();

    assertThat(total, is(140));
  }
}