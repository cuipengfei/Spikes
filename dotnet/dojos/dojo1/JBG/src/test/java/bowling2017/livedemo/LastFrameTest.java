package bowling2017.livedemo;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LastFrameTest {
  @Test
  public void shouldCalculateScoreAs3NumbersSum() throws Exception {
    LastFrame lastFrame = new LastFrame(1, 9, 3);
    int score = lastFrame.countScore();
    assertThat(score, is(13));
  }
}