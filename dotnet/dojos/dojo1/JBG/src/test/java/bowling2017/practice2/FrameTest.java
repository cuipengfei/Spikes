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
}