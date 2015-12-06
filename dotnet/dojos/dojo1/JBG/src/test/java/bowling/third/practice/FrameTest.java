package bowling.third.practice;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FrameTest {
    @Test
    public void shouldCountScoreWithoutBonus() throws Exception {
        //given
        Frame frame = new Frame(1, 2);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(3));
    }

    @Test
    public void shouldCountScoreWithSpareBonus() throws Exception {
        //given
        Frame frame = new Frame(1, 9);
        Frame nextFrame = new Frame(2, 3);
        frame.setNext(nextFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(12));
    }
}
