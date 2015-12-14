package bowling.seven;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FrameTest {
    @Test
    public void shouldCountScoreWithoutBonus() throws Exception {
        //given
        Frame frame = new Frame(3, 4);

        //when
        int score = frame.countScore();

        //when
        assertThat(score, is(7));
    }
}
