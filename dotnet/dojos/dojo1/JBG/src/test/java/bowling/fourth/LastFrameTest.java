package bowling.fourth;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LastFrameTest {
    @Test
    public void shouldOnlyCountOwnScore() throws Exception {
        //given
        LastFrame frame = new LastFrame(1, 9, 10);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(20));
    }
}
