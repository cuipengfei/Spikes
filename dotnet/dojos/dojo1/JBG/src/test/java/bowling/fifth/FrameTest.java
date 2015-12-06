package bowling.fifth;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FrameTest {
    @Test
    public void shouldCountScoreWithNoBonus() throws Exception {
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
        Frame frame2 = new Frame(3, 4);
        frame1.setNext(frame2);

        //when
        int score = frame1.countScore();

        //then
        assertThat(score, is(13));
    }

    @Test
    public void shouldCountScoreWithStrikeBonus() throws Exception {
        //given
        Frame frame1 = new Frame(10, 0);
        Frame frame2 = new Frame(3, 4);
        frame1.setNext(frame2);

        //when
        int score = frame1.countScore();

        //then
        assertThat(score, is(17));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonus() throws Exception {
        //given
        Frame frame1 = new Frame(10, 0);
        Frame frame2 = new Frame(10, 0);
        Frame frame3 = new Frame(3, 4);
        frame1.setNext(frame2);
        frame2.setNext(frame3);

        //when
        int score = frame1.countScore();

        //then
        assertThat(score, is(23));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonusWhenSecondStrikeIsLast() throws Exception {
        //given
        Frame frame1 = new Frame(10, 0);
        Frame frame2 = new Frame(10, 0);
        frame1.setNext(frame2);

        //when
        int score = frame1.countScore();

        //then
        assertThat(score, is(20));
    }
}
