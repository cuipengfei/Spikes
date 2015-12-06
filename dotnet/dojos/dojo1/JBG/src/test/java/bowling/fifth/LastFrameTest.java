package bowling.fifth;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LastFrameTest {
    @Test
    public void shouldOnlyCountItsOwnBalls() throws Exception {
        //given
        LastFrame frame = new LastFrame(1, 9, 2);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(12));
    }
}
