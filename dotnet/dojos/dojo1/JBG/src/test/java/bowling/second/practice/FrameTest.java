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

    @Test
    public void shouldCountScoreWithStrikeBonus() throws Exception {
        //given
        Frame frame = new Frame(10, 0);
        Frame nextFrame = new Frame(2, 3);
        frame.setNextFrame(nextFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(15));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonus() throws Exception {
        //given
        Frame frame = new Frame(10, 0);
        Frame nextFrame = new Frame(10, 0);
        Frame thirdFrame = new Frame(2, 3);
        frame.setNextFrame(nextFrame);
        nextFrame.setNextFrame(thirdFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(22));
    }

    @Test
    public void shouldCountScoreWith2StrikesBonusWhenSecondStrikeHasNoNextFrame() throws Exception {
        //given
        Frame ninthFrame = new Frame(10, 0);
        LastFrame tenthFrame = new LastFrame(10, 9, 9);
        ninthFrame.setNextFrame(tenthFrame);

        //when
        int score = ninthFrame.countScore();

        //then
        assertThat(score, is(29));
    }
}
