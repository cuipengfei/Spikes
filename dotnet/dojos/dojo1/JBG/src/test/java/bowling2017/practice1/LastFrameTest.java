package bowling2017.practice1;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LastFrameTest {
  @Test
  public void shouldCountOwnScoreAsSumOf3Rolls() throws Exception {
    //given
    LastFrame lastFrame = new LastFrame(1, 9, 3);

    //when
    int score = lastFrame.countScore();

    //then
    assertThat(score, is(13));
  }
}