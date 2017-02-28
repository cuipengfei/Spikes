package bowling2017.practice2;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BowlingGameTest {
  @Test
  public void shouldCountScoreOfAllFrames() throws Exception {
    BowlingGame bowlingGame = new BowlingGame();
    bowlingGame.play(1, 9);//11
    bowlingGame.play(1, 2);//3
    bowlingGame.play(9, 1);//11
    bowlingGame.play(1, 2);//3
    bowlingGame.play(10, 0);//13
    bowlingGame.play(1, 2);//3
    bowlingGame.play(0, 10);//11
    bowlingGame.play(1, 2);//3
    bowlingGame.play(1, 2);//3
    bowlingGame.play(1, 2);//3

    int total = bowlingGame.countScore();

    assertThat(total, is(64));
  }
}