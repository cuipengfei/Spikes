package bowling.second.practice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FrameTest {
    @Test
    public void shouldCountScoreWithNoBonus() throws Exception {
        //given
        Frame frame = new Frame(2, 3);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(5));
    }

    @Test
    public void shouldCountScoreWithSpareBonus() throws Exception {
        //given
        Frame frame = new Frame(4, 6);
        Frame nextFrame = new Frame(2, 3);
        frame.setNextFrame(nextFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(12));
    }
}
