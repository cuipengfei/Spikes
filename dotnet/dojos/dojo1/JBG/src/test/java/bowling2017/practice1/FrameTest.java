package bowling2017.practice1;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FrameTest {
  @Test
  public void shouldCountItsOwnScoreWhenForNonBonusCase() throws Exception {
    //given
    Frame frame = new Frame(1, 2);

    //when
    int score = frame.countScore();

    //then
    assertThat(score, is(3));
  }
}