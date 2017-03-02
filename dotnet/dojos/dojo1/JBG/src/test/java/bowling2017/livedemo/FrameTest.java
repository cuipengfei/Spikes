package bowling2017.livedemo;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FrameTest {

  @Test
  public void shouldCalculateScoreWithNoBonus() throws Exception {
    Frame frame = new Frame(1, 2);
    int score = frame.countScore();
    assertThat(score, is(3));
  }

  @Test
  public void shouldCountScoreWithSpareBonus() throws Exception {
    Frame frame = new Frame(1, 9);
    Frame frame2 = new Frame(1, 2);
    frame.setNext(frame2);

    int score = frame.countScore();

    assertThat(score, is(11));
  }

  @Test
  public void shouldCountScoreWithStrikeBonus() throws Exception {
    Frame frame = new Frame(10, 0);
    Frame frame2 = new Frame(1, 2);
    frame.setNext(frame2);

    int score = frame.countScore();

    assertThat(score, is(13));
  }
}