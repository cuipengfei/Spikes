package bowling.first.practice;

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
    public void shouldNotHaveBonusWhenNotSpareNorSpike() {
        //given
        Frame frame = new Frame(1, 2);
        Frame nextFrame = new Frame(1, 2);
        frame.setNextFrame(nextFrame);

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

    @Test
    public void shouldCountNext2RollsAsBonusForStrike() throws Exception {
        //given
        Frame frame = new Frame(10, 0);
        Frame nextFrame = new Frame(3, 2);
        frame.setNextFrame(nextFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(15));
    }

    @Test
    public void shouldCountNext2RollsFromDifferentFramesAsBonusFor2Strikes() throws Exception {
        //given
        Frame frame = new Frame(10, 0);
        Frame nextFrame = new Frame(10, 0);
        Frame thirdFrame = new Frame(5, 0);
        frame.setNextFrame(nextFrame);
        nextFrame.setNextFrame(thirdFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(25));
    }

    @Test
    public void shouldCountNext1RollAsBonusForStrikeWhenNextFrameIsLast() throws Exception {
        //given
        Frame frame = new Frame(10, 0);
        Frame nextFrame = new Frame(2, 0);
        frame.setNextFrame(nextFrame);

        //when
        int score = frame.countScore();

        //then
        assertThat(score, is(12));
    }
}
