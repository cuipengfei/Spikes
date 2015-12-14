package bowling.seven;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LastFrameTest {
    @Test
    public void shouldOnlyCountItsOwnScore() throws Exception {
        //given
        LastFrame frame = new LastFrame(1, 9, 2);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(12));
    }
}
