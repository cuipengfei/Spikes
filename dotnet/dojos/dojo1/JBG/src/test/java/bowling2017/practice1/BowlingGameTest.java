package bowling2017.practice1;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BowlingGameTest {
  @Test
  public void shouldCountScoreOfWholeGame() throws Exception {
    //given
    BowlingGame bowlingGame = new BowlingGame();
    bowlingGame.play(1, 2);//3
    bowlingGame.play(1, 9);//20
    bowlingGame.play(10, 0);//13
    bowlingGame.play(1, 2);//3
    bowlingGame.play(2, 8);//10
    bowlingGame.play(0, 10);//11
    bowlingGame.play(1, 2);//3
    bowlingGame.play(10, 0);//21
    bowlingGame.play(10, 0);//13
    bowlingGame.play(1, 2, 0);//3

    //when
    int score = bowlingGame.countScore();

    //then
    assertThat(score, is(100));
  }

  @Test
  public void shouldCountScoreOfPerfectGame() throws Exception {
    //given
    BowlingGame bowlingGame = new BowlingGame();
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 0);
    bowlingGame.play(10, 10, 10);

    //when
    int score = bowlingGame.countScore();

    //then
    assertThat(score, is(300));
  }
}