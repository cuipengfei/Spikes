package bowling;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FrameTest {
    @Test
    public void shouldCountScoreOfRegularRolls() {
        //given
        Frame frame = new Frame(1, 2);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(3));
    }

    @Test
    public void shouldCountNextRollAsBonusForSpare() throws Exception {
        //given
        Frame frame = new Frame(5, 5);
        Frame nextFrame = new Frame(3, 2);
        frame.setNextFrame(nextFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(13));
    }
}
