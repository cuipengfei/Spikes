package bowling.fourth;

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
        Frame frame1 = new Frame(1, 9);
        Frame frame2 = new Frame(1, 2);
        frame1.setNext(frame2);

        //when
        int score = frame1.countScore();

        //then
        assertThat(score, is(11));
    }
}
