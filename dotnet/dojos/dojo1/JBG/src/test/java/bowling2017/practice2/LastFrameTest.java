package bowling2017.practice2;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LastFrameTest {

  @Test
  public void shouldCountLastFrameScoreAsThreeRollsSum() throws Exception {
    LastFrame lastFrame = new LastFrame(9, 1, 2);
    int score = lastFrame.countScore();
    assertThat(score, is(12));
  }
}