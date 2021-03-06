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

    @Test
    public void shouldCountScoreWithStrikeBonus() throws Exception {
        //given
        Frame frame = new Frame(10, 0);
        Frame nextFrame = new Frame(2, 3);
        frame.setNext(nextFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(15));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonus() throws Exception {
        //given
        Frame frame = new Frame(10, 0);
        Frame frame2 = new Frame(10, 0);
        Frame frame3 = new Frame(2, 3);
        frame.setNext(frame2);
        frame2.setNext(frame3);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(22));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonusWhenSecondStrikeIsLast() throws Exception {
        //given
        Frame ninthFrame = new Frame(10, 0);
        LastFrame tenthFrame = new LastFrame(10, 2, 3);
        ninthFrame.setNext(tenthFrame);

        //when
        int score = ninthFrame.countScore();

        //then
        assertThat(score, is(22));
    }
}
