package bowling2017.practice2;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FrameTest {
  @Test
  public void frameShouldCountItsOwnScore() throws Exception {
    Frame frame = new Frame(1, 2);
    int score = frame.countScore();
    assertThat(score, is(3));
  }

  @Test
  public void frameShouldCountBonusForSpare() throws Exception {
    Frame frame = new Frame(2, 8);
    Frame frame2 = new Frame(3, 5);
    frame.setNext(frame2);

    int score = frame.countScore();

    assertThat(score, is(13));
  }

  @Test
  public void frameShouldCountBonusScoreForStrike() throws Exception {
    Frame frame = new Frame(10, 0);
    Frame frame2 = new Frame(2, 3);
    frame.setNext(frame2);

    int score = frame.countScore();

    assertThat(score, is(15));
  }

  @Test
  public void frameShouldCountBonusScoreForStrikeWhen2ContinuousStrikes() throws Exception {
    Frame frame = new Frame(10, 0);
    Frame frame2 = new Frame(10, 0);
    Frame frame3 = new Frame(4, 5);
    frame.setNext(frame2);
    frame2.setNext(frame3);

    int score = frame.countScore();

    assertThat(score, is(24));
  }
}