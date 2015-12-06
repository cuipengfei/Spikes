package bowling.second.practice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LastFrameTest {
    @Test
    public void shouldOnlyCountScoreOfItsOwn() throws Exception {
        //given
        LastFrame lastFrame = new LastFrame(2, 8, 8);

        //when
        int score = lastFrame.countScore();

        //then
        assertThat(score, is(18));
    }
}
