package bowling.sixth;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LastFrameTest {
    @Test
    public void shouldOnlyCountItsOwnScore() throws Exception {
        //given
        LastFrame lastFrame = new LastFrame(3, 7, 5);

        //when
        int score=lastFrame.countScore();

        //then
        assertThat(score, is(15));
    }
}
